package com.whisper.ws.user.controller;

import com.whisper.ws.auth.service.TokenService;
import com.whisper.ws.user.configuration.CurrentUser;
import com.whisper.ws.user.dto.UserDTO;
import com.whisper.ws.user.dto.UserUpdate;
import com.whisper.ws.user.exceptions.*;
import com.whisper.ws.user.request.UserCreateRequest;
import com.whisper.ws.user.response.ApiError;
import com.whisper.ws.user.shared.GenericMessage;
import com.whisper.ws.user.response.InternalApiResponse;
import com.whisper.ws.user.response.UserResponse;
import com.whisper.ws.user.service.UserService;
import com.whisper.ws.user.repository.entity.User;
import com.whisper.ws.user.shared.Messages;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/signup")
    ResponseEntity <?> createUser(@Valid @RequestBody UserCreateRequest userCreateRequest){
        User user = service.createUser(userCreateRequest.toUser());
        UserResponse userResponse = convertUserResponse(user);
        InternalApiResponse<Object> internalApiResponse =InternalApiResponse.builder()
                .httpStatus(CREATED)
                .payload(userResponse)
                .hasError(false)
                .message(new GenericMessage("User has created"))
                .build();
        return ResponseEntity.ok(internalApiResponse);
    }

    @GetMapping("/get/{id}")
    ResponseEntity<?> getUserById(@PathVariable Long id){
        User user = service.getUser(id);
        UserResponse userResponse = convertUserResponse(user);
        InternalApiResponse internalApiResponse = InternalApiResponse.builder()
                .message(new GenericMessage("Fetched user"))
                .hasError(false)
                .payload(userResponse)
                .httpStatus(OK)
                .build();
        return ResponseEntity.ok(internalApiResponse);
    }

    @PutMapping("/{id}")
    @PreAuthorize("#id == #currentUser.userId")
    UserDTO updateUser(@PathVariable long id, @Valid @RequestBody UserUpdate userUpdate,
                       @AuthenticationPrincipal CurrentUser currentUser){
        return new UserDTO(service.updateUser(id, userUpdate));
    }


    @GetMapping("/get")
    Page<UserDTO> getUsers(Pageable pageable, @AuthenticationPrincipal CurrentUser currentUser ){
        return service.getUsers(pageable, currentUser).map(UserDTO::new);
    }

    @PatchMapping("/{token}/active")
    GenericMessage activateUser(@PathVariable String token){
        service.activateUser(token);
        String message = Messages.getMessageForLocale("whisper.activate.user.success.message",
                LocaleContextHolder.getLocale());
        return new GenericMessage(message);
    }


    private Page<UserResponse> convertUserResponseList(Page<User> userList) {
        List<UserResponse> userResponses = userList.stream()
                .map(arg -> UserResponse.builder()
                        .userId(arg.getUserId())
                        .username(arg.getUsername())
                        .password(arg.getPassword())
                        .email(arg.getEmail())
                        .activationToken(arg.getActivationToken())
                        .active(arg.getActive())
                        .build())
                .collect(Collectors.toList());
        return new PageImpl<>(userResponses, userList.getPageable(), userList.getTotalElements());
    }



    private static UserResponse convertUserResponse(User user){
        return UserResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .activationToken(user.getActivationToken())
                .active(user.getActive())
                .build();
    }

}