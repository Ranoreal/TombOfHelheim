package com.robertx22.mine_and_slash.loot.generators;

import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.ItemBlueprint;
import com.robertx22.mine_and_slash.uncommon.enumclasses.LootType;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseLootGen<T extends ItemBlueprint> {

    public abstract float baseDropChance();

    public boolean chanceIsModified() {
        return true;
    }

    public abstract LootType lootType();

    public boolean hasLevelDistancePunishment() {
        return true;
    }

    public boolean onlyOneDropAllowed() {
        return false;
    }

    protected abstract ItemStack generateOne();

    public boolean condition() {
        return true;
    }

    public List<ItemStack> tryGenerate() {

        List<ItemStack> list = new ArrayList<ItemStack>();

        for (int i = 0; i < info.amount; i++) {
            if (condition()) {
                try {
                    list.add(generateOne());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    public LootInfo info;

    public BaseLootGen(LootInfo info) {
        this.info = info;
        this.info.setup(this);

    }

}
