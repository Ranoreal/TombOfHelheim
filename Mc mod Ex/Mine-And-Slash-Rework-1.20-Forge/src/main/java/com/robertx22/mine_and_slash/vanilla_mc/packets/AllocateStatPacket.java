package com.robertx22.mine_and_slash.vanilla_mc.packets;

import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import com.robertx22.mine_and_slash.capability.player.PlayerData;
import com.robertx22.mine_and_slash.database.data.game_balance_config.PlayerPointsType;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.datapacks.stats.CoreStat;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class AllocateStatPacket extends MyPacket<AllocateStatPacket> {

    public String stat;
    AllocateStatPacket.ACTION action;

    public enum ACTION {
        ALLOCATE, REMOVE
    }

    public AllocateStatPacket() {

    }

    public AllocateStatPacket(Stat stat, ACTION act) {
        this.stat = stat.GUID();
        this.action = act;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(SlashRef.MODID, "stat_alloc");
    }

    @Override
    public void loadFromData(FriendlyByteBuf tag) {
        stat = tag.readUtf(30);
        action = tag.readEnum(AllocateStatPacket.ACTION.class);

    }

    @Override
    public void saveToData(FriendlyByteBuf tag) {
        tag.writeUtf(stat, 30);
        tag.writeEnum(action);

    }

    @Override
    public void onReceived(ExilePacketContext ctx) {

        Load.Unit(ctx.getPlayer()).setEquipsChanged();

        PlayerData cap = Load.player(ctx.getPlayer());

        if (action == ACTION.ALLOCATE) {
            if (PlayerPointsType.STATS.getFreePoints(ctx.getPlayer()) > 0) {
                if (ExileDB.Stats().get(stat) instanceof CoreStat) {
                    cap.statPoints.map.put(stat, 1 + cap.statPoints.map.getOrDefault(stat, 0));
                }
            }
        } else {
            if (PlayerPointsType.STATS.getResetPoints(ctx.getPlayer()) > 0) {
                if (ExileDB.Stats().get(stat) instanceof CoreStat) {
                    int current = cap.statPoints.map.getOrDefault(stat, 0);
                    if (current > 0) {
                        cap.statPoints.map.put(stat, current - 1);
                    }
                }
            }
        }
        Load.Unit(ctx.getPlayer()).setEquipsChanged();
        cap.playerDataSync.setDirty();
    }

    @Override
    public MyPacket<AllocateStatPacket> newInstance() {
        return new AllocateStatPacket();
    }
}
