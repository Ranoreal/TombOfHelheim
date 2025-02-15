package com.robertx22.mine_and_slash.database.data.rarities;

import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import net.minecraft.ChatFormatting;

public abstract class BaseRarity implements Rarity {

    public BaseRarity() {
    }


    public int weight;
    public String text_format;
    public String higher_rar = "";

    public transient String loc_name;
    public String guid;

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".rarity." + GUID();
    }

    @Override
    public String GUID() {
        return guid;
    }

    @Override
    public int Weight() {
        return weight;
    }

    @Override
    public ChatFormatting textFormatting() {
        try {
            return ChatFormatting.valueOf(text_format);
        } catch (Exception e) {
            //  e.printStackTrace();
        }
        return ChatFormatting.GRAY;
    }

    @Override
    public String locNameForLangFile() {
        return loc_name;
    }

    public void setCommonFields() {
        this.guid = IRarity.COMMON_ID;
        this.loc_name = "Common";
        this.text_format = ChatFormatting.GRAY.name();
        onSetFields();
    }

    public void setUncommonFields() {
        this.guid = IRarity.UNCOMMON;
        this.loc_name = "Uncommon";
        this.text_format = ChatFormatting.GREEN.name();

        onSetFields();
    }

    public void setRareFields() {
        this.guid = IRarity.RARE_ID;
        this.loc_name = "Rare";
        this.text_format = ChatFormatting.AQUA.name();

        onSetFields();
    }

    public void setEpicFields() {
        this.guid = IRarity.EPIC_ID;
        this.loc_name = "Epic";
        this.text_format = ChatFormatting.LIGHT_PURPLE.name();

        onSetFields();
    }

    public void setLegendFields() {
        this.guid = IRarity.LEGENDARY_ID;
        this.loc_name = "Legendary";
        this.text_format = ChatFormatting.GOLD.name();

        onSetFields();
    }

    public void setMythicFields() {
        this.guid = IRarity.MYTHIC_ID;
        this.loc_name = "Mythic";
        this.text_format = ChatFormatting.DARK_PURPLE.name();

        onSetFields();
    }


    public void setUniqueFields() {
        this.guid = IRarity.UNIQUE_ID;
        this.loc_name = "Unique";
        this.text_format = ChatFormatting.RED.name();

        onSetFields();
    }

    public void setRunewordFields() {
        this.guid = IRarity.RUNEWORD_ID;
        this.loc_name = "Runed";
        this.text_format = ChatFormatting.YELLOW.name();
        onSetFields();
    }

    private void onSetFields() {

    }

}

