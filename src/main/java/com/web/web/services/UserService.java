package com.web.web.services;

import com.web.web.dto.RegistrationDTO;
import com.web.web.models.AuthenticationProvider;
import com.web.web.models.UserEntity;

public interface UserService {
    void saveUser(RegistrationDTO registrationDTO);

    UserEntity findByEmail(String email);

    UserEntity findByUserName(String userName);

    void createNewUserAfterOauthLogin(String email, String name, AuthenticationProvider provider);

    void updateUserAfterOauthLogin(UserEntity user, String name, AuthenticationProvider authenticationProvider);
}
