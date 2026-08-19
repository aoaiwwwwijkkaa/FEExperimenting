package net.aoai.feexperimenting.block;

import net.aoai.feexperimenting.FEExperimenting;
import net.aoai.feexperimenting.block.custom.ElectricalFreezerBlock;
import net.aoai.feexperimenting.block.custom.ElectricalFurnaceBlock;
import net.aoai.feexperimenting.block.custom.FEBatteryBlock;
import net.aoai.feexperimenting.block.custom.GeneratorBlock;
import net.aoai.feexperimenting.block.entity.ModBlockEnts;
import net.aoai.feexperimenting.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(FEExperimenting.MODID);

    public static final DeferredBlock<Block> GENERATOR = regBlock("generator",
            () -> new GeneratorBlock(BlockBehaviour.Properties.of()
                    .strength(8f).requiresCorrectToolForDrops().sound(SoundType.METAL)));

    public static final DeferredBlock<Block> ELECTRICAL_FURNACE = regBlock("electrical_furnace",
            () -> new ElectricalFurnaceBlock(BlockBehaviour.Properties.of()
                    .strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)));

    public static final DeferredBlock<Block> ELECTRICAL_FREEZER = regBlock("electrical_freezer",
            () -> new ElectricalFreezerBlock(BlockBehaviour.Properties.of()
                    .strength(5f).requiresCorrectToolForDrops().sound(SoundType.METAL)));

    public static final DeferredBlock<Block> FE_BATTERY = regBlock("fe_battery",
            () -> new FEBatteryBlock(BlockBehaviour.Properties.of()
                    .strength(3f).requiresCorrectToolForDrops().sound(SoundType.METAL)));

    private static <T extends Block> DeferredBlock<T> regBlock(String name, Supplier<T> block) {
        DeferredBlock<T> returnal = BLOCKS.register(name, block);
        regBlockI(name, returnal);
        return returnal;
    }

    private static <T extends Block> void regBlockI(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);

        ModBlockEnts.register(eventBus);
    }
}
