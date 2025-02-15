package com.robertx22.mine_and_slash.maps.processors.mob;

import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.mine_and_slash.config.forge.ServerContainer;
import com.robertx22.mine_and_slash.maps.dungeon_generation.ChunkProcessData;
import com.robertx22.mine_and_slash.maps.processors.DataProcessor;
import com.robertx22.mine_and_slash.maps.processors.helpers.MobBuilder;
import com.robertx22.mine_and_slash.maps.spawned_map_mobs.SpawnedMob;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;

public class MobHordeProcessor extends DataProcessor {

    public MobHordeProcessor() {
        super("mob_horde");
        this.detectIds.add("pack");
        this.detectIds.add("trader"); // todo for old unused trader stuff
    }

    @Override
    public boolean canSpawnLeagueMechanic() {
        return true;
    }

    @Override
    public void processImplementation(String key, BlockPos pos, Level world, ChunkProcessData data) {

        var map = Load.mapAt(world, pos);

        EntityType<? extends Mob> type = SpawnedMob.random(map).getType();

        int amount = RandomUtils.RandomRange(ServerContainer.get().PACK_MOB_MIN.get(), ServerContainer.get().PACK_MOB_MAX.get()); // add variability

        MobBuilder.of(type, x -> {
            x.amount = amount;
        }).summonMobs(world, pos);

    }
}