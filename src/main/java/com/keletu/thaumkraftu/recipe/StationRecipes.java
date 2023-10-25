package com.keletu.thaumkraftu.recipe;

import com.keletu.thaumkraftu.ThaumKraftu;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.registries.IForgeRegistry;
import thaumcraft.api.items.ItemsTC;

import java.util.*;

public class StationRecipes {
    public static List<RecipeContainer> List = new ArrayList<>();
    private static IForgeRegistry<IRecipe> registry;

    public static void addRecipe(RecipeContainer r, Object ob, Object... recipe) {
        ItemStack item = ob instanceof Item ? new ItemStack((Item)ob) : (ob instanceof Block ? new ItemStack((Block)ob) : (ItemStack)ob);
        r.add(item, recipe);
    }

    public void addRecipes(Object ... recipe) {
        int i;
        RecipeContainer r = new RecipeContainer();
        HashMap<ItemStack, Object> obs = new HashMap<>();
        for (i = 3; i < recipe.length; i += 4) {
            obs.put(ItemsTC.phial.getDefaultInstance(), recipe[i]);
        }
        recipe = Arrays.copyOfRange(recipe, i, recipe.length);
        for (Map.Entry ent : obs.entrySet()) {
            Object[] rec = Arrays.copyOf(recipe, recipe.length);
            for (int j = 0; j < rec.length; ++j) {
                if (rec[j] != null) continue;
                rec[j] = ent.getValue();
            }
            r.add((ItemStack)ent.getKey(), rec);
        }
    }

    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        List.clear();
        registry = event.getRegistry();
    }

    public static IRecipe match(InventoryCrafting inv, World worldIn) {
        for (IRecipe irecipe : registry.getValues()) {
            int j;
            int i;
            Object recipe;
            if (irecipe instanceof ShapedRecipes) {
                recipe = irecipe;
                for (i = 0; i <= 4 - ((ShapedRecipes)recipe).recipeWidth; ++i) {
                    for (j = 0; j <= 4 - ((ShapedRecipes)recipe).recipeHeight; ++j) {
                        if (!StationRecipes.checkMatch((ShapedRecipes)recipe, inv, i, j, true) && !StationRecipes.checkMatch((ShapedRecipes)recipe, inv, i, j, false)) continue;
                        return irecipe;
                    }
                }
                continue;
            }
            if (irecipe instanceof ShapedOreRecipe) {
                recipe = irecipe;
                for (i = 0; i <= 4 - ((ShapedOreRecipe)recipe).getWidth(); ++i) {
                    for (j = 0; j <= 4 - ((ShapedOreRecipe)recipe).getHeight(); ++j) {
                        if (!StationRecipes.checkMatch((ShapedOreRecipe)recipe, inv, i, j, true) && !StationRecipes.checkMatch((ShapedOreRecipe)recipe, inv, i, j, false)) continue;
                        return irecipe;
                    }
                }
                continue;
            }
            if (!irecipe.matches(inv, worldIn)) continue;
            return irecipe;
        }
        return null;
    }

    private static boolean checkMatch(ShapedOreRecipe recipe, InventoryCrafting inv, int startX, int startY, boolean mirror) {
        for (int x = 0; x < 4; ++x) {
            for (int y = 0; y < 4; ++y) {
                int subX = x - startX;
                int subY = y - startY;
                Ingredient target = Ingredient.EMPTY;
                if (subX >= 0 && subY >= 0 && subX < recipe.getWidth() && subY < recipe.getHeight()) {
                    target = mirror ? recipe.getIngredients().get(recipe.getWidth() - subX - 1 + subY * recipe.getWidth()) : recipe.getIngredients().get(subX + subY * recipe.getWidth());
                }
                if (target.apply(inv.getStackInRowAndColumn(x, y)))
                    continue;
                return false;
            }
        }

        return true;
    }

    private static boolean checkMatch(ShapedRecipes recipe, InventoryCrafting inv, int startX, int startY, boolean mirror) {
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                int k = i - startX;
                int l = j - startY;
                Ingredient ingredient = Ingredient.EMPTY;
                if (k >= 0 && l >= 0 && k < recipe.recipeWidth && l < recipe.recipeHeight) {
                    ingredient = mirror ? recipe.recipeItems.get(recipe.recipeWidth - k - 1 + l * recipe.recipeWidth) : recipe.recipeItems.get(k + l * recipe.recipeWidth);
                }

                if (ingredient.apply(inv.getStackInRowAndColumn(i, j))) continue;
                return false;
            }
        }

        return true;
    }

    public static class RecipeContainer {
        private final LinkedHashMap<ItemStack, List<ItemStack>> items = new LinkedHashMap<>();
        public int width = 1;
        public int height = 1;

        public RecipeContainer() {
            List.add(this);
        }

        public void add(ItemStack output, Object[] params) {
            CraftingHelper.ShapedPrimer primer = CraftingHelper.parseShaped(params);
            IRecipe r = new ShapedRecipes(ThaumKraftu.MOD_ID, primer.width, primer.height, primer.input, output).setRegistryName(output.getItem().getRegistryName() + "_" + output.getItemDamage());
            if (r.getRecipeOutput() .isEmpty() || r.getRecipeOutput().isEmpty()) {
                return;
            }
            ArrayList<ItemStack> input = new ArrayList<>();
            ShapedRecipes recipe = (ShapedRecipes) r;
            this.width = recipe.recipeWidth;
            this.height = recipe.recipeHeight;
            for (Ingredient in : recipe.recipeItems) {
                if (in == Ingredient.EMPTY) {
                    input.add(ItemStack.EMPTY);
                    continue;
                }
                input.add(in.getMatchingStacks()[0]);
            }
            this.items.put(r.getRecipeOutput(), input);
            registry.register(r);
        }

        public void add(ItemStack input, ItemStack output) {
            ArrayList<ItemStack> l = new ArrayList<>();
            l.add(input);
            this.items.put(output, l);
        }

        public ItemStack output() {
            int i = 0;
            if (this.items.size() > 1) {
                i = (int)(System.currentTimeMillis() / 2000L % (long)this.items.size());
            }
            for (Map.Entry<ItemStack, List<ItemStack>> entry : this.items.entrySet()) {
                if (i == 0) {
                    return entry.getKey();
                }
                --i;
            }
            return null;
        }

        public List<ItemStack> input() {
            int i = 0;
            if (this.items.size() > 1) {
                i = (int)(System.currentTimeMillis() / 2000L % (long)this.items.size());
            }
            for (Map.Entry<ItemStack, List<ItemStack>> entry : this.items.entrySet()) {
                if (i == 0) {
                    return entry.getValue();
                }
                --i;
            }
            return null;
        }
    }
}
