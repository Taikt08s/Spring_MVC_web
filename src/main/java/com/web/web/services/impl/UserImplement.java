package com.web.web.services.impl;

import com.web.web.dto.RegistrationDTO;
import com.web.web.models.AuthenticationProvider;
import com.web.web.models.Role;
import com.web.web.models.UserEntity;
import com.web.web.respository.RoleRepository;
import com.web.web.respository.UserRepository;
import com.web.web.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserImplement implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserImplement(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(RegistrationDTO registrationDTO) {
        UserEntity user = new UserEntity();
        user.setUserName(registrationDTO.getUserName());
        user.setEmail(registrationDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        Role role = roleRepository.findByName("USER");
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserEntity findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public void createNewUserAfterOauthLogin(String email, String name, AuthenticationProvider provider) {
        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setUserName(name);
        user.setAuthProvider(provider);
        userRepository.save(user);
    }

    @Override
    public void updateUserAfterOauthLogin(UserEntity user, String name, AuthenticationProvider authenticationProvider) {
        user.setUserName(name);
        user.setAuthProvider(authenticationProvider);
        userRepository.save(user);
    }

    public void updateResetPasswordToken(String token, String email) throws userNotFoundException {
        UserEntity user = userRepository.findByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        } else {
            throw new userNotFoundException("Cant find user with email: " + email);
        }
    }

    public UserEntity get(String resetPasswordToken) {
        return userRepository.findByResetPasswordToken(resetPasswordToken);
    }

    public void updatePassword(UserEntity user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodePassword);
        user.setResetPasswordToken(null);
        userRepository.save(user);
    }
}
