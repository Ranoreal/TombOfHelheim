package com.robertx22.mine_and_slash.database.data.loot_chest.base;

import com.robertx22.mine_and_slash.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import com.robertx22.mine_and_slash.uncommon.interfaces.INeedsNBT;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.mine_and_slash.vanilla_mc.items.misc.AutoItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class LootChestItem extends AutoItem implements INeedsNBT {
    String name;

    public LootChestItem(String name) {
        super(new Properties().stacksTo(64));
        this.name = name;
    }

    @Override
    public void generateModel(ItemModelManager manager) {
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player p, InteractionHand pUsedHand) {
        ItemStack stack = p.getItemInHand(pUsedHand);

        try {
            if (!pLevel.isClientSide) {

                p.getCooldowns().addCooldown(this, 20);

                LootChestData data = StackSaving.LOOT_CHEST.loadFrom(stack);

                if (data != null) {

                    if (data.canOpen(p)) {
                        if (data.isLocked()) {
                            data.spendKey(p);
                        }
                        stack.shrink(1);

                        for (ItemStack loot : data.getLootChest().generateAll(data)) {
                            PlayerUtils.giveItem(loot, p);
                        }

                    }
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return InteractionResultHolder.pass(p.getItemInHand(pUsedHand));

    }

    @Override
    public String locNameForLangFile() {
        return "Loot Chest";
    }

    @Override
    public String GUID() {
        return "";
    }
}
