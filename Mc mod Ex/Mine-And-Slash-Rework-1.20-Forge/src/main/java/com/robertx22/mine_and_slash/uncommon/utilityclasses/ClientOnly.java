package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import com.robertx22.mine_and_slash.prophecy.gui.ProphecyScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class ClientOnly {

    public static int ticksSinceChatWasOpened = 0;

    public static void totemAnimWithItem(ItemStack stack) {
        Minecraft.getInstance().player.playSound(SoundEvents.TOTEM_USE, 1, 1);
        Minecraft.getInstance().gameRenderer.displayItemActivation(stack);
    }

    public static void openProphecy() {
        Minecraft.getInstance().setScreen(new ProphecyScreen());
    }


    public static Entity getEntityByUUID(Level world, UUID id) {

        if (world instanceof ClientLevel) {
            for (Entity entity : ((ClientLevel) world).entitiesForRendering()) {
                if (entity.getUUID()
                        .equals(id)) {

                    return entity;
                }
            }
        }
        return null;

    }

    public static Player getPlayerById(UUID id) {

        try {
            return Minecraft.getInstance().level.getPlayerByUUID(id);
        } catch (Exception e) {

        }
        return null;
    }

    public static Player getPlayer() {
        return Minecraft.getInstance().player;
    }

    public static void setScreen(Screen s) {
        Minecraft.getInstance().setScreen(s);
    }

    public static void closeScreen() {
        Minecraft.getInstance().setScreen(null);
    }


    public static void pressUseKey() {
        Minecraft.getInstance().options.keyUse.setDown(true);
    }


    public static void stopUseKey() {
        Minecraft.getInstance().options.keyUse.setDown(false);
    }

    public static void printInChat(MutableComponent text) {
        Minecraft.getInstance().player.sendSystemMessage(text);
    }


}
