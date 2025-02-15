package com.robertx22.mine_and_slash.database.data.spells.components;

import com.google.common.base.Preconditions;
import com.robertx22.library_of_exile.registry.IGUID;
import com.robertx22.mine_and_slash.database.data.spells.map_fields.MapField;

import java.util.List;

public abstract class BaseFieldNeeder implements IGUID {

    List<MapField> requiredPieces;

    public BaseFieldNeeder(List<MapField> requiredPieces) {
        this.requiredPieces = requiredPieces;
    }

    public void validate(MapHolder holder) {
        if (holder.type.equals(GUID())) {
            Preconditions.checkArgument(requiredPieces.stream().allMatch(x -> holder.get(x) != null));
        }
    }

}
