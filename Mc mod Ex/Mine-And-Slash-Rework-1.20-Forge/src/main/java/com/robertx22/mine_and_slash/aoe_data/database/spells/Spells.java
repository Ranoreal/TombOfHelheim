package com.robertx22.mine_and_slash.aoe_data.database.spells;

import com.robertx22.mine_and_slash.aoe_data.database.spells.impl.*;
import com.robertx22.mine_and_slash.aoe_data.database.spells.schools.*;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class Spells implements ExileRegistryInit {

    @Override
    public void registerAll() {

        new BossSpells().registerAll();

        new TestSpells().registerAll();
        new CurseSpells().registerAll();
        new SongSpells().registerAll();
        new DexSpells().registerAll();
        new IntSpells().registerAll();
        new StrSpells().registerAll();
        new TotemSpells().registerAll();

        new LightningSpells().registerAll();
        new FireSpells().registerAll();
        new NatureSpells().registerAll();
        new WaterSpells().registerAll();
        new HolySpells().registerAll();
        new RangerSpells().registerAll();
        new BasicAttackSpells().registerAll();
        new PetSpells().registerAll();

        new SummonSpells().registerAll();
        ProcSpells.init();
    }
}
