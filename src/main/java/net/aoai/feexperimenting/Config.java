package net.aoai.feexperimenting;

import java.util.List;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue GENERATOR_GENERATE = BUILDER
            .comment("The amount of FE the generator generates each tick")
            .defineInRange("generator_gen", 5, 1, 20);

    static final ModConfigSpec SPEC = BUILDER.build();
}
