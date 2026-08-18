package net.aoai.feexperimenting.item;

import net.aoai.feexperimenting.FEExperimenting;
import net.aoai.feexperimenting.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FEExperimenting.MODID);

    public static final Supplier<CreativeModeTab> FEEXPERIMENTING_TAB = CREATIVE_MODE_TAB.register("feex_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("feexperimenting.feex_tab.title"))
                    .icon(() -> new ItemStack(ModBlocks.GENERATOR.asItem()))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.GENERATOR.asItem());
                        output.accept(ModBlocks.ELECTRICAL_FREEZER.asItem());
                        output.accept(ModBlocks.ELECTRICAL_FURNACE.asItem());
                        output.accept(ModItems.ENERGY_DETECTOR);
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
