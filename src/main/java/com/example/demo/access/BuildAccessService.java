package com.example.demo.access;

import com.example.demo.appuser.AppUser;
import com.example.demo.appuser.AppUserService;
import com.example.demo.registration.EmailValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

import static com.example.demo.utils.DateManipulation.convertMsToActualDate;

@Service
@AllArgsConstructor
public class BuildAccessService {

    private final BuildAccessRepository buildAccessRepository;
    private final EmailValidator emailValidator;
    private final AppUserService appUserService;

   public String punchIn(String email) {

       boolean isValidEmail = emailValidator.
               test(email);

       if (!isValidEmail) {
           throw new IllegalStateException("email not valid");
       }

       AppUser appUser = (AppUser) appUserService.loadUserByUsername(email);
       long currentTime = System.currentTimeMillis();
       buildAccessRepository.save(
                new BuildAccess(
                        currentTime,
                        appUser
                )
       );
       appUserService.updatePresenceForAppUser(appUser.getEmail(), !appUser.getInsideBuilding());
        return "o mers";
    }

    public String punchOut(String accessCardId) {
        System.out.println("Card id " + accessCardId);

        AppUser appUser = (AppUser) appUserService.findByAccessCardId(accessCardId);

        long currentTime = System.currentTimeMillis();
        if (appUser.getInsideBuilding()){
            buildAccessRepository.punchOut(
                    currentTime,
                    appUser
            );
            } else {
             buildAccessRepository.save(
                    new BuildAccess(
                            currentTime,
                            appUser
                 )
             );
        }

        appUserService.updatePresenceForAppUser(appUser.getEmail(), !appUser.getInsideBuilding());
        return "o mers mno";
    }

    public Optional<BuildAccess> getAppUser(String appUser) {
        return buildAccessRepository.findByAppUser(appUser);
    }

    @Transactional
    public Collection<BuildAccess> getEntries(String email) {
        boolean isValidEmail = emailValidator.
                test(email);

        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }

        AppUser appUser = (AppUser) appUserService.loadUserByUsername(email);

        return buildAccessRepository.findAllEntriesById(appUser);
    }

    public int setExitTime(Long appUser) {
        return buildAccessRepository.updateExitTime(
                appUser, LocalDateTime.now());
    }

}