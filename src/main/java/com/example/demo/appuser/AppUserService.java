package com.example.demo.appuser;

import com.example.demo.registration.token.ConfirmationToken;
import com.example.demo.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG =
            "user with email %s not found";

    private final static String ACCESS_CARD_NOT_FOUND_MSG =
            "card with id %s not found";

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;


    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND_MSG, email)));
    }

    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        return appUserRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND_MSG, id)));
    }

    public UserDetails findByAccessCardId(String accessCardId) throws UsernameNotFoundException {
        return appUserRepository.findByAccessCardId(accessCardId).orElseThrow(() ->
                new UsernameNotFoundException(
                        String.format(ACCESS_CARD_NOT_FOUND_MSG, accessCardId)));
    }

    public String signUpUser(AppUser appUser) {
        boolean userExists = appUserRepository
                .findByEmail(appUser.getEmail())
                .isPresent();

        if (userExists) {
            // TODO check of attributes are the same and
            // TODO if email not confirmed send confirmation email.

            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = bCryptPasswordEncoder
                .encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );

        confirmationTokenService.saveConfirmationToken(
                confirmationToken);
        return token;
    }

    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }

    public int updatePresenceForAppUser(String email, Boolean presence) {
        return appUserRepository.updatePresence(email, presence);
    }

    public Optional<AppUser> getUserById(Long id)
    {
        return appUserRepository.findById(id);
    }

    public Boolean existUser(Long id)
    {
       return appUserRepository.existsById(id);
    }

    public String signInUser(AppUser appUser)
    {
        boolean userExists = appUserRepository
                .findByEmail(appUser.getEmail())
                .isPresent();

        if (!userExists) {
            throw new IllegalStateException("email does not exist");
        }


        return "log in";
    }

    public boolean isEnabled(String email) {
        AppUser appUser = appUserRepository.findByEmail(email) .orElseThrow(() ->
                new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND_MSG, email)));;
        return appUser.isEnabled();
    }

    public boolean findUser(String email, String password) {
        boolean isFound = false;
        AppUser appUser = appUserRepository.findByEmail(email) .orElseThrow(() ->
                new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND_MSG, email)));

        String pass = appUser.getPassword();

        boolean isPasswordMatch = bCryptPasswordEncoder.matches(password, pass);

        if(isPasswordMatch)
            isFound = true;

        return isFound;
    }

    Optional<AppUser> getUserByEmail(String email)
    {
        Optional<AppUser> appUser = Optional.ofNullable(appUserRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND_MSG, email))));

        return appUser;
    }
}
