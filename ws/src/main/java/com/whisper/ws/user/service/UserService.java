package com.whisper.ws.user.service;

import com.whisper.ws.user.configuration.CurrentUser;
import com.whisper.ws.user.response.dto.UserUpdate;
import com.whisper.ws.user.repository.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {
    public User createUser(User user);
    public Page<User> getUsers(Pageable pageable, CurrentUser currentUser);
    
    public void activateUser(String token);

    User getUser(Long id);

    User findByEmail(String email);

    User updateUser(long id, UserUpdate userUpdate);

    void deleteUser(long id);
}
