package com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases;

import com.robertx22.mine_and_slash.capability.entity.EntityData;
import com.robertx22.mine_and_slash.database.data.spells.components.Spell;
import com.robertx22.mine_and_slash.database.data.spells.entities.CalculatedSpellData;
import com.robertx22.mine_and_slash.saveclasses.unit.Unit;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.effectdatas.SpellStatsCalculationEvent;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.EventData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;

;

public class SpellCastContext {

    public final LivingEntity caster;
    public final EntityData data;
    public final int ticksInUse;
    public final Spell spell;
    public boolean isLastCastTick;
    public boolean castedThisTick = false;
    public SpellStatsCalculationEvent event;
    public CalculatedSpellData calcData;
    public Unit unit;

    public SpellCastContext(LivingEntity caster, int ticksInUse, Spell spell) {
        this.caster = caster;
        this.ticksInUse = ticksInUse;
        this.spell = spell;
        this.data = Load.Unit(caster);

        Objects.requireNonNull(spell);


        this.event = new SpellStatsCalculationEvent(caster, spell.GUID());

        event.Activate();


        this.calcData = event.savedData;

        int castTicks = (int) event.data.getNumber(EventData.CAST_TICKS).number;
        this.isLastCastTick = castTicks == ticksInUse;

        if (caster instanceof Player p) {
            try {
                this.unit = Load.player(p).getSpellUnitStats(p, spell);
            } catch (Exception e) {
                this.unit = this.data.getUnit();
            }
        } else {
            this.unit = this.data.getUnit();
        }

    }
}
