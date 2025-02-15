package com.robertx22.mine_and_slash.database.data.support_gem;

import com.robertx22.mine_and_slash.capability.entity.EntityData;
import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.database.registry.ExileRegistryTypes;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.skill_gem.ISkillGem;
import com.robertx22.mine_and_slash.saveclasses.skill_gem.SkillGemData;
import com.robertx22.mine_and_slash.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class SupportGem implements ISkillGem, JsonExileRegistry<SupportGem>, IAutoGson<SupportGem> {

    public static SupportGem SER = new SupportGem("", "", PlayStyle.STR, 0, Arrays.asList());
    public String id = "";
    public transient String locname = "";

    public PlayStyle style = PlayStyle.DEX;

    public int min_lvl = 1;

    public float manaMulti = 0.25F;

    public List<StatMod> stats = new ArrayList<>();

    public int weight = 1000;

    public String one_of_a_kind = "";

    public SupportGem(String id, String name, PlayStyle style, float manaMulti, List<StatMod> stats) {
        this.id = id;
        this.locname = name + " Support Gem";
        this.style = style;
        this.manaMulti = manaMulti;
        this.stats = stats;
    }

    public boolean isOneOfAKind() {
        return !one_of_a_kind.isEmpty();
    }

    public SupportGem setOneOfAKind(String id) {
        this.one_of_a_kind = id;
        return this;
    }

    public SupportGem levelReq(int lvl) {
        this.min_lvl = lvl;
        return this;
    }

    public SupportGem edit(Consumer<SupportGem> s) {
        s.accept(this);
        return this;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.SUPPORT_GEM;
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
        return AutoLocGroup.Spells;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".support_gem." + GUID();
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }

    public List<ExactStatData> GetAllStats(EntityData en, SkillGemData data) {
        return this.stats
                .stream()
                .map(x -> x.ToExactStat(data.getStatPercent(), en.getLevel()))
                .collect(Collectors.toList());

    }

    @Override
    public int getRequiredLevel() {
        return min_lvl;
    }

    @Override
    public PlayStyle getStyle() {
        return style;
    }

    @Override
    public Class<SupportGem> getClassForSerialization() {
        return SupportGem.class;
    }
}
