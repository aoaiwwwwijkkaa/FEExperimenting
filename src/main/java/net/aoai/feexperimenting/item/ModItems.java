package net.aoai.feexperimenting.item;

import net.aoai.feexperimenting.FEExperimenting;
import net.aoai.feexperimenting.item.custom.EnergyDetector;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(FEExperimenting.MODID);

    public static final DeferredItem<Item> ENERGY_DETECTOR = ITEMS.register("energy_detector",
            () -> new EnergyDetector(new Item.Properties().stacksTo(1).durability(50)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);

        ModTabs.register(eventBus);
    }
}
