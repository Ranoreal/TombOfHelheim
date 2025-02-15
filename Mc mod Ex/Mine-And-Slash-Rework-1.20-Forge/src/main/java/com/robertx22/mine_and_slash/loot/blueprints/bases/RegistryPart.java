package com.robertx22.mine_and_slash.loot.blueprints.bases;

import com.robertx22.library_of_exile.registry.Database;
import com.robertx22.library_of_exile.registry.ExileRegistry;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.mine_and_slash.loot.blueprints.ItemBlueprint;

import java.util.function.Predicate;

public class RegistryPart<Registry extends ExileRegistry> extends BlueprintPart<Registry, ItemBlueprint> {

    public ExileRegistryType regType;
    public Predicate<Registry> pred;

    public RegistryPart(ItemBlueprint blueprint, ExileRegistryType regType, Predicate<Registry> pred) {
        super(blueprint);
        this.regType = regType;
        this.pred = pred;
    }


    @Override
    protected Registry generateIfNull() {
        return (Registry) Database.getRegistry(regType).getFilterWrapped(x -> pred.test((Registry) x)).random();

    }

    public void set(String id) {
        super.set((Registry) Database.get(regType, id));
    }

}


