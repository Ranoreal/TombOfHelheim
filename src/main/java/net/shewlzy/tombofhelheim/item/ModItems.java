package net.shewlzy.tombofhelheim.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.shewlzy.tombofhelheim.TombOfHelheim;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TombOfHelheim.MOD_ID);
    //Items
    public static final RegistryObject<Item> DIVAN_ALLOY = ITEMS.register("divan_alloy",
            () -> new Item(new Item.Properties()));

    //Weapons
    public static final RegistryObject<Item> HYPERION = ITEMS.register("hyperion",
            () -> new SwordItem(ModToolTiers.DIVAN, 107, 7, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
