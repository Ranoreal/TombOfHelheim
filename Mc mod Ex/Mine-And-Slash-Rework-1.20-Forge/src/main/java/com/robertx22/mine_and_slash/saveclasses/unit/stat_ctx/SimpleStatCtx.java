package com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx;

import com.robertx22.mine_and_slash.saveclasses.ExactStatData;

import java.util.List;

public class SimpleStatCtx extends StatContext {

    public SimpleStatCtx(StatCtxType type, List<ExactStatData> stats) {
        super(type, stats);
    }

    public SimpleStatCtx(StatCtxType type, String gear_slot, List<ExactStatData> stats) {
        super(type, gear_slot, stats);
    }
}

