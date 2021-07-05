package com.example.demo.appuser;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = { "http://localhost:3000"})
@RestController
@RequestMapping(path = "api/v1/user")
@AllArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping(path = "/viewAll/{email}")
    @ResponseBody
    Optional<AppUser> getUser(@PathVariable String email) {
        return  appUserService.getUserByEmail(email);
    }

}