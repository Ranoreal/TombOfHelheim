package com.robertx22.mine_and_slash.event_hooks.entity;

import com.robertx22.mine_and_slash.saveclasses.unit.Unit;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class OnTrackEntity {

    public static void onPlayerStartTracking(ServerPlayer serverPlayerEntity, Entity entity) {

        try {

            if (entity.level().isClientSide) {
                return;
            }

            if (entity instanceof LivingEntity) {
                if (!Unit.shouldSendUpdatePackets((LivingEntity) entity)) {
                    return;
                }
                if (entity.is(serverPlayerEntity) == false) {
                    Packets.sendToClient(serverPlayerEntity,
                            Unit.getUpdatePacketFor((LivingEntity) entity, Load.Unit(entity))
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
