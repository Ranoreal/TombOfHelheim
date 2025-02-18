package com.robertx22.mine_and_slash.saveclasses.stat_soul;

import com.robertx22.library_of_exile.util.ExplainedResult;
import com.robertx22.library_of_exile.utils.ItemstackDataSaver;
import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.mine_and_slash.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.mine_and_slash.database.data.gear_slots.GearSlot;
import com.robertx22.mine_and_slash.database.data.gear_types.bases.SlotFamily;
import com.robertx22.mine_and_slash.database.data.level_ranges.LevelRange;
import com.robertx22.mine_and_slash.database.data.profession.items.CraftedSoulItem;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.database.data.unique_items.UniqueGear;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.gui.inv_gui.actions.auto_salvage.ToggleAutoSalvageRarity;
import com.robertx22.mine_and_slash.gui.texts.ExileTooltips;
import com.robertx22.mine_and_slash.gui.texts.textblocks.*;
import com.robertx22.mine_and_slash.gui.texts.textblocks.usableitemblocks.UsageBlock;
import com.robertx22.mine_and_slash.itemstack.CustomItemData;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.itemstack.ExileStacklessData;
import com.robertx22.mine_and_slash.itemstack.StackKeys;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.GearBlueprint;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.RarityItems;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.SlashItems;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.ModRange;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRangeInfo;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipContext;
import com.robertx22.mine_and_slash.tags.imp.SlotTag;
import com.robertx22.mine_and_slash.uncommon.MathHelper;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.ISettableLevelTier;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import com.robertx22.mine_and_slash.uncommon.localization.Itemtips;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.LevelUtils;
import com.robertx22.temp.SkillItemTier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;


public class StatSoulData implements ICommonDataItem<GearRarity>, ISettableLevelTier {


    public int tier = 1;

    public String slot = "";

    public String rar = "";

    public SlotFamily fam = SlotFamily.NONE;

    public String uniq = "";

    public String force_tag = "";

    public boolean can_sal = true;


    public SavedGearSoul gear = null;


    public static boolean has(ItemStack stack) {

        return StackSaving.STAT_SOULS.has(stack) || (stack.getItem() instanceof CraftedSoulItem i && i.getSoul(stack) != null);


    }

    // todo how do i make the result accept nbt.
    // and how to make jei accept nbt

    // i COULD make each of these an item ? and have profession set the correct tier?

    public static StatSoulData ofFamily(GearRarity rar, SkillItemTier tier, SlotFamily fam) {
        StatSoulData data = new StatSoulData();
        data.tier = tier.tier;
        data.fam = fam;
        data.rar = rar.GUID();
        return data;
    }

    public boolean canBeOnAnySlot() {
        return slot.isEmpty();
    }

    public void setCanBeOnAnySlot() {
        this.slot = "";
    }

    public ItemStack toStack() {


        ItemStack stack = new ItemStack(SlashItems.STAT_SOUL.get());

        StackSaving.STAT_SOULS.saveTo(stack, this);

        if (!slot.isEmpty()) {
            stack.getOrCreateTag()
                    .putInt("CustomModelData", ExileDB.GearSlots()
                            .get(slot).model_num);
        }

        return stack;

    }

    public ItemStack insertAsUnidentifiedOn(ItemStack s, Player p) {

        ItemStack copy = s.copy();

        // todo replacing souls needs to support replacing more than just gearitemdata.. otherwise you could just delete potential or corruption or whatever
        if (gear != null) {
            gear.saveTo(copy);
            return copy;
        } else {
            var e = this.createGearData(copy, p);
            var ex = ExileStack.of(copy);
            e.apply(ex);
            return ex.getStack();
        }
    }

    public GearSlot getSlotFor(ItemStack stack) {
        GearSlot gearslot = ExileDB.GearSlots().random();

        if (!slot.isEmpty()) {
            gearslot = ExileDB.GearSlots().get(slot);
        }
        if (stack != null) {
            gearslot = GearSlot.getSlotOf(stack);
        }
        return gearslot;
    }

