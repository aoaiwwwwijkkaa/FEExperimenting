package net.aoai.feexperimenting.capability;

import net.aoai.feexperimenting.FEExperimenting;
import net.aoai.feexperimenting.block.entity.ModBlockEnts;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = FEExperimenting.MODID)
public class ModCapabilities {

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.EnergyStorage.BLOCK,
                ModBlockEnts.GENERATOR_BLOCK_ENTITY_TYPE.get(),
                (blockEntity, direction) -> blockEntity.getStorage()
        );

        event.registerBlockEntity(
                Capabilities.EnergyStorage.BLOCK,
                ModBlockEnts.FE_BATTERY_BLOCK_ENTITY_TYPE.get(),
                (blockent, direct) -> blockent.getStorage()
        );

        event.registerBlockEntity(
                Capabilities.EnergyStorage.BLOCK,
                ModBlockEnts.ELECTRICAL_FURNACE_BLOCK_ENTITY_TYPE.get(),
                (blockEntity, direction) -> blockEntity.getStorage()
        );

        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEnts.ELECTRICAL_FURNACE_BLOCK_ENTITY_TYPE.get(),
                (blockEntity, direction) -> blockEntity.inventory
        );

        event.registerBlockEntity(
                Capabilities.EnergyStorage.BLOCK,
                ModBlockEnts.ELECTRICAL_FREEZER_BLOCK_ENTITY_TYPE.get(),
                (blockEntity, direction) -> blockEntity.getStorage()
        );

        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEnts.ELECTRICAL_FREEZER_BLOCK_ENTITY_TYPE.get(),
                (blockEntity, direction) -> blockEntity.inventory
        );
    }
}
