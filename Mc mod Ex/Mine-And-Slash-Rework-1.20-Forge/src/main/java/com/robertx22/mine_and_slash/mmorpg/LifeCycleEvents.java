package com.robertx22.mine_and_slash.mmorpg;

import com.robertx22.mine_and_slash.config.forge.compat.CompatConfig;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.mmorpg.registers.server.CommandRegister;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.LevelUtils;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;

public class LifeCycleEvents {

    static boolean regenDefault = true;

    public static void register() {


        ForgeEvents.registerForgeEvent(PlayerEvent.PlayerLoggedInEvent.class, event -> {
            if (MMORPG.RUN_DEV_TOOLS) {
                DataGeneration.generateAll();
            }
        });

        ForgeEvents.registerForgeEvent(RegisterCommandsEvent.class, event -> {
            CommandRegister.Register(event.getDispatcher());
        });

        ForgeEvents.registerForgeEvent(ServerStartedEvent.class, event -> {

            LevelUtils.runTests();

            if (CompatConfig.get().disableVanillaHealthRegen()) {
                regenDefault = event.getServer()
                        .getGameRules()
                        .getRule(GameRules.RULE_NATURAL_REGENERATION)
                        .get();

                event.getServer()
                        .getGameRules()
                        .getRule(GameRules.RULE_NATURAL_REGENERATION)
                        .set(false, event.getServer());
            }

            ExileDB.checkAllDatabasesHaveDefaultEmpty();

        });

        ForgeEvents.registerForgeEvent(ServerStoppingEvent.class, event -> {
            if (CompatConfig.get().disableVanillaHealthRegen()) {
                event.getServer()
                        .getGameRules()
                        .getRule(GameRules.RULE_NATURAL_REGENERATION)
                        .set(regenDefault, event.getServer());

            }
        });

    }
}
