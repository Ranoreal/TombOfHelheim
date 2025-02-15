package com.robertx22.mine_and_slash.database.data.spells.components.selectors;

import com.robertx22.mine_and_slash.database.data.spells.components.BaseFieldNeeder;
import com.robertx22.mine_and_slash.database.data.spells.components.MapHolder;
import com.robertx22.mine_and_slash.database.data.spells.map_fields.MapField;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.library_of_exile.registry.IGUID;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.List;

public abstract class BaseTargetSelector extends BaseFieldNeeder implements IGUID {

    public BaseTargetSelector(List<MapField> requiredPieces) {
        super(requiredPieces);
    }

    public abstract List<LivingEntity> get(SpellCtx ctx, LivingEntity caster, LivingEntity target, Vec3 pos, MapHolder data);

    public static HashMap<String, BaseTargetSelector> MAP = new HashMap<>();

    public static CasterSelector CASTER = of(new CasterSelector());
    public static TargetSelector TARGET = of(new TargetSelector());
    public static AoeSelector AOE = of(new AoeSelector());
    public static InFrontSelector IN_FRONT = of(new InFrontSelector());
    public static RayCastSelector RAY_CAST = of(new RayCastSelector());

    private static <T extends BaseTargetSelector> T of(T s) {
        MAP.put(s.GUID(), s);
        return s;

    }
}
