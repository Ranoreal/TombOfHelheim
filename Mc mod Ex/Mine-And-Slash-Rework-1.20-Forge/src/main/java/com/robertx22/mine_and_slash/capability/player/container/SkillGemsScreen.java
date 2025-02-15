package com.robertx22.mine_and_slash.capability.player.container;

import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.saveclasses.skill_gem.MaxLinks;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SkillGemsScreen extends AbstractContainerScreen<SkillGemsMenu> {
    public static final ResourceLocation BACKGROUND_LOCATION = new ResourceLocation(SlashRef.MODID, "textures/gui/skill_gems.png");

    public SkillGemsScreen(SkillGemsMenu pMenu, Inventory pPlayerInventory, Component txt) {
        super(pMenu, pPlayerInventory, Component.literal(""));
        this.imageWidth = 230;
        this.imageHeight = 256;

    }

    int auraX = 0;
    int auraY = 0;

    @Override
    protected void init() {
        super.init();

        auraX = getGuiLeft() + 201;
        auraY = getGuiTop() + 153;

        int x = getGuiLeft() + 16;
        int y = getGuiTop() + 16;

        for (int i = 0; i < 8; i++) {
            int xadd = 0;
            if (i > 3) {
                xadd = 7;
            }
            var spellb = new SpellButton(i, x + (i * 25) + xadd, y);
            this.addRenderableWidget(spellb);

            for (int s = 0; s < 5; s++) {
                boolean can = false;

                MaxLinks suppslots = null;

                if (spellb.getSpell() != null) {
                    int num = s + 1;
                    suppslots = spellb.getSpell().getMaxLinks(menu.player);
                    can = suppslots.links >= num;
                }

                addRenderableWidget(new SuppGemOverlayButton(can, suppslots, x + (i * 25) + xadd - 2, y + 20 + (s * 18)));
            }

        }
    }

    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {

        String left = "" + Load.player(minecraft.player).getSkillGemInventory().getRemainingSpirit(minecraft.player);

        pGuiGraphics.drawString(this.font, left, 201, 153, ChatFormatting.LIGHT_PURPLE.getColor(), true);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        pGuiGraphics.blit(BACKGROUND_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);

    }

}
