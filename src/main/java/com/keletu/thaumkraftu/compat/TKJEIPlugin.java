package com.keletu.thaumkraftu.compat;

import com.keletu.thaumkraftu.client.gui.GuiCraftingStation;
import com.keletu.thaumkraftu.container.ContainerTKCraftingStation;
import com.keletu.thaumkraftu.init.KBlocks;
import com.keletu.thaumkraftu.recipe.IStationRecipe;
import com.keletu.thaumkraftu.recipe.StationShapedRecipe;
import mezz.jei.api.*;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.ResourceLocation;

import java.util.stream.Collectors;

@JEIPlugin
public class TKJEIPlugin implements IModPlugin {

    public static final String TK_STATION = "tk.station";

    public static IJeiHelpers jeiHelpers;

    public static IDrawableStatic extreme_crafting;

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new CraftingStationCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void register(IModRegistry registry) {
        jeiHelpers = registry.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        setupDrawables(guiHelper);

        registry.addRecipes(CraftingManager.REGISTRY.getKeys().stream().map(CraftingManager.REGISTRY::getObject).filter(iRecipe -> iRecipe instanceof IStationRecipe).map(iRecipe -> new StationRecipeWrapper((IStationRecipe) iRecipe)).collect(Collectors.toList()), TK_STATION);
        registry.handleRecipes(StationShapedRecipe.class, StationRecipeWrapper::new, TK_STATION);
        registry.addRecipeCatalyst(new ItemStack(KBlocks.crafting_station), TK_STATION);
        registry.getRecipeTransferRegistry().addRecipeTransferHandler(ContainerTKCraftingStation.class, TK_STATION, 1, 1, 1, 1);
        registry.addRecipeClickArea(GuiCraftingStation.class, 100, 40, 15, 13, TK_STATION);
    }

    private static void setupDrawables(IGuiHelper helper) {
        ResourceLocation location = new ResourceLocation("thaumkraftu:textures/gui/crafting_station_jei.png");
        extreme_crafting = helper.createDrawable(location, 0, 0, 150, 70);
    }
}
