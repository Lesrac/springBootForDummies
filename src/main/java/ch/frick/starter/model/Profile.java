package ch.frick.starter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Profile {

    private final String name;
    private final int movement;
    private final int weaponSkill;
    private final int ballisticSkill;
    private final int strength;
    private final int toughness;
    private final int wounds;
    private final int attacks;
    private final int leadership;
    private final int save;
    private final int power;
    private final int pointsPerModel;
    private final int minSize;
    private final int maxSize;
    private final Faction faction;

}
