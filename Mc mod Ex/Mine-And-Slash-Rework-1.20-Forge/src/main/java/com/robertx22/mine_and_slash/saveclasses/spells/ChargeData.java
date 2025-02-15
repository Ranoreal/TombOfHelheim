package com.robertx22.mine_and_slash.saveclasses.spells;

import com.robertx22.library_of_exile.main.ExileLog;
import com.robertx22.mine_and_slash.database.data.spells.components.Spell;
import com.robertx22.mine_and_slash.uncommon.MathHelper;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.Cached;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChargeData {

    private HashMap<String, List<CdData>> cds = new HashMap<>();

    static class CdData {

        String id;
        Integer ticks;

        public CdData(String id, Integer ticks) {
            this.id = id;
            this.ticks = ticks;
        }
    }

    public int getCurrentTicksChargingOf(String id) {
        if (cds.containsKey(id)) {
            if (cds.get(id).size() > 0) {
                return cds.get(id).get(0).ticks;
            }
        }
        return 0;
    }

    public boolean hasCharge(String id) {
        return getCharges(id) > 0;
    }


    void add(String id, int cd) {
        addList(id);
        cds.get(id).add(new CdData(id, cd));
    }

    void addList(String id) {
        if (!cds.containsKey(id)) {
            cds.put(id, new ArrayList<>());
        }
    }

    public void spendCharge(Player player, Spell spell, int cd) {

        if (player.level().isClientSide) {
            return;
        }
        String id = spell.config.charge_name;

        int fn = MathHelper.clamp(cd, 0, 100000);
        
        add(id, fn);

        Load.player(player).playerDataSync.setDirty();
    }


    static List<CdData> empty = new ArrayList<>();

    public int getCharges(String id) {
        int oncd = (int) cds.getOrDefault(id, empty).stream().count();

        if (!Cached.MAX_SPELL_CHARGES.containsKey(id)) {
            ExileLog.get().log("Spell has no charges possible or the max spell charges aren't cached!");
        }

        int charges = Cached.MAX_SPELL_CHARGES.getOrDefault(id, 0);

        charges -= oncd;

        charges = MathHelper.clamp(charges, 0, 100);

        return charges;
    }


    public void addOneCharges() {

        for (Map.Entry<String, List<CdData>> en : cds.entrySet()) {
            if (en.getValue().size() > 0) {
                en.getValue().remove(0);
            }
        }

    }

    public void onTicks(Player player, int ticks) {

        if (player.level().isClientSide) {
            return;
        }

        boolean sync = false;

        for (Map.Entry<String, List<CdData>> en : cds.entrySet()) {
            for (CdData cd : en.getValue()) {
                cd.ticks -= ticks;
            }
            if (en.getValue().removeIf(x -> x.ticks < 1)) {
                sync = true;
            }
        }

        if (sync) {

            Load.player(player).playerDataSync.setDirty();
        }
    }
}
