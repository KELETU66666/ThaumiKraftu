package com.keletu.thaumkraftu.recipe;

import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * Created by covers1624 on 9/10/2017.
 */
public abstract class StationRecipeBase extends IForgeRegistryEntry.Impl<IRecipe> implements IStationRecipe {

    @Override
    public boolean canFit(int width, int height) {
        return width >= 4 && height >= 4;
    }
}
