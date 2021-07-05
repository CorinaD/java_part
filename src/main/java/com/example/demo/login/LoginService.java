package com.example.demo.login;

import com.example.demo.appuser.AppUserService;
import com.example.demo.registration.EmailValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;

    public String login(LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();
        String status = "error";

        boolean isValidEmail = emailValidator.
                test(email);

        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }

       if (appUserService.findUser(email, password) && appUserService.isEnabled(email) ) {
           status = "success";
       }

        return status;
    }
}