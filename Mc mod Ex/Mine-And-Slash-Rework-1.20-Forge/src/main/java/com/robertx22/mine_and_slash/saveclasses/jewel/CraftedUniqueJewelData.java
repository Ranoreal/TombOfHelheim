package com.robertx22.mine_and_slash.saveclasses.jewel;

import com.robertx22.mine_and_slash.database.data.affixes.Affix;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts.AffixData;
import com.robertx22.mine_and_slash.tags.all.SlotTags;
import net.minecraft.world.item.ItemStack;

public class CraftedUniqueJewelData {
    public static String CRAFTED_UNIQUE_ID = "crafted_unique";
    public static String WATCHER_EYE = "watcher_eye";

    private int t = 0;

    public String id = "";

    public int perc = 0;


    public boolean isUnique() {
        return !id.isEmpty();
    }

    public CraftedJewelTier getCraftedTier() {
        return CraftedJewelTier.fromTier(t);
    }

    public ItemStack getStackNeededForUpgrade() {
        return getCraftedTier().upgradeStack.get();
    }

    public void upgradeUnique(JewelItemData data) {
        t++;

        CraftedJewelTier tier = getCraftedTier();

        data.lvl = tier.lvl;

        if (tier.addsAffix) {// make all rarity upgrades add to affixes, whatever, if its too op, nerf the affixes

            Affix affix = ExileDB.Affixes().getFilterWrapped(x -> {
                return x.type == Affix.AffixSlot.crafted_jewel_unique && x.getAllTagReq().contains(SlotTags.crafted_jewel_unique.GUID());
            }).random();

            var affixdata = new AffixData(Affix.AffixSlot.crafted_jewel_unique);
            affixdata.randomizeTier(data.getRarity());
            affixdata.p = affixdata.getMinMax().random();
            affixdata.id = affix.guid;
            data.affixes.add(affixdata);

        }
    }


    public boolean isCraftableUnique() {
        return id.equals(CRAFTED_UNIQUE_ID);
    }

}
