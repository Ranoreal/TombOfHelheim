package com.robertx22.mine_and_slash.database.data.gear_types.weapons.mechanics;

import com.robertx22.mine_and_slash.database.data.stats.types.offense.WeaponDamage;
import com.robertx22.mine_and_slash.event_hooks.damage_hooks.util.AttackInformation;
import com.robertx22.mine_and_slash.uncommon.effectdatas.DamageEvent;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EventBuilder;
import com.robertx22.mine_and_slash.uncommon.enumclasses.AttackType;
import com.robertx22.mine_and_slash.uncommon.enumclasses.WeaponTypes;
import com.robertx22.library_of_exile.registry.IGUID;

import java.util.HashMap;

public abstract class WeaponMechanic implements IGUID {

    private static HashMap<String, WeaponMechanic> ALL = new HashMap<String, WeaponMechanic>() {
        {
            {
                put(new NormalWeaponMechanic().GUID(), new NormalWeaponMechanic());
            }
        }
    };

    public static WeaponMechanic get(String id) {
        return ALL.getOrDefault(id, new NormalWeaponMechanic());
    }

    protected void doNormalAttack(AttackInformation data) {

        WeaponTypes weptype = data.weaponData.GetBaseGearType().weaponType();

        int num = (int) data.getAttackerEntityData()
                .getUnit()
                .getCalculatedStat(WeaponDamage.getInstance())
                .getValue();

        DamageEvent dmg = EventBuilder.ofDamage(data, data.getAttackerEntity(), data.getTargetEntity(), num)
                .setupDamage(AttackType.hit, weptype, data.weaponData.GetBaseGearType().style)
                .setIsBasicAttack()
                .build();

        dmg.Activate();

    }

    public void attack(AttackInformation data) {
        doNormalAttack(data);
    }
}
