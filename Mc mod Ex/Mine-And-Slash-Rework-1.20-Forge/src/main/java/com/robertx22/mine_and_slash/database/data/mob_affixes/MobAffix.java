package com.robertx22.mine_and_slash.database.data.mob_affixes;

import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.database.data.affixes.Affix;
import com.robertx22.mine_and_slash.database.registry.ExileRegistryTypes;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.IStatCtx;
import com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.SimpleStatCtx;
import com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocName;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MobAffix implements JsonExileRegistry<MobAffix>, IAutoGson<MobAffix>, IAutoLocName, IStatCtx {

    public Affix.AffixSlot type = Affix.AffixSlot.prefix;
    List<StatMod> stats = new ArrayList<>();
    String id = "";
    int weight = 1000;
    public String icon = "";
    public ChatFormatting format;
    transient String locName;

    public MobAffix(String id, String locName, ChatFormatting format, Affix.AffixSlot type) {
        this.id = id;
        this.type = type;
        this.locName = locName;
        this.format = format;
    }

    public MobAffix setMods(StatMod... mods) {
        this.stats = Arrays.asList(mods);
        return this;
    }

    public MobAffix icon(String icon) {
        this.icon = icon;
        return this;
    }

    public MobAffix setWeight(int weight) {
        this.weight = weight;
        return this;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.MOB_AFFIX;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public int Weight() {
        return weight;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".mob_affix." + GUID();
    }

    @Override
    public String locNameForLangFile() {
        return locName;
    }

    @Override
    public List<StatContext> getStatAndContext(LivingEntity en) {
        List<ExactStatData> stats = new ArrayList<>();
        try {
            this.stats.forEach(x -> stats.add(x.ToExactStat(100, Load.Unit(en)
                    .getLevel())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Arrays.asList(new SimpleStatCtx(StatContext.StatCtxType.MOB_AFFIX, stats));
    }

    @Override
    public Class<MobAffix> getClassForSerialization() {
        return MobAffix.class;
    }
}