    public boolean forcesTag() {
        return !force_tag.isEmpty();
    }

    public boolean isArmor() {
        return this.fam == SlotFamily.Armor || (!this.slot.isEmpty() && ExileDB.GearSlots().get(slot).fam == SlotFamily.Armor);
    }

    public ExplainedResult canApplyTo(ItemStack stack) {
        GearSlot slot = GearSlot.getSlotOf(stack);

        if (slot == null) {
            return ExplainedResult.failure(Chats.NOT_GEAR_OR_NOT_COMPAT.locName());
        }
        if (fam != SlotFamily.NONE) {
            if (slot.fam != fam) {
                return ExplainedResult.failure(Chats.NOT_MATCHING_GEAR_FAMILY.locName());
            } else {
                return ExplainedResult.success();
            }
        }
        if (canBeOnAnySlot()) {
            return ExplainedResult.success();
        }

        if (!this.slot.isEmpty()) {
            if (this.slot.equals(slot.GUID())) {
                return ExplainedResult.success();
            } else {
                return ExplainedResult.failure(Chats.NOT_MATCHING_GEAR_SLOT.locName());
            }
        }

        return ExplainedResult.silentlyFail();
    }


    public ExileStacklessData createGearData(@Nullable ItemStack stack, Player p) {

        int lvl = MathHelper.clamp(Load.Unit(p).getLevel(), LevelUtils.tierToLevel(tier).getMinLevel(), LevelUtils.tierToLevel(tier).getMaxLevel());

        GearBlueprint b = new GearBlueprint(LootInfo.ofLevel(lvl));
        b.level.set(lvl);
        b.rarity.set(ExileDB.GearRarities()
                .get(rar));


        GearSlot gearslot = getSlotFor(stack);
        String slotid = gearslot.GUID();


        var possible = ExileDB.GearTypes().getFilterWrapped(x -> x.gear_slot.equals(slotid)).list;

        if (forcesTag()) {
            // only use the force tag if the slot can actually roll that tag, this is a bandaid fix for now
            var filted = possible.stream().filter(x -> x.tags.contains(force_tag)).collect(Collectors.toList());
            if (!filted.isEmpty()) {
                possible = filted;
            }
        }
        if (!possible.isEmpty()) {
            b.gearItemSlot.set(RandomUtils.weightedRandom(possible));
        }

        UniqueGear uniq = ExileDB.UniqueGears().get(this.uniq);

        if (!uniq.isEmpty()) {
            b.uniquePart.set(uniq);
            b.rarity.set(uniq.getUniqueRarity());
            b.gearItemSlot.set(uniq.getBaseGear());
        }

        var ex = b.createData();

        ex.getOrCreate(StackKeys.POTENTIAL).potential *= GameBalanceConfig.get().CRAFTED_GEAR_POTENTIAL_MULTI;
        ex.getOrCreate(StackKeys.CUSTOM).data.set(CustomItemData.KEYS.CRAFTED, true);
        ex.getOrCreate(StackKeys.CUSTOM).data.set(CustomItemData.KEYS.SALVAGING_DISABLED, !this.can_sal);

        return ex;
    }

    public ExplainedResult canInsertIntoStack(ItemStack stack) {

        if (stack.isEmpty()) {
            return ExplainedResult.silentlyFail();
        }

        if (StackSaving.GEARS.has(stack)) {
            return ExplainedResult.failure(Chats.ALREADY_HAS_SOUL.locName());
        }

        if (this.gear != null) {
            if (GearSlot.isItemOfThisSlot(gear.gear.GetBaseGearType().getGearSlot(), stack)) {
                return ExplainedResult.success();
            } else {
                return ExplainedResult.failure(Chats.NOT_MATCHING_GEAR_SLOT.locName());
            }
        }

        return canApplyTo(stack);

    }

    @Override
    public String getRarityId() {
        return rar;
    }

    @Override
    public GearRarity getRarity() {
        return ExileDB.GearRarities().get(rar);
    }


