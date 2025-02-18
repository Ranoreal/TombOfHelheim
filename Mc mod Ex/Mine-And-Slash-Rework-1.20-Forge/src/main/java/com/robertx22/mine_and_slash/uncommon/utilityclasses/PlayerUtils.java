package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PlayerUtils {

    public static List<Player> getNearbyPlayers(Level world, BlockPos pos, double range) {
        return world.getServer()
                .getPlayerList()
                .getPlayers()
                .stream()
                .filter(x -> pos.distSqr(new BlockPos((int) x.getX(), (int) x.getY(), (int) x.getZ())) < range)
                .collect(Collectors.toList());

    }


    public static void giveItem(ItemStack stack, Player player) {
        if (player.addItem(stack) == false) {
            player.spawnAtLocation(stack, 1F);
        }
        player.getInventory().setChanged();
    }

    public static Player nearestPlayer(ServerLevel world, LivingEntity entity) {
        return nearestPlayer(world, entity.position());
    }

    public static Player nearestPlayer(ServerLevel world, BlockPos pos) {
        return nearestPlayer(world, new Vec3(pos.getY(), pos.getY(), pos.getZ()));
    }

    public static Player nearestPlayer(ServerLevel world, Vec3 pos) {

        Optional<ServerPlayer> player = world.players()
                .stream().filter(x -> !x.isSpectator())
                .min(Comparator.comparingDouble(x -> x.distanceToSqr(pos)));

        return player.orElse(null);
    }

}
