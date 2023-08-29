package com.web.web.security.oauth;

import com.web.web.models.AuthenticationProvider;
import com.web.web.models.UserEntity;
import com.web.web.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class Oauth2LoginSucessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private UserService userService;

    public Oauth2LoginSucessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getEmail();
        UserEntity user = userService.findByEmail(email);
        String name = oAuth2User.getName();
        if (user == null) {
            //register as new user
            userService.createNewUserAfterOauthLogin(email, name, AuthenticationProvider.GOOGLE);
        } else {
            //update existing user
            userService.updateUserAfterOauthLogin(user, name, AuthenticationProvider.GOOGLE);
        }
        setDefaultTargetUrl("/clubs");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
