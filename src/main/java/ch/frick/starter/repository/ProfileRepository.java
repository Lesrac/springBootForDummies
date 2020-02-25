package ch.frick.starter.repository;

import ch.frick.starter.controller.FactionController;
import ch.frick.starter.model.Profile;
import ch.frick.starter.model.ProfileHeader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileRepository {

    private final Map<String, Profile> profiles = new HashMap<>();
    private final Map<String, List<Profile>> profilesPerFaction = new HashMap<>();
    private final FactionRepository factionRepository;

    public ProfileRepository(@Value("${data.profiles.path}") final Resource profileData, final FactionRepository factionRepository) throws IOException {
        this.factionRepository = factionRepository;
        createProfiles(profileData);
    }

    private void createProfiles(final Resource profileData) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(profileData.getInputStream(), StandardCharsets.UTF_8))) {
            final CSVParser parse = CSVFormat.newFormat(';').withFirstRecordAsHeader().parse(bufferedReader);
            parse.forEach(record -> {
                Profile profile = Profile.builder()
                    .name(record.get(ProfileHeader.NAME))
                    .attacks(Integer.parseInt(record.get(ProfileHeader.ATTACKS)))
                    .ballisticSkill(Integer.parseInt(record.get(ProfileHeader.BALLISTICSKILL)))
                    .leadership(Integer.parseInt(record.get(ProfileHeader.LEADERSHIP)))
                    .maxSize(Integer.parseInt(record.get(ProfileHeader.MAXSIZE)))
                    .minSize(Integer.parseInt(record.get(ProfileHeader.MINSIZE)))
                    .movement(Integer.parseInt(record.get(ProfileHeader.MOVEMENT)))
                    .pointsPerModel(Integer.parseInt(record.get(ProfileHeader.POINTSPERMODEL)))
                    .power(Integer.parseInt(record.get(ProfileHeader.POWER)))
                    .save(Integer.parseInt(record.get(ProfileHeader.SAVE)))
                    .strength(Integer.parseInt(record.get(ProfileHeader.STRENGTH)))
                    .toughness(Integer.parseInt(record.get(ProfileHeader.TOUGHNESS)))
                    .weaponSkill(Integer.parseInt(record.get(ProfileHeader.WEAPONSKILL)))
                    .wounds(Integer.parseInt(record.get(ProfileHeader.WOUNDS)))
                    .faction(factionRepository.findFaction(record.get(ProfileHeader.FACTION)))
                    .build();

                if(profiles.put(profile.getName(), profile) != null) {
                    throw new IllegalArgumentException(String.format("Duplicate profile entry for name: %s", profile.getName()));
                }
                final String factionName = profile.getFaction().getName();
                if(profilesPerFaction.containsKey(factionName)) {
                    final List<Profile> profiles = profilesPerFaction.get(factionName);
                    profiles.add(profile);
                } else {
                    profilesPerFaction.put(factionName, Stream.of(profile).collect(Collectors.toList()));
                }
            });
        }
    }

    public Profile findProfile(String name) {
        return profiles.get(name);
    }

    public List<Profile> getFactionProfiles(String factionName) {
        return profilesPerFaction.get(factionName);
    }



}
