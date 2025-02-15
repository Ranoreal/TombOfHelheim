package com.robertx22.mine_and_slash.uncommon.testing;

import com.robertx22.mine_and_slash.uncommon.testing.tests.*;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;

public class CommandTests {

    public static void run(String id, ServerPlayer p) {
        try {

            System.out.print("\n");

            System.out.print("Starting test: " + "id" + "\n");

            tests.get(id).runINTERNAL(p);

            System.out.print("Test ended: " + id + "\n");

            System.out.print("\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, CommandTest> tests = new HashMap<>();

    static {
        reg(new CountTalentTreeAttributes());
        reg(new PlayerLevelTest());
        reg(new FindUnusedPerksTest());
        reg(new GivePlayerCapNbt());
        reg(new CheckRunewordConflictTest());
        reg(new GenerateGearTest());


    }

    static void reg(CommandTest t) {
        tests.put(t.GUID(), t);
    }
}
