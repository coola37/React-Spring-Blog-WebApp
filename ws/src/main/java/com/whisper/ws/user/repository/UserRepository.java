package com.whisper.ws.user.repository;

import com.whisper.ws.user.repository.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByActivationToken(String token);

    Page<User> findByUserIdNot(long userId, Pageable pageable);

}
