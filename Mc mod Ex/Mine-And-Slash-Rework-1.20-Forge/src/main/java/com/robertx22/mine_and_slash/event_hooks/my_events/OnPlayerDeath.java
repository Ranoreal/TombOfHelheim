package com.robertx22.mine_and_slash.event_hooks.my_events;

import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import com.robertx22.mine_and_slash.capability.player.PlayerData;
import com.robertx22.mine_and_slash.capability.player.data.PlayerBuffData;
import com.robertx22.mine_and_slash.config.forge.ServerContainer;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.player.Player;

public class OnPlayerDeath extends EventConsumer<ExileEvents.OnPlayerDeath> {

    @Override
    public void accept(ExileEvents.OnPlayerDeath event) {
        try {

            Player player = event.player;
            var cd = Load.Unit(player).getCooldowns();

            if (!cd.isOnCooldown("death_event")) {

                Load.Unit(player).setAllDirtyOnLoginEtc();

                cd.setOnCooldown("death_event", 100);

                Load.Unit(player).setEquipsChanged();

                PlayerData data = Load.player(player);


                if (Load.Unit(player).getLevel() > ServerContainer.get().DEATH_PENALTY_START_LEVEL.get()) {
                    Load.Unit(player).onDeathDoPenalty();
                    data.rested_xp.onDeath();
                    data.favor.onDeath(player);
                }

                data.deathStats.died = true;
                data.buff = new PlayerBuffData(); // we delete all the buff foods and potions on death, if in the future i want some buffs to persist across death, change this
                data.playerDataSync.setDirty();

                var map = Load.mapAt(player.level(), player.blockPosition());

                if (map != null) {
                    map.reduceLives(player);

                    player.sendSystemMessage(Chats.MAP_DEATH_LIVES_LOSS.locName(map.getLives(player)).withStyle(ChatFormatting.RED));
                    /*
                    todo why was this code here??
                    List<Component> reqDifference = map.map.getStatReq().getReqDifference(map.map.lvl, Load.Unit(player));
                    if (!reqDifference.isEmpty()) {
                        player.sendSystemMessage(Chats.NOT_MEET_MAP_REQ_FIRST_LINE.locName());
                        reqDifference.forEach(player::sendSystemMessage);
                    }

                     */
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

