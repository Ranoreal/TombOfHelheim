package com.robertx22.mine_and_slash.uncommon.testing.tests;

import com.robertx22.mine_and_slash.database.OptScaleExactStat;
import com.robertx22.mine_and_slash.database.data.perks.Perk;
import com.robertx22.mine_and_slash.database.data.talent_tree.TalentTree;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.uncommon.testing.CommandTest;
import com.robertx22.mine_and_slash.uncommon.testing.TestResult;
import net.minecraft.server.level.ServerPlayer;

import java.util.*;

public class CountTalentTreeAttributes extends CommandTest {

    @Override
    public TestResult runINTERNAL(ServerPlayer player) {

        TalentTree tree = ExileDB.TalentTrees()
                .get("talents");

        List<OptScaleExactStat> stats = new ArrayList<>();

        int totalperks = tree.calcData.perks.size();

        int notables = 0;
        int gamechangers = 0;

        Set<Perk> diffPerks = new HashSet<>();

        for (String x1 : tree.calcData.perks.values()) {
            Perk perk = ExileDB.Perks()
                    .get(x1);

            if (perk != null) {
                perk.stats.forEach(s -> {
                    if (s.v1 > 0) {
                        stats.add(s);
                    }
                });

                if (perk.type == Perk.PerkType.SPECIAL) {
                    notables++;
                }
                if (perk.type == Perk.PerkType.MAJOR) {
                    gamechangers++;
                }

                diffPerks.add(perk);
            }

        }
        int differentPerks = diffPerks.size();

        OptScaleExactStat.combine(stats);
        stats.sort(Comparator.comparingInt(x -> (int) -x.v1));

        System.out.print("" + "\n");

        System.out.print("Talent tree statistics:" + "\n");

        System.out.print("Total perks: " + totalperks + "\n");
        System.out.print("Different perks: " + differentPerks + "\n");
        System.out.print("Total notable perks: " + notables + "\n");
        System.out.print("Total game changer perks: " + gamechangers + "\n");

        System.out.print("" + "\n");

        System.out.print("Total stats: " + "\n");

        for (OptScaleExactStat x : stats) {
            System.out.print(x.getDebugString() + "\n");

        }

        return TestResult.SUCCESS;
    }

    @Override
    public boolean shouldRunEveryLogin() {
        return false;
    }

    @Override
    public String GUID() {
        return "talent_tree_info";
    }
}
