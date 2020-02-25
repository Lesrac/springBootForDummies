package ch.frick.starter.repository;

import ch.frick.starter.model.Faction;
import ch.frick.starter.model.ProfileHeader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class FactionRepository {
    private final Map<String, Faction> factions = new HashMap<>();
    private final List<Faction> factionList = new ArrayList<>();

    public FactionRepository(@Value("${data.factions.path}") final Resource factionData) throws IOException {
        createFactions(factionData);
    }

    private void createFactions(final Resource factionData) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(factionData.getInputStream(), StandardCharsets.UTF_8))) {
            final CSVParser parse = CSVFormat.newFormat(';').withFirstRecordAsHeader().parse(bufferedReader);
            parse.forEach(record -> {
                Faction faction = Faction.builder()
                    .name(record.get(ProfileHeader.NAME))
                    .build();

                if(factions.put(faction.getName(), faction) != null) {
                    throw new IllegalArgumentException(String.format("Duplicate faction entry for name: %s", faction.getName()));
                }
                factionList.add(faction);
            });
        }
    }

    public Faction findFaction(String name) {
        return factions.get(name);
    }

    public List<Faction> getAllFactions() {
        return factionList;
    }
}