    @Override
    public void setTier(int tier) {
        this.tier = tier;
    }

    @Override
    public void BuildTooltip(TooltipContext ctx) {

    }

    @Override
    public int getLevel() {
        return LevelUtils.levelToTier(tier);
    }

    @Override
    public ItemstackDataSaver<? extends ICommonDataItem> getStackSaver() {
        return StackSaving.STAT_SOULS;
    }

    public ExileTooltips getTooltip(ItemStack stack, boolean cangen) {

        ExileTooltips exileTooltips = new ExileTooltips()
                .accept(new RarityBlock(getRarity()))
                .accept(new UsageBlock(Collections.singletonList(Chats.INFUSES_STATS.locName().withStyle(ChatFormatting.AQUA))));


        if (this.gear != null) {
            //this.gear.BuildTooltip(new TooltipContext(stack, tooltip, Load.Unit(ClientOnly.getPlayer())));
            exileTooltips.accept(new AdditionalBlock(Itemtips.CHECK_GEAR_STATS_IN_SOUL.locName().withStyle(ChatFormatting.AQUA)));
            exileTooltips.accept(new OperationTipBlock().setAlt().setShift());
        } else {

            List<Component> tooltip = new ArrayList<>();
            LevelRange levelRange = LevelUtils.tierToLevel(tier);
            tooltip.add(Itemtips.SOUL_GENERATE_GEAR_LEVEL_RANGE
                    .locName(Component.literal(levelRange.getMinLevel() + ""),
                            Component.literal(levelRange.getMaxLevel() + ""))
                    .withStyle(ChatFormatting.GOLD));
            //tooltip.add(TooltipUtils.gearTier(this.tier));
            if (new StatRangeInfo(ModRange.hide()).hasAltDown) {
                // tooltip.add(Component.literal("[" + Itemtips.MAP_TIER_TIP.locName().getString() + "]").withStyle(ChatFormatting.BLUE));
            }
            if (this.canBeOnAnySlot()) {

            } else {
                if (this.fam != SlotFamily.NONE) {
                    tooltip.add(Itemtips.ITEM_TYPE.locName(Component.literal(this.fam.name()).withStyle(ChatFormatting.BLUE)).withStyle(ChatFormatting.GRAY));
                } else {
                    tooltip.add(Itemtips.ITEM_TYPE.locName(ExileDB.GearSlots()
                            .get(this.slot)
                            .locName().withStyle(ChatFormatting.BLUE)).withStyle(ChatFormatting.GRAY));
                }
            }
            if (!Objects.equals(this.force_tag, "")) {
                SlotTag tag = new SlotTag(this.force_tag);
                tooltip.add(Itemtips.SOUL_LOCKED_TO_TYPE.locName(tag.locName()).withStyle(ChatFormatting.GOLD));
            }
            exileTooltips.accept(new UsageBlock(tooltip));
            exileTooltips.accept(new OperationTipBlock().setShift());
        }
        exileTooltips.accept(WorksOnBlock.usableOn(WorksOnBlock.ItemType.SOULLESS_GEAR));

        exileTooltips
                .accept(new AdditionalBlock(Collections.singletonList(Chats.RIGHT_CLICK_TO_GEN_ITEM.locName().withStyle(ChatFormatting.BLUE))).showWhen(() -> cangen))
                .accept(new SalvageBlock(this, ExileStack.of(stack)));

        return exileTooltips;
    }

    @Override
    public void saveToStack(ItemStack stack) {
        StackSaving.STAT_SOULS.saveTo(stack, this);
    }

    @Override
    public List<ItemStack> getSalvageResult(ExileStack stack) {
        int amount = 1;
        return Arrays.asList(new ItemStack(RarityItems.RARITY_STONE.getOrDefault(getRarity().GUID(), RarityItems.RARITY_STONE.get(IRarity.COMMON_ID)).get(), amount));
    }


    @Override
    public ToggleAutoSalvageRarity.SalvageType getSalvageType() {
        return ToggleAutoSalvageRarity.SalvageType.GEAR;
    }
}
