package com.robertx22.mine_and_slash.database.data.stats.types.ailment;

import com.robertx22.mine_and_slash.aoe_data.database.ailments.Ailment;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.StatGuiGroup;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;

public class AilmentDuration extends Stat {

    Ailment ailment;

    public AilmentDuration(Ailment ailment) {
        this.ailment = ailment;
        this.is_perc = true;
        this.gui_group = StatGuiGroup.AILMENT_DURATION;
    }

    @Override
    public Elements getElement() {
        return ailment.element;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases duration. For DOTs, this does increase the total damage dealt, but it doesn't increase damage per second.";
    }

    @Override
    public String locNameForLangFile() {
        return ailment.locNameForLangFile() + " Duration";
    }

    @Override
    public String GUID() {
        return ailment.GUID() + "_duration";
    }
}
