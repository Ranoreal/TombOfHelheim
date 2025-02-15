package com.robertx22.mine_and_slash.uncommon.effectdatas;

import com.robertx22.library_of_exile.registry.IGUID;
import com.robertx22.mine_and_slash.capability.entity.EntityData;
import com.robertx22.mine_and_slash.capability.player.data.PlayerConfigData;
import com.robertx22.mine_and_slash.database.data.spells.components.Spell;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.datapacks.test.DataPackStatEffect;
import com.robertx22.mine_and_slash.database.data.stats.datapacks.test.DatapackStat;
import com.robertx22.mine_and_slash.database.data.stats.layers.StatLayer;
import com.robertx22.mine_and_slash.database.data.stats.layers.StatLayerData;
import com.robertx22.mine_and_slash.database.data.stats.priority.StatPriority;
import com.robertx22.mine_and_slash.database.data.stats.types.UnknownStat;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.saveclasses.unit.Unit;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.effectdatas.base.EffectWithCtx;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.EventData;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;
import com.robertx22.mine_and_slash.uncommon.interfaces.IStatEffect;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public abstract class EffectEvent implements IGUID {
    public boolean disableActivation = false;

    public boolean calcSourceEffects = true;
    public boolean calcTargetEffects = true;

    public EntityData sourceData;
    public EntityData targetData;

    public LivingEntity source;
    public LivingEntity target;

    private boolean effectsCalculated = false;

    public EventData data = new EventData();

    public abstract String getName();

    private boolean activated = false;

    private HashMap<String, StatLayerData> layers = new HashMap<>(); // todo use this later

    private List<MoreMultiData> moreMultis = new ArrayList<>(); // todo use this later

    public List<MoreMultiData> getMoreMultis() {
        return moreMultis;
    }

    public void addMoreMulti(Stat stat, String number, float multi) {
        addMoreMulti(Component.literal("Stat: ").append(stat.locName()), number, multi);
    }

    public void addMoreMulti(MutableComponent text, String number, float multi) {
        if (multi != 1) {
            moreMultis.add(new MoreMultiData(text, multi, number));
        }
    }

    public static class MoreMultiData {

        public MutableComponent text;
        public float multi = 1;
        public String numberid = "";

        public MoreMultiData(MutableComponent text, float multi, String numberid) {
            this.text = text;
            this.multi = multi;
            this.numberid = numberid;
        }
    }


    public StatLayerData getLayer(StatLayer layer, String number, EffectSides side) {
        String id = layer.GUID() + "_" + number;
        if (!layers.containsKey(id)) {
            var data = new StatLayerData(layer.GUID(), number, 0, side);

            layers.put(id, data);
        }
        return layers.get(id);

    }

    public StatLayerData getConversionLayer(StatLayer layer, Elements ele, String number, EffectSides side) {
        String id = layer.GUID() + "_" + number + "_" + ele.GUID();
        if (!layers.containsKey(id)) {
            var data = new StatLayerData(layer.GUID(), number, 0, side);

            layers.put(id, data);
        }
        return layers.get(id);

    }

    public EffectEvent(float num, LivingEntity source, LivingEntity target) {
        this(source, target);

        data.setupNumber(EventData.NUMBER, num);

    }

    public EffectEvent(LivingEntity source, LivingEntity target) {

        this.source = source;
        this.target = target;

        if (target != null && source != null) {
            this.targetData = Load.Unit(target);
            this.sourceData = Load.Unit(source);
        } else {
            this.data.setBoolean(EventData.CANCELED, true);
        }
    }

    public boolean isSpell() {
        return data.isSpellEffect();
    }

    public Spell getSpell() {
        return ExileDB.Spells().get(data.getString(EventData.SPELL));
    }

    public Spell getSpellOrNull() {
        if (!isSpell()) {
            return null;
        }
        return ExileDB.Spells().get(data.getString(EventData.SPELL));
    }

    public void initBeforeActivating() {

    }


    public boolean canWorkOnDeadTarget() {
        return false;
    }


    public void increaseByPercent(String num, float perc) {
        data.getNumber(num).number += data.getOriginalNumber(num).number * perc / 100F;
    }

    public void Activate() {
        if (!activated) {

            if (this.source.isDeadOrDying() || target.isDeadOrDying()) {
                if (false && !canWorkOnDeadTarget()) {
                    // todo, enabled dead entities to work with events so on death stats can work, will see if any problems arise
                    this.activated = true;
                    return;
                }
            }

            //Watch watch = new Watch();
            //watch.min = 500;

            initBeforeActivating();
            calculateEffects();
            data.freeze();

            if (!data.isCanceled()) {
                if (!disableActivation) {
                    activate();
                }
                this.activated = true;
            }

            // watch.print("stat events " + GUID() + " ");
        }

    }

    public List<StatLayerData> getSortedLayers() {
        List<StatLayerData> all = new ArrayList<>();
        all.addAll(layers.values());
        all.sort(Comparator.comparingInt(x -> x.getLayer().priority));
        return all;
    }

    //  this shouldnt be calculated at the end.. stats like magic shield depend on it, that's why i call it with a custom stat
    private void calculateStatLayersAndMoreMultis() {
        if (!this.layers.isEmpty()) {
            List<StatLayerData> all = getSortedLayers();

            for (StatLayerData layer : all) {
                layer.getLayer().action.apply(this, layer, layer.numberID);
            }
        }
        if (!this.moreMultis.isEmpty()) {
            for (MoreMultiData more : this.moreMultis) {
                this.data.getNumber(more.numberid).number *= more.multi;
            }
        }
    }

    public void calculateEffects() {
        if (source.level().isClientSide) {
            return; // todo is this fine? spell calc seems to be called on client every tick!
        }
        if (!effectsCalculated) {
            effectsCalculated = true;
            if (target == null || data.isCanceled() || sourceData == null || targetData == null) {
                return;
            }

            if (data.isCanceled()) {
                return;
            }
            // todo something is weird, why are some stats used 2 times here?

            List<EffectWithCtx> effectsWithCtx = new ArrayList<>();
            if (calcSourceEffects) {
                effectsWithCtx = AddEffects(effectsWithCtx, sourceData, source, EffectSides.Source);
            }
            if (calcTargetEffects) {
                effectsWithCtx = AddEffects(effectsWithCtx, targetData, target, EffectSides.Target);
            }
            effectsWithCtx.add(CALC_LAYERS_EFFECT);
            effectsWithCtx.sort(EffectWithCtx.COMPARATOR);

            testEffectsAreOrderedCorrectly(effectsWithCtx);

            for (EffectWithCtx item : effectsWithCtx) {
                if (item.stat.isNotZero()) {
                    item.effect.TryModifyEffect(this, item.statSource, item.stat, item.stat.GetStat());
                } else {
                    System.out.print("ERORR cant be zero! ");
                }
            }


        }

    }

    public void testEffectsAreOrderedCorrectly(List<EffectWithCtx> sortedEffects) {

        if (this.source instanceof Player p && Load.player(p).config.isConfigEnabled(PlayerConfigData.Config.STAT_ORDER_TEST)) {
            List<String> toremove = new ArrayList<>();

            HashMap<String, Integer> map = new HashMap<>();
            int i = 0;
            for (EffectWithCtx sort : sortedEffects) {
                map.put(sort.stat.GetStat().GUID(), i);
                i++;
            }

            for (StatOrderTest.FirstAndSecond test : StatOrderTest.getAll()) {
                String id = test.test(map, source.level());
                if (!id.isEmpty()) {
                    toremove.add(id);
                }
            }

            for (String s : toremove) {
                StatOrderTest.removeTest(s);
            }
        }
    }


    protected abstract void activate();


    public LivingEntity getSide(EffectSides side) {
        if (side == EffectSides.Source) {
            return source;
        } else {
            return target;
        }
    }

    private static EffectWithCtx CALC_LAYERS_EFFECT = new EffectWithCtx(new IStatEffect() {
        @Override
        public boolean worksOnEvent(EffectEvent ev) {
            return true;
        }

        @Override
        public EffectSides Side() {
            return EffectSides.Source;
        }

        @Override
        public StatPriority GetPriority() {
            return StatPriority.Damage.CALC_DAMAGE_LAYERS;
        }

        @Override
        public void TryModifyEffect(EffectEvent effect, EffectSides statSource, StatData data, Stat stat) {
            effect.calculateStatLayersAndMoreMultis();
        }
    }, EffectSides.Source, new StatData(new UnknownStat().GUID(), 1, 1));


    private List<EffectWithCtx> AddEffects(List<EffectWithCtx> effects, EntityData enData, LivingEntity en, EffectSides side) {

        if (enData == null) {
            return effects;
        }


        Unit un = enData.getUnit();


        if (side == EffectSides.Source) {

            if (isSpell()) {
                if (en instanceof Player p) {
                    if (getSpell() != null) {
                        un = Load.player(p).getSpellUnitStats(p, getSpell());
                    }
                }
            }
        }

        List<StatData> list = new ArrayList<>();

        un.getStats().stats
                .values()
                .forEach(data -> {
                    if (data.isNotZero()) {
                        Stat stat = data.GetStat();

                        if (stat instanceof DatapackStat) {
                            DatapackStat d = (DatapackStat) stat;
                            for (DataPackStatEffect eff : d.effect) {
                                if (eff.worksOnEvent(this)) {
                                    if (eff.Side().equals(side)) {
                                        effects.add(new EffectWithCtx(eff, side, data));
                                        list.add(data);
                                    }
                                }
                            }

                        } else {
                            IStatEffect effect = null;

                            if (stat.statEffect != null) {
                                if (stat.statEffect.Side().equals(side)) {
                                    if (stat.statEffect.worksOnEvent(this)) {
                                        effect = stat.statEffect;
                                    }
                                }
                            }
                            if (effect != null) {
                                effects.add(new EffectWithCtx(effect, side, data));
                                list.add(data);
                            }
                        }


                    }
                });


        return effects;
    }

}