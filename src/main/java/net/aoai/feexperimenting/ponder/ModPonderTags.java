package net.aoai.feexperimenting.ponder;

import net.aoai.feexperimenting.FEExperimenting;
import net.aoai.feexperimenting.block.ModBlocks;
import net.aoai.feexperimenting.item.ModItems;
import net.createmod.catnip.registry.RegisteredObjectsHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

public class ModPonderTags {
    public static final ResourceLocation

            FE_SOURCES = loc("fe_sources"),
            FE_CONSUMERS = loc("fe_consumers"),
            UTILS = loc("utils");

    private static ResourceLocation loc(String id) {
        return ResourceLocation.fromNamespaceAndPath(FEExperimenting.MODID,id);
    }

    public static void reg(PonderTagRegistrationHelper<ResourceLocation> helper) {
        helper.registerTag(FE_SOURCES)
                .addToIndex()
                .item(ModBlocks.GENERATOR.asItem(), true, true)
                .title("FE Sources")
                .description("Blocks that make FE")
                .register();

        helper.registerTag(FE_CONSUMERS)
                 .addToIndex()
                 .item(ModBlocks.ELECTRICAL_FURNACE.asItem(), true, true)
                 .title("FE Consumers")
                 .description("Blocks that consume FE to make something")
                 .register();

        helper.registerTag(UTILS)
                .addToIndex()
                .item(ModItems.ENERGY_DETECTOR.asItem(), true, true)
                .title("Utilities")
                .description("Utilities that help you figure out something")
                .register();

        helper.addToTag(UTILS).add(ModBlocks.FE_BATTERY.getId());
        helper.addToTag(FE_CONSUMERS).add(ModBlocks.ELECTRICAL_FREEZER.getId());
        helper.addToTag(FE_CONSUMERS).add(ModBlocks.FE_BATTERY.getId());
    }
}
