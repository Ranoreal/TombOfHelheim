package com.robertx22.mine_and_slash.aoe_data.database.boss_spell;

import com.robertx22.mine_and_slash.aoe_data.database.spells.schools.BossSpells;

public class BossDealCloseAoe extends BossCastSpell {
    @Override
    public int castTicks() {
        return 80;
    }

    @Override
    public String getCastSpeech() {
        return "DO NOT TOUCH ME!";
    }

    @Override
    public String GUID() {
        return "close_dmg";
    }

    @Override
    public String spellId() {
        return BossSpells.CLOSE_NOVA;
    }
}
