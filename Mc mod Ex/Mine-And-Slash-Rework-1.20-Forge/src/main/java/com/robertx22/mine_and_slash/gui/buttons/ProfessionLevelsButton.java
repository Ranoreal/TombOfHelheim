package com.robertx22.mine_and_slash.gui.buttons;

import com.robertx22.library_of_exile.utils.TextUTIL;
import com.robertx22.mine_and_slash.database.data.profession.Profession;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.localization.Gui;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ClientOnly;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class ProfessionLevelsButton extends ImageButton {

    public static int SX = 16;
    public static int SY = 16;

    Minecraft mc = Minecraft.getInstance();

    public ProfessionLevelsButton(int xPos, int yPos) {
        super(xPos, yPos, SX, SY, 0, 0, SY, new ResourceLocation("empty"), (button) -> {
        });

    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        setModTooltip();
        super.render(gui, mouseX, mouseY, delta);
    }

    @Override
    public void renderWidget(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        gui.blit(SlashRef.guiId("profession/button"), getX(), getY(), SX, SX, SX, SX, SX, SX);

    }

    @Override
    protected ClientTooltipPositioner createTooltipPositioner() {
        return DefaultTooltipPositioner.INSTANCE;
    }

    public void setModTooltip() {

        List<Component> list = new ArrayList<>();
        list.add(Words.PROFESSIONS.locName().withStyle(ChatFormatting.GREEN, ChatFormatting.BOLD));
        list.add(Component.empty());

        for (Profession prof : ExileDB.Professions().getList()) {
            var lvl = Load.player(ClientOnly.getPlayer()).professions.getLevel(prof.GUID());
            int exp = Load.player(ClientOnly.getPlayer()).professions.getExp(prof.GUID());
            int maxexp = Load.player(ClientOnly.getPlayer()).professions.getMaxExp(prof.GUID());

            class cappedChecker {
                private MutableComponent check() {
                    if (Load.player(ClientOnly.getPlayer()).professions.getLevel(prof.GUID()) >= Load.Unit(ClientOnly.getPlayer()).getLevel()) {
                        return Words.CAPPED_TO_LVL.locName();
                    } else {
                        return Component.literal("");
                    }
                }
            }
            var name = Gui.PROF_NAME.locName(prof.locName(), new cappedChecker().check()).withStyle(ChatFormatting.YELLOW);

            list.add(name);
//the rest of text needed for there is written in lang, just don't want to use the TooltipUtils cuz THIS TEXT IS NOT RELATED TO TOOLTIPS.
            list.add(Gui.PROF_LEVEL_AND_EXP.locName(lvl, exp, maxexp).withStyle(ChatFormatting.GREEN));

        }
        list.add(Component.empty());

        list.add(Gui.RESTED_COMBAT_EXP.locName().append(String.valueOf(Load.player(mc.player).rested_xp.bonusCombatExp)).withStyle(ChatFormatting.WHITE));
        list.add(Gui.RESTED_PROF_EXP.locName().append(String.valueOf(Load.player(mc.player).rested_xp.bonusProfExp)).withStyle(ChatFormatting.WHITE));

        this.setTooltip(Tooltip.create(TextUTIL.mergeList(list)));

    }


}