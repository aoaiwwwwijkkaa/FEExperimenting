package net.aoai.feexperimenting;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue GENERATOR_GENERATE = BUILDER
            .comment("The amount of FE the generator generates each tick")
            .defineInRange("generator_gen", 5, 1, 20);

    static final ModConfigSpec SPEC = BUILDER.build();
}
