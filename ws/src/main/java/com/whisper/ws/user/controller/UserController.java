package com.whisper.ws.user.controller;

import com.whisper.ws.user.configuration.CurrentUser;
import com.whisper.ws.user.response.dto.UserDTO;
import com.whisper.ws.user.response.dto.UserUpdate;
import com.whisper.ws.user.request.UserCreateRequest;
import com.whisper.ws.user.shared.GenericMessage;
import com.whisper.ws.user.response.InternalApiResponse;
import com.whisper.ws.user.service.UserService;
import com.whisper.ws.user.repository.entity.User;
import com.whisper.ws.user.shared.Messages;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
        UserDTO userResponse = new UserDTO(user);
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
        UserDTO userResponse = new UserDTO(user);
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
    ResponseEntity<?> updateUser(@PathVariable long id, @Valid @RequestBody UserUpdate userUpdate,
                       @AuthenticationPrincipal CurrentUser currentUser){
        UserDTO response = new UserDTO(service.updateUser(id, userUpdate));
        InternalApiResponse internalApiResponse = InternalApiResponse.builder()
                .hasError(false)
                .httpStatus(OK)
                .payload(response)
                .message(new GenericMessage("User has updated"))
                .build();
        return ResponseEntity.ok(internalApiResponse);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("#id == #currentUser.userId")
    GenericMessage deleteUser(@PathVariable long id,@AuthenticationPrincipal CurrentUser currentUser){
        service.deleteUser(id);
        return new GenericMessage("User has been deleted");
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

}