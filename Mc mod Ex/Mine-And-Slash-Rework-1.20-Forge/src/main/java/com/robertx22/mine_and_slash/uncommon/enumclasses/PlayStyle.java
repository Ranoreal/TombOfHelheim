package com.robertx22.mine_and_slash.uncommon.enumclasses;

import com.robertx22.mine_and_slash.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.tags.all.SlotTags;
import com.robertx22.mine_and_slash.tags.all.SpellTags;
import com.robertx22.mine_and_slash.tags.imp.SlotTag;
import com.robertx22.mine_and_slash.tags.imp.SpellTag;
import com.robertx22.library_of_exile.registry.IGUID;

public enum PlayStyle implements IGUID {

    STR("str", "Melee") {
        @Override
        public Stat getStat() {
            return DatapackStats.STR;
        }

        @Override
        public SpellTag getTag() {
            return SpellTags.melee;
        }

        @Override
        public SlotTag getJewelAffixTag() {
            return SlotTags.jewel_str;
        }
    },
    DEX("dex", "Ranged") {
        @Override
        public Stat getStat() {
            return DatapackStats.DEX;
        }

        @Override
        public SpellTag getTag() {
            return SpellTags.ranged; // todo maybe call it technique
        }

        @Override
        public SlotTag getJewelAffixTag() {
            return SlotTags.jewel_dex;
        }
    },
    INT("int", "Magic") {
        @Override
        public Stat getStat() {
            return DatapackStats.INT;
        }

        @Override
        public SpellTag getTag() {
            return SpellTags.magic;
        }

        @Override
        public SlotTag getJewelAffixTag() {
            return SlotTags.jewel_int;
        }
    };


    public String id;
    public String name;

    public abstract Stat getStat();

    public abstract SpellTag getTag();

    public abstract SlotTag getJewelAffixTag();

    public static PlayStyle fromID(String id) {
        for (PlayStyle value : values()) {
            if (value.id.equals(id)) {
                return value;
            }
        }
        return PlayStyle.STR;
    }

    @Override
    public String GUID() {
        return id;
    }

    PlayStyle(String id, String name) {
        this.id = id;
        this.name = name;
    }

}
