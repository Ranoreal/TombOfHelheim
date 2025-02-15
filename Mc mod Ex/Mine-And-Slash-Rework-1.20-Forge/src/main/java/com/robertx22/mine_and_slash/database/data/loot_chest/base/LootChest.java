package com.robertx22.mine_and_slash.database.data.loot_chest.base;

import com.robertx22.library_of_exile.registry.ExileRegistry;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.mine_and_slash.database.registry.ExileRegistryTypes;
import com.robertx22.mine_and_slash.loot.req.DropRequirement;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public abstract class LootChest implements ExileRegistry<LootChest> {

    public abstract ItemStack generateOne(LootChestData data);


    public int minLevelDrop() {
        return 0;
    }

    public abstract DropRequirement getDropReq();

    public abstract Item getKey();


    public List<ItemStack> generateAll(LootChestData data) {
        List<ItemStack> list = new ArrayList<>();
        for (int i = 0; i < data.num; i++) {
            list.add(generateOne(data));
        }
        return list;
    }

    public boolean isLocked() {
        return getKey() != null && getKey() != Items.AIR;
    }

    public abstract Item getChestItem(LootChestData data);

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.LOOT_CHEST;
    }
}
