package net.aoai.feexperimenting.recipe;

import net.aoai.feexperimenting.FEExperimenting;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, FEExperimenting.MODID);
    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, FEExperimenting.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ElectricalFurnaceRecipe>> ELECTRICAL_FURNACE_SERIALIZER =
            SERIALIZERS.register("electrical_furnace", ElectricalFurnaceRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<ElectricalFurnaceRecipe>> ELECTRICAL_FURNACE_TYPE =
            TYPES.register("electrical_furnace", () -> new RecipeType<ElectricalFurnaceRecipe>() {
                @Override
                public String toString() {
                    return "electrical_furnace";
                }
            });

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ElectricalFreezerRecipe>> ELECTRICAL_FREEZER_SERIALIZER =
            SERIALIZERS.register("electrical_freezer", ElectricalFreezerRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<ElectricalFreezerRecipe>> ELECTRICAL_FREEZER_TYPE =
            TYPES.register("electrical_freezer", () -> new RecipeType<ElectricalFreezerRecipe>() {
                @Override
                public String toString() {
                    return "electrical_freezer";
                }
            });

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}
