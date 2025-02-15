package com.robertx22.mine_and_slash.database.data.omen;

import com.robertx22.mine_and_slash.database.data.rarities.GearRarityType;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.ITypeBlueprint;
import com.robertx22.mine_and_slash.loot.blueprints.RarityItemBlueprint;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.SlashItems;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts.AffixData;
import com.robertx22.mine_and_slash.tags.all.SlotTags;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import net.minecraft.world.item.ItemStack;

public class OmenBlueprint extends RarityItemBlueprint implements ITypeBlueprint {

    public OmenBlueprint(LootInfo info) {
        super(info);
    }


    public OmenPart omen = new OmenPart(this);

    @Override
    protected ItemStack generate() {
        OmenData data = new OmenData();

        var rar = this.rarity.get();

        var diff = rar.omens;

        var omen = this.omen.get();

        data.lvl = this.info.level;

        data.id = omen.GUID();

        data.rar = rar.GUID();

        int slots = diff.specific_slots.random();

        int affixes = diff.affixes.random();

        data.rarities.put(GearRarityType.NORMAL, diff.normal.random());
        data.rarities.put(GearRarityType.UNIQUE, diff.unique.random());
        data.rarities.put(GearRarityType.RUNED, diff.runed.random());

        while (data.slot_req.size() < slots) {
            var slot = omen.getRandomSlotReq();
            if (data.slot_req.stream().noneMatch(x -> x.slot.equals(slot.GUID()))) {
                data.slot_req.add(new OmenData.OmenSlotReq(slot.GUID(), omen.getRandomSlotReqRarity(data)));
            }
        }

        for (int i = 0; i < affixes; i++) {
            var affix = ExileDB.Affixes().getFilterWrapped(x -> omen.affix_types.contains(x.type)).of(x -> !x.requirements.tag_requirements.stream().allMatch(t -> t.included.contains(SlotTags.weapon_family.GUID()))).random();
            var adata = new AffixData(affix.type);
            adata.id = affix.GUID();
            adata.rar = rar.GUID();
            adata.p = OmenData.getStatPercent(data.rarities, data.slot_req, rar);
            data.aff.add(adata);
        }


        var stack = new ItemStack(SlashItems.OMEN.get());
        StackSaving.OMEN.saveTo(stack, data);
        return stack;
    }

    @Override
    public void setType(String type) {
        this.omen.set(ExileDB.Omens().get(type));
    }
}
