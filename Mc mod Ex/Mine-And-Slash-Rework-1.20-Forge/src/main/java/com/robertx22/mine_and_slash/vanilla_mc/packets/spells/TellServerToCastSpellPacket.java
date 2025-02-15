package com.robertx22.mine_and_slash.vanilla_mc.packets.spells;

import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import com.robertx22.mine_and_slash.capability.player.data.PlayerConfigData;
import com.robertx22.mine_and_slash.database.data.spells.components.Spell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.RepairUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class TellServerToCastSpellPacket extends MyPacket<TellServerToCastSpellPacket> {

    int number;

    public TellServerToCastSpellPacket(int number) {
        this.number = number;
    }

    public TellServerToCastSpellPacket() {
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(SlashRef.MODID, "tell_server_castspell");
    }

    @Override
    public void loadFromData(FriendlyByteBuf tag) {
        this.number = tag.readInt();
    }

    @Override
    public void saveToData(FriendlyByteBuf tag) {
        tag.writeInt(number);
    }

    public static boolean tryCastSpell(Player player, Spell spell) {

        var data = Load.player(player);

        if (player.isBlocking() || player.swinging) {
            return false;
        }

        if (spell != null) {

            var can = data.spellCastingData.canCast(spell, player);

            if (can.can) {

                ItemStack wep = player.getMainHandItem();

                if (!wep.isEmpty() && !RepairUtils.isItemBroken(wep)) {
                    wep.hurt(1, player.getRandom(), (ServerPlayer) player);
                }

                SpellCastContext c = new SpellCastContext(player, 0, spell);
                data.spellCastingData.setToCast(c);
                spell.spendResources(c);

                data.playerDataSync.setDirty();
                return true;
            } else {

                var cds = Load.Unit(player).getCooldowns();

                if (!cds.isOnCooldown("spell_fail")) {
                    cds.setOnCooldown("spell_fail", 40);
                    if (can.answer != null) {
                        if (Load.Unit(player).getLevel() < 15 || Load.player(player).config.isConfigEnabled(PlayerConfigData.Config.CAST_FAIL)) {
                            player.sendSystemMessage(Chats.CAST_FAILED.locName().append(can.answer));
                        }
                    }
                }
            }

        }
        return false;
    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        Player player = ctx.getPlayer();

        Spell spell = Load.player(ctx.getPlayer()).getSkillGemInventory().getHotbarGem(number).getSpell();

        tryCastSpell(player, spell);

    }

    @Override
    public MyPacket<TellServerToCastSpellPacket> newInstance() {
        return new TellServerToCastSpellPacket();
    }
}
