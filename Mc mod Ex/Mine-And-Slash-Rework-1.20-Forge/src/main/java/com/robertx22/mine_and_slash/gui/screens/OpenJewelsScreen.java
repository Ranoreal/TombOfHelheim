package com.robertx22.mine_and_slash.gui.screens;

import com.robertx22.mine_and_slash.gui.bases.IAlertScreen;
import com.robertx22.mine_and_slash.gui.bases.IContainerNamedScreen;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ClientOnly;
import com.robertx22.mine_and_slash.vanilla_mc.packets.OpenJewelsPacket;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.resources.ResourceLocation;

public class OpenJewelsScreen implements IContainerNamedScreen, IAlertScreen {

    @Override
    public void openContainer() {

        Packets.sendToServer(new OpenJewelsPacket());
    }

    @Override
    public ResourceLocation iconLocation() {
        return new ResourceLocation(SlashRef.MODID, "textures/gui/main_hub/icons/jewel.png");
    }

    @Override
    public Words screenName() {
        return Words.Jewels;
    }

    @Override
    public boolean shouldAlert() {
        var data = Load.player(ClientOnly.getPlayer());
        return data.getJewels().hasFreeJewelSlots(ClientOnly.getPlayer());
    }
}
