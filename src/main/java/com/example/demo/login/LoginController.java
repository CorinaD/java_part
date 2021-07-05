package com.example.demo.login;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = { "http://localhost:3000"})
@RestController
@RequestMapping(path = "api/v1/login")
@AllArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public String login(@RequestBody LoginRequest request) {
        return loginService.login(request);
    }
}
