package com.whisper.ws.user.service;

import com.whisper.ws.FileService;
import com.whisper.ws.user.configuration.CurrentUser;
import com.whisper.ws.user.response.dto.UserUpdate;
import com.whisper.ws.user.email.EmailService;
import com.whisper.ws.user.exceptions.ActivationNotificationException;
import com.whisper.ws.user.exceptions.InvalidTokenException;
import com.whisper.ws.user.exceptions.NotUniqueEmailException;
import com.whisper.ws.user.exceptions.UserNotFoundException;
import com.whisper.ws.user.repository.UserRepository;
import com.whisper.ws.user.repository.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repo;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EmailService emailService;

    @Autowired
    FileService fileService;

    @Transactional(rollbackOn = MailException.class)
    @Override
    public User createUser(User userCreateRequest) {
        try{
            User user = User.builder()
                    .username(userCreateRequest.getUsername())
                    .email(userCreateRequest.getEmail())
                    .password(passwordEncoder.encode(userCreateRequest.getPassword()))
                    .activationToken(UUID.randomUUID().toString())
                    .active(false)
                    .build();

            User userResponse = repo.saveAndFlush(user);
            emailService.sendActivationEmail(user.getEmail(), user.getActivationToken());
            return userResponse;
        }catch (DataIntegrityViolationException e){
            throw new NotUniqueEmailException();
        }catch (MailException e){
            throw new ActivationNotificationException();
        }
    }

    @Override
    public Page<User> getUsers(Pageable pageable, CurrentUser currentUser) {
        if(currentUser == null) return repo.findAll(pageable);
        return repo.findByUserIdNot(currentUser.getUserId(), pageable);
    }

    @Override
    public void activateUser(String token) {
        User inDB = repo.findByActivationToken(token);
        if(inDB == null){
            throw new InvalidTokenException();
        }
        inDB.setActive(true);
        inDB.setActivationToken(null);
        repo.save(inDB);
    }

    @Override
    public User getUser(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User findByEmail(String email) {
        return repo.findByEmail(email);
    }

    @Override
    public User updateUser(long id, UserUpdate userUpdate) {
        User inDB = getUser(id);
        inDB.setUsername(userUpdate.username());
        if(userUpdate.image() != null){
            String fileName = fileService.saveBase64StringAsFile(userUpdate.image());
            fileService.deleteProfileImage(inDB.getImage());
            inDB.setImage(fileName);
        }

        return repo.save(inDB);
    }

    @Override
    public void deleteUser(long id) {
        User inDb = getUser(id);
        if(inDb.getImage() != null){
            fileService.deleteProfileImage(inDb.getImage());
        }

        repo.deleteById(id);
    }


}