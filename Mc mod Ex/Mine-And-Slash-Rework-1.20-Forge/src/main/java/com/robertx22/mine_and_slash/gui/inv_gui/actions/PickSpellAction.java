package com.robertx22.mine_and_slash.gui.inv_gui.actions;

import com.robertx22.mine_and_slash.database.data.spells.components.Spell;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.ModRange;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRangeInfo;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.vanilla_mc.packets.OpenContainerPacket;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class PickSpellAction extends GuiAction {

    Spell spell;

    public static int SLOT = 0;

    public PickSpellAction(Spell spell) {
        this.spell = spell;
    }


    @Override
    public ResourceLocation getIcon() {
        return spell.getIconLoc();
    }

    @Override
    public void saveExtraData(FriendlyByteBuf buf) {
        buf.writeInt(SLOT);
    }

    @Override
    public Object loadExtraData(FriendlyByteBuf buf) {
        return buf.readInt();
    }

    @Override
    public List<Component> getTooltip(Player p) {
        return spell.GetTooltipString(new StatRangeInfo(ModRange.hide()));
    }

    @Override
    public void doAction(Player p, Object data) {
        int slot = (int) data;

        Load.player(p).spellCastingData.setHotbar(slot, spell.GUID());

        Load.player(p).playerDataSync.setDirtyAndSync(p);
    }

    @Override
    public void clientAction(Player p, Object obj) {
        Packets.sendToServer(new OpenContainerPacket(OpenContainerPacket.GuiType.SKILL_GEMS));
    }

    @Override
    public String GUID() {
        return spell.GUID();
    }
}
