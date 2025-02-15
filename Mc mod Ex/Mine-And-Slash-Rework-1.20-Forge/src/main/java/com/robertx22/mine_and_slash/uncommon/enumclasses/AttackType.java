package com.robertx22.mine_and_slash.uncommon.enumclasses;

import com.robertx22.mine_and_slash.uncommon.utilityclasses.ErrorUtils;

import java.util.Arrays;
import java.util.List;

// should be renamed to hittype
public enum AttackType {

    hit("hit", "Hit") {},
    bonus_dmg("bonus_dmg", "Bonus Dmg") {},
    dot("dot", "DOT") {};
    // all("all", "Any") {};

    public static List<AttackType> getAllUsed() {
        return Arrays.asList(hit);
    }

    public String id;

    AttackType(String id, String locname) {
        this.id = id;
        this.locname = locname;

        ErrorUtils.ifFalse(id.equals(this.name()));
    }


    public boolean isHit() {
        return this == hit;
    }

    public boolean isAttack() {
        return this == hit;
    }


    public String locname;

}