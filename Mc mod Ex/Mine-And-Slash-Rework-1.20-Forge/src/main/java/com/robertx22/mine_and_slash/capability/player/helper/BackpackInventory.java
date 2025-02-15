package com.robertx22.mine_and_slash.capability.player.helper;

import com.robertx22.mine_and_slash.capability.player.data.Backpacks;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.PlayerUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class BackpackInventory extends MyInventory {

    Player p;
    Backpacks.BackpackType type;

    public BackpackInventory(Player p, Backpacks.BackpackType type, int pSize) {
        super(pSize);
        this.type = type;
        this.p = p;
    }


    /*
    @Override
    public int getTotalSlots() {
        int slots = 0;

        ItemStack stack = MyCurioUtils.get(RefCurio.BACKPACK, p, 0);
        if (stack.getItem() instanceof BackpackItem bag) {
            slots += bag.getSlots();
        }
        return MathHelper.clamp(slots, 0, getContainerSize());
    }
     */


    // todo must test this
    public void throwOutBlockedSlotItems(int slots) {

        
        int open = slots;

        for (int i = 0; i < getContainerSize(); i++) {
            if (i >= open || !this.type.isValid(getItem(i))) {
                ItemStack stack = getItem(i);
                PlayerUtils.giveItem(stack.copy(), p);
                stack.shrink(100);
            }

        }

    }

    public int getBlockedSlots() {
        return getContainerSize() - getTotalSlots();
    }


}
