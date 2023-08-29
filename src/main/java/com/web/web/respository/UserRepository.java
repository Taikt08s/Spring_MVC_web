package com.web.web.respository;

import com.web.web.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    UserEntity findByEmail(String email);
    UserEntity findByUserName(String userName);
    UserEntity findFirstByUserName(String username);
    UserEntity findByResetPasswordToken(String token);
}
