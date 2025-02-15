package com.robertx22.mine_and_slash.capability.bases;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;

public class EntityGears {

    private HashMap<EquipmentSlot, ItemStack> map = new HashMap<>();

    public ItemStack get(EquipmentSlot slot) {
        if (map.isEmpty()) {
            for (EquipmentSlot s : EquipmentSlot.values()) {
                map.put(s, ItemStack.EMPTY);
            }
        }
        return map.get(slot);
    }

    public ItemStack put(EquipmentSlot slot, ItemStack stack) {
        if (map.isEmpty()) {
            for (EquipmentSlot s : EquipmentSlot.values()) {
                map.put(s, ItemStack.EMPTY);
            }
        }
        return map.put(slot, stack);
    }

}
