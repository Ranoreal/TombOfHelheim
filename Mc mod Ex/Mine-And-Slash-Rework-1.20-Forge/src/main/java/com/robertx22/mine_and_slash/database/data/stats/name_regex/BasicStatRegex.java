package com.robertx22.mine_and_slash.database.data.stats.name_regex;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import com.robertx22.mine_and_slash.uncommon.localization.Formatter;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import net.minecraft.ChatFormatting;

public class BasicStatRegex extends StatNameRegex {

    @Override
    public String getStatNameRegex(ChatFormatting format, ModType type, Stat stat, float v1) {

        if (stat.is_long) {
            return NAME;
        }


        if (type.isFlat()) {
            return VALUE + NAME;
        }

        if (type.isPercent()) {
            if (stat.IsPercent() && type != ModType.MORE) {
                if (v1 > 0) {
                    return VALUE + Words.INCREASE_PERCENT_STAT.locName().getString() + NAME;
                } else {
                    return VALUE + Words.REDUCE_PERCENT_STAT.locName().getString() + NAME;
                }
            }

            String s = v1 > 0 && (stat.IsPercent() && type != ModType.MORE) ? Words.INCREASE_PERCENT_STAT.locName().getString() : "";
            return VALUE + s + NAME;
        }
        if (type == ModType.MORE) {
            if (v1 > 0) {
                return VALUE + " " + Formatter.SECOND_SPECIAL_CALC_STAT.locName(stat.getMultiUseType().prefixWord.locName(), NAME);
            } else {
                return VALUE + " " + Formatter.SECOND_SPECIAL_CALC_STAT.locName(stat.getMultiUseType().prefixLessWord.locName(), NAME);

            }
        }

        return null;
    }
}
