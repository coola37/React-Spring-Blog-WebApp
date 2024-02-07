package com.whisper.ws.user.configuration;

import com.whisper.ws.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.whisper.ws.user.repository.entity.User;

@Service
public class AppUserDetailService implements UserDetailsService {

    @Autowired
    UserService userService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User inDb = userService.findByEmail(email);
        if(inDb == null){
            throw new UsernameNotFoundException(email + "is not found");
        }else{
            return new CurrentUser(inDb);
        }


    }
}
