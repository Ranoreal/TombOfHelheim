package com.robertx22.mine_and_slash.capability.player.data;

import com.robertx22.mine_and_slash.config.forge.ServerContainer;
import com.robertx22.mine_and_slash.mmorpg.ModErrors;

public class RestedExpData {

    public int bonusProfExp = 0;
    public int bonusCombatExp = 0;

    public void onGiveCombatExp(int give) {
        bonusProfExp += give * ServerContainer.get().COMBAT_TO_PROFESSION_RESTED_XP_GENERATION.get();
    }

    public void onGiveProfExp(int give) {
        bonusCombatExp += give * ServerContainer.get().PROFESSION_TO_COMBAT_RESTED_XP_GENERATION.get();
    }

    public void onDeath() {

        try {

            bonusProfExp *= ServerContainer.get().RESTED_XP_DEATH_PENALTY.get();
            bonusCombatExp *= ServerContainer.get().RESTED_XP_DEATH_PENALTY.get();

        } catch (Exception e) {
            ModErrors.print(e);
        }

    }
}
