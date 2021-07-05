package com.example.demo.access;

import com.example.demo.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/api/v1/entries")
@AllArgsConstructor
public class BuildAccessController {

    private final BuildAccessService buildAccessService;
    private final AppUserService appUserService;

    @GetMapping(path = "/viewAll/{email}")
    @ResponseBody
    Collection<BuildAccess> getEntries(@PathVariable String email) {
        return  buildAccessService.getEntries(email);
    }

    @PostMapping
    public String punchIn(@RequestParam("user") String user) {
        return buildAccessService.punchIn(user);
    }

    @PostMapping(path = "/access")
    public String punchOut(@RequestParam("card") String accessCardId) {
        return buildAccessService.punchOut(accessCardId);
    }
}
