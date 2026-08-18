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

        helper.registerTag(UTILS)
                .addToIndex()
                .item(ModItems.ENERGY_DETECTOR.asItem(), true, true)
                .title("Utilities")
                .description("Utilities that help you figure out something")
                .register();
    }
}
