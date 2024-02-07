package com.whisper.ws.user.service;

import com.whisper.ws.user.configuration.CurrentUser;
import com.whisper.ws.user.dto.UserDTO;
import com.whisper.ws.user.dto.UserUpdate;
import com.whisper.ws.user.repository.entity.User;
import com.whisper.ws.user.request.UserCreateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {
    public User createUser(User user);
    public Page<User> getUsers(Pageable pageable, CurrentUser currentUser);
    
    public void activateUser(String token);

    User getUser(Long id);

    User findByEmail(String email);

    User updateUser(long id, UserUpdate userUpdate);
}
