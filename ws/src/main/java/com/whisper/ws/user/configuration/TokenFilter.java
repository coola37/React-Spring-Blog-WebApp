package com.whisper.ws.user.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whisper.ws.auth.service.TokenService;
import com.whisper.ws.user.response.ApiError;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.whisper.ws.user.repository.entity.*;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.io.OutputStream;

@Component
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver exceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String tokenWithPrefix = getTokenWithPrefix(request);
        if(tokenWithPrefix != null){
            User user = tokenService.verifyToken(tokenWithPrefix);
            if(user != null){
                if(!user.getActive()){
                    exceptionResolver.resolveException(request, response, null, new DisabledException("User disabled"));
                    return;

                }
                CurrentUser currentUser = new CurrentUser(user);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(currentUser,
                        null, currentUser.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenWithPrefix(HttpServletRequest request){
        var tokenWithPrefix = request.getHeader("Authorization");
        var cookies = request.getCookies();
        if(cookies == null) return tokenWithPrefix;
        for(var cookie: cookies){
            if(!cookie.getName().equals("w-token")) continue;
            if(cookie.getValue() == null || cookie.getValue().isEmpty()) continue;
            return "AnyPrefix " + cookie.getValue();
        }
        return tokenWithPrefix;
     }
}
