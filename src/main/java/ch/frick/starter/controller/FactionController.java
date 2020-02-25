package ch.frick.starter.controller;

import ch.frick.starter.model.Faction;
import ch.frick.starter.repository.FactionRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class FactionController {

    private final FactionRepository factionRepository;

    public FactionController(final FactionRepository factionRepository) {
        this.factionRepository = factionRepository;
    }

    @GetMapping("/factions")
    public List<Faction> getFactions() {
        log.info("get all factions");
        return factionRepository.getAllFactions();
    }

}
