package com.robertx22.mine_and_slash.vanilla_mc.packets.spells;

import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

// todo is this class even used
public class TellServerToCancelSpellCast extends MyPacket<TellServerToCancelSpellCast> {

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(SlashRef.MODID, "cancelspell");
    }

    @Override
    public void loadFromData(FriendlyByteBuf tag) {

    }

    @Override
    public void saveToData(FriendlyByteBuf tag) {

    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        Player player = ctx.getPlayer();

        var spells = Load.player(player);

        if (spells.spellCastingData
                .getSpellBeingCast() != null) {

            //SpellCastContext sctx = new SpellCastContext(player, spells.spellCastingData.castTicksDone, spells.spellCastingData
            //      .getSpellBeingCast());

            //spells.spellCastingData.tryCast(sctx);
            spells.spellCastingData.cancelCast(player);

            spells.playerDataSync.setDirty();
        }
    }

    @Override
    public MyPacket<TellServerToCancelSpellCast> newInstance() {
        return new TellServerToCancelSpellCast();
    }
}

