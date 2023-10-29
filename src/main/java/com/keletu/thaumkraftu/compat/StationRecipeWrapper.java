package com.keletu.thaumkraftu.compat;

import com.keletu.thaumkraftu.recipe.IStationRecipe;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by covers1624 on 31/07/2017.
 */
public class StationRecipeWrapper implements IRecipeWrapper {

    protected final IStationRecipe recipe;

    public StationRecipeWrapper(IStationRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(ItemStack.class, getRecipeInputs(recipe));
        ingredients.setOutput(ItemStack.class, getRecipeOutput(recipe));
    }

    protected List<List<ItemStack>> getRecipeInputs(IStationRecipe recipe) {
        return TKJEIPlugin.jeiHelpers.getStackHelper().expandRecipeItemStackInputs(recipe.getIngredients());
    }

    protected ItemStack getRecipeOutput(IStationRecipe recipe) {
        return recipe.getRecipeOutput();
    }
}