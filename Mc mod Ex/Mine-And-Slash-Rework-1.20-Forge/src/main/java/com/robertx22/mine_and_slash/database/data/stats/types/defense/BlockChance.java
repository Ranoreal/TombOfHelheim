package com.robertx22.mine_and_slash.database.data.stats.types.defense;

import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.mine_and_slash.database.data.stats.layers.StatLayers;
import com.robertx22.mine_and_slash.database.data.stats.priority.StatPriority;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.DamageEvent;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.EventData;
import com.robertx22.mine_and_slash.uncommon.enumclasses.AttackType;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.ShieldItem;

public class BlockChance extends Stat {

    public static String GUID = "block_chance";

    public static BlockChance getInstance() {
        return BlockChance.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Chance to passively block hit damage and reduce the damage taken by 50%. Requires a shield in offhand. Blocked hits can't proc ailments like burn."; //, requires a shield in the offhand, but don't have to activate it.";
    }

    private BlockChance() {
        this.min = 0;
        this.max = 75;
        this.group = StatGroup.MAIN;

        this.statEffect = new BlockChance.Effect();

        this.format = ChatFormatting.BLUE.getName();

    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public Elements getElement() {
        return Elements.Physical;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return "Block Chance";
    }

    private static class Effect extends BaseDamageEffect {

        @Override
        public StatPriority GetPriority() {
            return StatPriority.Damage.DAMAGE_LAYERS;
        }

        @Override
        public EffectSides Side() {
            return EffectSides.Target;
        }

        @Override
        public DamageEvent activate(DamageEvent effect, StatData data, Stat stat) {
            float chance = data.getValue();
            if (RandomUtils.roll(chance)) {
                effect.getLayer(StatLayers.Defensive.DAMAGE_SUPPRESSION, EventData.NUMBER, Side()).reduce(50);
                effect.data.setBoolean(EventData.IS_BLOCKED, true);
            }
            return effect;
        }

        @Override
        public boolean canActivate(DamageEvent effect, StatData data, Stat stat) {
            return effect.getAttackType().isHit() || effect.getAttackType() == AttackType.bonus_dmg && effect.target.getOffhandItem().getItem() instanceof ShieldItem;// todo ask for shield mns item
        }
    }

    private static class SingletonHolder {
        private static final BlockChance INSTANCE = new BlockChance();
    }
}
