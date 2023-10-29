package com.keletu.thaumkraftu.compat;

import com.keletu.thaumkraftu.ThaumKraftu;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.ICraftingGridHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.Collections;
import java.util.List;

/**
 * Created by brandon3055 on 17/02/2017.
 */
public class CraftingStationCategory implements IRecipeCategory<StationRecipeWrapper> {

    private static final int craftOutputSlot = 0;
    private static final int craftInputSlot1 = 1;

    private final String localizedName;
    private final ICraftingGridHelper gridHelper;

    public CraftingStationCategory(IGuiHelper guiHelper) {
        localizedName = I18n.format("crafting.tk_station");
        gridHelper = guiHelper.createCraftingGridHelper(craftInputSlot1, craftOutputSlot);
    }

    @Override
    public String getUid() {
        return "tk.station";
    }

    @Override
    public String getTitle() {
        return localizedName;
    }

    @Override
    public String getModName() {
        return ThaumKraftu.MOD_NAME;
    }

    @Override
    public IDrawable getBackground() {
        return TKJEIPlugin.extreme_crafting;
    }

    @Override
    public void setRecipe(IRecipeLayout layout, StationRecipeWrapper wrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = layout.getItemStacks();

        guiItemStacks.init(craftOutputSlot, false, 112, 25);

        for (int y = 0; y < 4; ++y) {
            for (int x = 0; x < 4; ++x) {
                int index = craftInputSlot1 + x + (y * 4);
                guiItemStacks.init(index, true, (x * 17) + 1, (y * 17) + 1);
            }
        }
        List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
        if (wrapper.recipe.isShapedRecipe()) {
            try {
                int width = wrapper.recipe.getWidth();
                int height = wrapper.recipe.getHeight();
                if (width != 4) {
                    List<List<ItemStack>> newInputs = NonNullList.withSize(4 * height, Collections.emptyList());
                    for (int i = 0; i < height; i++) {
                        for (int j = 0; j < 4; j++) {
                            int index = i + j * 4;
                            int oldidx = i + j * width;
                            if (j < width) {
                                newInputs.set(index, inputs.get(oldidx));
                            }
                        }
                    }
                    ingredients.setInputLists(ItemStack.class, newInputs);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            layout.setShapeless();
        }
        guiItemStacks.set(ingredients);
    }
}
