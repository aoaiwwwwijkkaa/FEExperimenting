package net.aoai.feexperimenting.block.entity;

import net.aoai.feexperimenting.FEExperimenting;
import net.aoai.feexperimenting.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEnts {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, FEExperimenting.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GeneratorBlockEntity>> GENERATOR_BLOCK_ENTITY_TYPE =
            BLOCK_ENTITIES.register("generator_block_entity", () ->
                    BlockEntityType.Builder.of(
                            GeneratorBlockEntity::new,
                            ModBlocks.GENERATOR.get())
                            .build(
                                    (com.mojang.datafixers.types.Type<?>) null
                            )
            );

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ElectricalFurnaceBlockEntity>> ELECTRICAL_FURNACE_BLOCK_ENTITY_TYPE =
            BLOCK_ENTITIES.register("electrical_furnace_block_entity", () ->
                    BlockEntityType.Builder.of(
                            ElectricalFurnaceBlockEntity::new,
                            ModBlocks.ELECTRICAL_FURNACE.get())
                            .build(
                                    (com.mojang.datafixers.types.Type<?>) null
                            )
            );

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ElectricalFreezerBlockEntity>> ELECTRICAL_FREEZER_BLOCK_ENTITY_TYPE =
            BLOCK_ENTITIES.register("electrical_freezer_block_entity", () ->
                    BlockEntityType.Builder.of(
                                    ElectricalFreezerBlockEntity::new,
                                    ModBlocks.ELECTRICAL_FREEZER.get())
                            .build(
                                    (com.mojang.datafixers.types.Type<?>) null
                            )
            );

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
