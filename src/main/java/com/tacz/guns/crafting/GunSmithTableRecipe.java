package com.tacz.guns.crafting;

import com.tacz.guns.GunMod;
import com.tacz.guns.resource.pojo.data.recipe.TableRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.List;

public class GunSmithTableRecipe implements Recipe<CraftingRecipeInput> {
    private final Identifier identifier;
    private final GunSmithTableResult result;
    private final List<GunSmithTableIngredient> inputs;

    public GunSmithTableRecipe(Identifier identifier, GunSmithTableResult result, List<GunSmithTableIngredient> inputs) {
        this.identifier = identifier;
        this.result = result;
        this.inputs = inputs;
    }

    public GunSmithTableRecipe(Identifier identifier, TableRecipe tableRecipe) {
        this(identifier, tableRecipe.getResult(), tableRecipe.getMaterials());
    }

    @Override
    @Deprecated
    public boolean matches(CraftingRecipeInput playerInventory, World world) {
        return false;
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return this.result.result().copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return GunSmithTableSerializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public ItemStack getOutput() {
        return result.result();
    }

    public List<GunSmithTableIngredient> getInputs() {
        return inputs;
    }

    public GunSmithTableResult getResult() {
        return result;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public static class Type implements RecipeType<GunSmithTableRecipe> {
        // Define ExampleRecipe.Type as a singleton by making its constructor private and exposing an instance.
        private Type() {
        }

        public static final Type INSTANCE = new Type();

        // This will be needed in step 4
        public static final String ID = "gun_smith_table_crafting";

        @Override
        public String toString() {
            return GunMod.MOD_ID + ":" + ID;
        }
    }
}
