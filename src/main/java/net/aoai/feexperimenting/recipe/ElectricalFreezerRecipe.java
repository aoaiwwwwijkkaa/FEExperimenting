package net.aoai.feexperimenting.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public record ElectricalFreezerRecipe(Ingredient input, ItemStack output) implements Recipe<ElectricalFreezerRecipeInput> {
    public ElectricalFreezerRecipe(Ingredient input, ItemStack output) {
        this.input = input != null ? input : Ingredient.of();
        this.output = output != null ? output : ItemStack.EMPTY;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(input);
        return Recipe.super.getIngredients();
    }

    @Override
    public boolean matches(ElectricalFreezerRecipeInput electricalFreezerRecipeInput, Level level) {
        return input.test(electricalFreezerRecipeInput.getItem(0));
    }

    @Override
    public ItemStack assemble(ElectricalFreezerRecipeInput electricalFreezerRecipeInput, HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return false;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.ELECTRICAL_FREEZER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.ELECTRICAL_FREEZER_TYPE.get();
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public String getGroup() {
        return "";
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    public static class Serializer implements RecipeSerializer<ElectricalFreezerRecipe> {
        public static final MapCodec<ElectricalFreezerRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(ElectricalFreezerRecipe::input),
                ItemStack.CODEC.fieldOf("result").forGetter(ElectricalFreezerRecipe::output)
        ).apply(inst, ElectricalFreezerRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, ElectricalFreezerRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        ByteBufCodecs.fromCodec(Ingredient.CODEC), ElectricalFreezerRecipe::input,
                        ItemStack.STREAM_CODEC, ElectricalFreezerRecipe::output,
                        ElectricalFreezerRecipe::new
                );

        @Override
        public MapCodec<ElectricalFreezerRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ElectricalFreezerRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
