package com.robertx22.mine_and_slash.mechanics.harvest;

import com.robertx22.library_of_exile.deferred.RegObj;
import com.robertx22.mine_and_slash.mechanics.harvest.vanilla.HarvestMaterialItem;
import com.robertx22.mine_and_slash.mmorpg.registers.deferred_wrapper.Def;
import net.minecraft.world.item.Item;

public class HarvestItems {

    // todo

    static String BLUE_NAME = "Lucid";
    static String PURPLE_NAME = "Chaotic";
    static String GREEN_NAME = "Primal";

    public static RegObj<Item> BLUE_INGOT = Def.item(() -> new HarvestMaterialItem(BLUE_NAME + " Gem of Entanglement"), "harvest/blue_gem");
    public static RegObj<Item> GREEN_INGOT = Def.item(() -> new HarvestMaterialItem(GREEN_NAME + " Gem of Entanglement"), "harvest/green_gem");
    public static RegObj<Item> PURPLE_INGOT = Def.item(() -> new HarvestMaterialItem(PURPLE_NAME + " Gem of Entanglement"), "harvest/purple_gem");


    public static void init() {

    }
}
