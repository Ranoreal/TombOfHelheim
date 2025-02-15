package com.robertx22.mine_and_slash.uncommon.enumclasses;

public enum AttackStyle {

    ATTACK("attack", "Attack"),
    SPELL("spell", "Spell");;

    AttackStyle(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String id;
    public String name;

}
