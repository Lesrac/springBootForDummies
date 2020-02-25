package ch.frick.starter.controller;

import ch.frick.starter.model.Profile;
import ch.frick.starter.repository.ProfileRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ProfileController {

    private final ProfileRepository profileRepository;

    public ProfileController(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @GetMapping("/profile")
    public Profile profile(@RequestParam(value = "name") String profileName) {
        log.info(String.format("Requesting profile with name %s", profileName));
        return profileRepository.findProfile(profileName);
    }

    @GetMapping("/{faction}/profiles")
    public List<Profile> profilesForFaction(@PathVariable("faction") String faction) {
        log.info(String.format("Requesting profiles for faction %s", faction));
        return profileRepository.getFactionProfiles(faction);
    }

}
