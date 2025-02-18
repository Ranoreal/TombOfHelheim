package com.robertx22.mine_and_slash.database.data.profession.items;

import com.robertx22.addons.orbs_of_crafting.currency.IItemAsCurrency;
import com.robertx22.addons.orbs_of_crafting.currency.base.CodeCurrency;
import com.robertx22.addons.orbs_of_crafting.currency.base.GearCurrency;
import com.robertx22.addons.orbs_of_crafting.currency.base.GearOutcome;
import com.robertx22.addons.orbs_of_crafting.currency.base.OutcomeType;
import com.robertx22.library_of_exile.registry.IWeighted;
import com.robertx22.library_of_exile.util.ExplainedResult;
import com.robertx22.library_of_exile.util.UNICODE;
import com.robertx22.mine_and_slash.database.data.affixes.Affix;
import com.robertx22.mine_and_slash.database.data.gear_types.bases.SlotFamily;
import com.robertx22.mine_and_slash.database.data.profession.ICreativeTabTiered;
import com.robertx22.mine_and_slash.database.data.profession.LeveledItem;
import com.robertx22.mine_and_slash.database.data.profession.all.Professions;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.database.data.requirements.bases.GearRequestedFor;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.gui.texts.ExileTooltips;
import com.robertx22.mine_and_slash.gui.texts.textblocks.NameBlock;
import com.robertx22.mine_and_slash.gui.texts.textblocks.RarityBlock;
import com.robertx22.mine_and_slash.gui.texts.textblocks.RequirementBlock;
import com.robertx22.mine_and_slash.gui.texts.textblocks.WorksOnBlock;
import com.robertx22.mine_and_slash.gui.texts.textblocks.dropblocks.ProfessionDropSourceBlock;
import com.robertx22.mine_and_slash.gui.texts.textblocks.usableitemblocks.UsageBlock;
import com.robertx22.mine_and_slash.itemstack.CustomItemData;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.itemstack.StackKeys;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts.GearInfusionData;
import com.robertx22.mine_and_slash.tags.all.SlotTags;
import com.robertx22.mine_and_slash.uncommon.interfaces.IRarityItem;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import com.robertx22.mine_and_slash.uncommon.localization.Itemtips;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.StringUTIL;
import com.robertx22.mine_and_slash.vanilla_mc.items.misc.AutoItem;
import com.robertx22.orbs_of_crafting.misc.LocReqContext;
import com.robertx22.temp.SkillItemTier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CraftedInfusionItem extends AutoItem implements IRarityItem, IItemAsCurrency, ICreativeTabTiered, IRarity {

    public SlotFamily fam;
    String rar;

    public CraftedInfusionItem(SlotFamily fam, String rar) {
        super(new Properties());
        this.rar = rar;
        this.fam = fam;

    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> l, TooltipFlag pIsAdvanced) {
        var tier = LeveledItem.getTier(pStack);
        l.clear();
        l.addAll(new ExileTooltips()
                .accept(WorksOnBlock.usableOn(WorksOnBlock.ItemType.GEAR))
                .accept(new NameBlock(Collections.singletonList(pStack.getHoverName())))
                .accept(new RarityBlock(getRarity()))
                .accept(new UsageBlock(() -> {
                    List<GearOutcome> outcomes = ((GearCurrency) this.currencyEffect(pStack)).getOutcomes();
                    return outcomes.stream()
                            .map(x -> x.getTooltip(outcomes.stream().mapToInt(IWeighted::Weight).sum()).withStyle(ChatFormatting.GRAY))
                            .toList();

                }))
                .accept(new ProfessionDropSourceBlock(Professions.INFUSING))
                .accept(new UsageBlock(() -> Arrays.asList(Chats.ENCHANT_UPGRADE_RARITY.locName().withStyle(ChatFormatting.BLUE))))
                .accept(new RequirementBlock(Collections.singletonList(Itemtips.INFUSION_GEAR_LEVEL_RANGE.locName(tier.levelRange.getMinLevel(), tier.levelRange.getMaxLevel())), UNICODE.ROTATED_CUBE + " "))
                //  .accept(new LeveledItemBlock(pStack))
                .release());
    }

    @Override
    public String locNameForLangFile() {
        return StringUTIL.capitalise(rar) + " " + fam.name() + " Infusion";
    }

    @Override
    public String GUID() {
        return "infusion";
    }

    @Override
    public CodeCurrency currencyEffect(ItemStack stack) {
        return new GearCurrency() {
            @Override
            public List<GearOutcome> getOutcomes() {
                return Arrays.asList(
                        new GearOutcome() {
                            @Override
                            public Words getName() {
                                return Words.UpgradeInfusion;
                            }

                            @Override
                            public OutcomeType getOutcomeType() {
                                return OutcomeType.GOOD;
                            }

                            @Override
                            public void modify(LocReqContext ctx) {

                                ExileStack ex = ExileStack.of(ctx.stack);

                                ex.get(StackKeys.GEAR).edit(gear -> {

                                    boolean makenew = gear.ench == null || gear.ench.isEmpty();

                                    if (makenew) {
                                        gear.ench = new GearInfusionData();

                                        Affix affix = ExileDB.Affixes().getFilterWrapped(x -> {
                                            return x.type == Affix.AffixSlot.enchant && x.requirements.satisfiesAllRequirements(new GearRequestedFor(gear)) && x.getAllTagReq().contains(SlotTags.enchantment.GUID());
                                        }).random();
                                        gear.ench.en = affix.GUID();
                                    } else {
                                        gear.ench.rar = rar;
                                    }

                                    ex.get(StackKeys.CUSTOM).edit(x -> CustomItemData.KEYS.ENCHANT_TIMES.add(x, 1));

                                });

                                ctx.stack = ex.getStack();
                            }

                            @Override
                            public int Weight() {
                                return 1000;
                            }

                        },
                        new GearOutcome() {
                            @Override
                            public Words getName() {
                                return Words.Nothing;
                            }

                            @Override
                            public OutcomeType getOutcomeType() {
                                return OutcomeType.BAD;
                            }

                            @Override
                            public void modify(LocReqContext ctx) {
                                ExileStack ex = ExileStack.of(ctx.stack);
                                ex.get(StackKeys.CUSTOM).edit(x -> CustomItemData.KEYS.ENCHANT_TIMES.add(x, 1));
                                ctx.stack = ex.getStack();
                            }

                            @Override
                            public int Weight() {
                                return 1000;
                            }
                        }
                );
            }

            @Override
            public int getPotentialLoss() {
                return 0;
            }

            // todo turn this into a result with chat messages why it doesnt work
            @Override
            public ExplainedResult canBeModified(LocReqContext c) {

                ExileStack stack = ExileStack.of(c.stack);

                var data = stack.get(StackKeys.GEAR).get();

                SkillItemTier tier = LeveledItem.getTier(c.Currency);

                if (!tier.levelRange.isLevelInRange(data.lvl)) {
                    return ExplainedResult.failure(Chats.NOT_CORRECT_TIER_LEVEL.locName());
                }

                if (data.GetBaseGearType().family() != fam) {
                    return ExplainedResult.failure(Chats.NOT_FAMILY.locName());
                }

                if (data.ench == null) {
                    if (!IRarity.COMMON_ID.equals(rar)) {
                        return ExplainedResult.failure(Chats.ENCHANT_UPGRADE_RARITY.locName());
                    }
                } else {
                    if (data.ench.rar.equals(rar)) {
                        return ExplainedResult.failure(Chats.ENCHANT_UPGRADE_RARITY_CANT_SAME.locName());
                    }
                    if (!data.ench.canUpgradeToRarity(rar)) {
                        return ExplainedResult.failure(Chats.ENCHANT_UPGRADE_RARITY.locName());
                    }
                }


                if (stack.get(StackKeys.CUSTOM).getOrCreate().data.get(CustomItemData.KEYS.ENCHANT_TIMES) > 9) {
                    return ExplainedResult.failure(Chats.THIS_ITEM_CANT_BE_USED_MORE_THAN_X_TIMES.locName(10));
                }

                return ExplainedResult.success();
            }

            @Override
            public String locDescForLangFile() {
                return "Tries to Infuse the item, adding stats. Item can only attempt infusion 10 times. As this can fail, some items will never reach maximum rarity Infusions." +
                        "The item must be infused through the rarities progressively. So first you have to infuse a common enchant, then an uncommon etc";
            }

            @Override
            public String locNameForLangFile() {
                return "";
            }

            @Override
            public String GUID() {
                return "infusion";
            }

            @Override
            public int Weight() {
                return 0;
            }
        };
    }

    @Override
    public Item getThis() {
        return this;
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
    public GearRarity getItemRarity(ItemStack stack) {
        return ExileDB.GearRarities().get(rar);
    }
}
