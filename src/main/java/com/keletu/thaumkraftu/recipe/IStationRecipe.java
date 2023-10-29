package com.keletu.thaumkraftu.recipe;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public interface IStationRecipe extends IRecipe {

    boolean matches(InventoryCrafting inventory, World world);

    default ItemStack getCraftingResult(InventoryCrafting inventory) {
        return getRecipeOutput();
    }

    boolean canFit(int width, int height);

    ItemStack getRecipeOutput();

    default NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        return ForgeHooks.defaultRecipeGetRemainingItems(inv);
    }

    default NonNullList<Ingredient> getIngredients() {
        return NonNullList.create();
    }

    /**
     * If this recipe is shaped.
     * Used for JEI ingredient layout.
     * Used for CraftTweaker recipe removal.
     *
     * @return true or false, default false.
     */
    default boolean isShapedRecipe() {
        return false;
    }

    /**
     * Gets the width for this recipe.
     * Only supported on shaped recipes.
     * Throws an UnsupportedOperationException if isShapedRecipe returns false.
     *
     * @return the width.
     */
    default int getWidth() {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the height for this recipe.
     * Only supported on shaped recipes.
     * Throws an UnsupportedOperationException if isShapedRecipe returns false.
     *
     * @return the height.
     */
    default int getHeight() {
        throw new UnsupportedOperationException();
    }
}
