package com.robertx22.mine_and_slash.database.data.profession.all;

import com.robertx22.library_of_exile.deferred.RegObj;
import com.robertx22.mine_and_slash.capability.player.data.PlayerBuffData;
import com.robertx22.mine_and_slash.database.data.gear_types.bases.SlotFamily;
import com.robertx22.mine_and_slash.database.data.profession.buffs.StatBuffs;
import com.robertx22.mine_and_slash.database.data.profession.items.CraftedBuffFoodItem;
import com.robertx22.mine_and_slash.database.data.profession.items.CraftedInfusionItem;
import com.robertx22.mine_and_slash.database.data.profession.items.CraftedSoulItem;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.RarityItemHolder;
import com.robertx22.mine_and_slash.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import net.minecraft.world.item.Item;

import java.util.HashMap;

public class ProfessionProductItems {

    public static HashMap<SlotFamily, HashMap<String, RegObj<Item>>> CRAFTED_SOULS = new HashMap<>();
    public static HashMap<SlotFamily, HashMap<String, RegObj<Item>>> CRAFTED_ENCHANTS = new HashMap<>();

    public static HashMap<StatBuffs.AlchemyBuff, RarityItemHolder> POTIONS = new HashMap<>();
    public static HashMap<StatBuffs.FoodBuff, RarityItemHolder> FOODS = new HashMap<>();
    public static HashMap<StatBuffs.SeaFoodBuff, RarityItemHolder> SEAFOOD = new HashMap<>();

    public static void init() {

        for (StatBuffs.AlchemyBuff buff : StatBuffs.ALCHEMY) {
            RarityItemHolder ho = new RarityItemHolder(buff.id,
                    new RarityItemHolder.Maker("buff_potion", x -> new CraftedBuffFoodItem(PlayerBuffData.Type.POTION, buff.id, x)));
            POTIONS.put(buff, ho);
        }
        for (StatBuffs.FoodBuff buff : StatBuffs.FOOD_BUFFS) {

            RarityItemHolder ho = new RarityItemHolder(buff.id,
                    new RarityItemHolder.Maker("food", x -> new CraftedBuffFoodItem(PlayerBuffData.Type.MEAL, buff.id, x)));
            FOODS.put(buff, ho);
        }
        for (StatBuffs.SeaFoodBuff buff : StatBuffs.SEAFOOD_BUFFS) {
            RarityItemHolder ho = new RarityItemHolder(buff.id,
                    new RarityItemHolder.Maker("seafood", x -> new CraftedBuffFoodItem(PlayerBuffData.Type.FISH, buff.id, x)));
            SEAFOOD.put(buff, ho);
        }

        for (String rar : IRarity.NORMAL_GEAR_RARITIES) {
            for (SlotFamily fam : SlotFamily.values()) {
                if (fam != SlotFamily.NONE) {

                    String id = "stat_soul/family/" + fam.id + "/" + rar;
                    if (!CRAFTED_SOULS.containsKey(fam)) {
                        CRAFTED_SOULS.put(fam, new HashMap<>());
                    }
                    RegObj<Item> obj = Def.item(id, () -> new CraftedSoulItem(fam, rar));
                    CRAFTED_SOULS.get(fam).put(rar, obj);

                }
            }
        }
        for (String rar : IRarity.NORMAL_GEAR_RARITIES) {
            for (SlotFamily fam : SlotFamily.values()) {
                if (fam != SlotFamily.NONE) {
                    String id = "enchantment/family/" + fam.id + "/" + rar;
                    if (!CRAFTED_ENCHANTS.containsKey(fam)) {
                        CRAFTED_ENCHANTS.put(fam, new HashMap<>());
                    }
                    RegObj<Item> obj = Def.item(id, () -> new CraftedInfusionItem(fam, rar));
                    CRAFTED_ENCHANTS.get(fam).put(rar, obj);

                }
            }
        }

    }
}
