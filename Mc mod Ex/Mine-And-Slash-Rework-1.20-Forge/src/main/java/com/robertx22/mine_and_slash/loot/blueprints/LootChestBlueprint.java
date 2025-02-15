package com.robertx22.mine_and_slash.loot.blueprints;

import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import com.robertx22.mine_and_slash.database.data.loot_chest.base.LootChestData;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.bases.LootChestPart;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.RarityItems;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import net.minecraft.world.item.ItemStack;

public class LootChestBlueprint extends RarityItemBlueprint implements ITypeBlueprint {

    public LootChestBlueprint(LootInfo info) {
        super(info);
        this.rarity.chanceForHigherRarity = 75;
    }

    public LootChestPart type = new LootChestPart(this);

    public boolean useRarityKey = false;

    @Override
    public ItemStack generate() {


        LootChestData data = createData();

        ItemStack stack = data.getLootChest().getChestItem(data).getDefaultInstance();

        StackSaving.LOOT_CHEST.saveTo(stack, data);

        return stack;

    }

    public LootChestData createData() {
        LootChestData data = new LootChestData();

        // todo i set this to always same because otherwise you cant stack them, think of other way
        data.num = 5; // RandomUtils.RandomRange(5, 8);
        data.lvl = info.level;
        data.rar = this.rarity.get().GUID();
        data.id = this.type.get().GUID();

        if (useRarityKey) {
            data.key = VanillaUTIL.REGISTRY.items().getKey(RarityItems.RARITY_KEYS.get(data.rar).get()).toString();
        }

        return data;
    }


    @Override
    public void setType(String type) {
        this.type.set(ExileDB.LootChests().get(type));
    }
}
