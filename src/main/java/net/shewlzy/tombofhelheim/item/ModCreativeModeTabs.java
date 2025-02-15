package net.shewlzy.tombofhelheim.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.shewlzy.tombofhelheim.TombOfHelheim;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TombOfHelheim.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TOMB_OF_HELHEIM = CREATIVE_MODE_TABS.register("tomb_of_helheim",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.DIVAN_ALLOY.get()))
                    .title(Component.translatable("creativetab.tomb_of_helheim"))
                    .displayItems((pParameters, pOutput) -> {
                        //Items
                        pOutput.accept(ModItems.DIVAN_ALLOY.get());

                        //Weapons
                        pOutput.accept(ModItems.HYPERION.get());
                    })
        .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
