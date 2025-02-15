package com.robertx22.mine_and_slash.database.base;

import com.robertx22.library_of_exile.registry.IWeighted;

import java.util.List;

public interface IRandom<T extends IhasRequirements & IWeighted, Config> {

    List<T> All();

    public T random(Config gearRequestedFor);

    public T random();

    public List<T> allThatMeetRequirement(List<T> list, Config gearRequestedFor);

}
