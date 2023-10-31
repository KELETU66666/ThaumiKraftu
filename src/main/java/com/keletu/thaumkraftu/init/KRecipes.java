package com.keletu.thaumkraftu.init;

import com.keletu.thaumkraftu.ThaumKraftu;
import com.keletu.thaumkraftu.recipe.IStationRecipe;
import com.keletu.thaumkraftu.recipe.StationShapedRecipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.GameData;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.items.ItemsTC;

public class KRecipes {

    public static void addArcaneStationCraftingRecipe(ResourceLocation registry, IStationRecipe recipe) {
        recipe.setRegistryName(registry);
        GameData.register_impl(recipe);
    }

    public static IStationRecipe findMatchingArcaneRecipe(InventoryCrafting matrix, EntityPlayer player) {
        int var2 = 0;
        ItemStack var3 = null;
        ItemStack var4 = null;
        for (int var5 = 0; var5 < 17; ++var5) {
            ItemStack var6 = matrix.getStackInSlot(var5);
            if (!var6.isEmpty()) {
                if (var2 == 0) {
                    var3 = var6;
                }
                if (var2 == 1) {
                    var4 = var6;
                }
                ++var2;
            }
        }
        for (ResourceLocation key : CraftingManager.REGISTRY.getKeys()) {
            IRecipe recipe = CraftingManager.REGISTRY.getObject(key);
            if (recipe != null && recipe instanceof IStationRecipe && recipe.matches(matrix, player.world)) {
                return (IStationRecipe)recipe;
            }
        }
        return null;
    }

    public static void TKStationRecipes() {
        addArcaneStationCraftingRecipe(new ResourceLocation(ThaumKraftu.MOD_ID, "moon_stone"), new StationShapedRecipe(
                new ItemStack(KItems.moon_stone, 1, 0),
                " X P",
                "XXXO",
                " X A",
                "   A",
                'X', "gemDiamond",
                'P', new ItemStack(BlocksTC.crystalWater),
                'O', ItemsTC.visResonator,
                'A', ItemsTC.quicksilver));

        addArcaneStationCraftingRecipe(new ResourceLocation(ThaumKraftu.MOD_ID, "moon_stone_sword"), new StationShapedRecipe(
                new ItemStack(KItems.moonstone_claymore, 1, 0),
                " X P",
                " X A",
                "GXGB",
                " D C",
                'X', "gemMoonstone",
                'P', new ItemStack(BlocksTC.crystalOrder),
                'A', ItemsTC.alumentum,
                'B', ItemsTC.mechanismSimple,
                'C', new ItemStack(ItemsTC.modules, 1, 1),
                'G', "gemAmber",
                'D', KItems.electrum_dagger));

        addArcaneStationCraftingRecipe(new ResourceLocation(ThaumKraftu.MOD_ID, "golem_amulet"), new StationShapedRecipe(
                new ItemStack(KItems.golem_amulet),
                "SCST",
                "IXIG",
                "BAB ",
                "H H ",
                'S', ItemsTC.salisMundus,
                'C', ItemsTC.causalityCollapser,
                'T', BlocksTC.crystalTaint,
                'I', "plateIron",
                'X', new ItemStack(ItemsTC.baubles, 1, 4),
                'B', "plateBrass",
                'A', Blocks.ANVIL,
                'G', ItemsTC.visResonator,
                'H', "plateThaumium"));

        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(ThaumKraftu.MOD_ID, "electrum_ingot"), new CrucibleRecipe(
                "ELECTRUM",
                new ItemStack(KItems.electrum_ingot),
                "ingotBrass",
                new AspectList().add(Aspect.DESIRE, 20).add(Aspect.METAL, 20).add(Aspect.ENERGY, 20)
        ));

        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(ThaumKraftu.MOD_ID, "electrum_ingot"), new InfusionRecipe(
                "ELECTRUM",
                new ItemStack(KItems.electrum_ingot),
                3,
                new AspectList().add(Aspect.MAGIC, 10).add(Aspect.AURA, 10),
                "ingotGold",
                new ItemStack(ItemsTC.salisMundus),
                new ItemStack(ItemsTC.salisMundus),
                new ItemStack(ItemsTC.salisMundus),
                new ItemStack(ItemsTC.salisMundus)));

        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(ThaumKraftu.MOD_ID, "shadow_metal"), new CrucibleRecipe(
                "SHADOW_METAL",
                new ItemStack(KItems.shadow_nugget),
                "nuggetIron",
                new AspectList().add(Aspect.DARKNESS, 15).add(Aspect.METAL, 30).add(Aspect.DARKNESS, 10)
        ));

        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(ThaumKraftu.MOD_ID, "runic_greatsword"), new InfusionRecipe(
                "RUNESTONE_GREATSWORD",
                new ItemStack(KItems.runic_greatsword),
                7,
                new AspectList().add(Aspect.PROTECT, 100).add(Aspect.AVERSION, 100).add(Aspect.VOID, 50).add(Aspect.EARTH, 50),
                new ItemStack(KItems.moonstone_claymore),
                new ItemStack(BlocksTC.stoneArcaneBrick),
                "plateIron",
                new ItemStack(BlocksTC.stoneArcaneBrick),
                "gemMoonstone",
                new ItemStack(BlocksTC.stoneArcaneBrick),
                "plateIron",
                new ItemStack(BlocksTC.stoneArcaneBrick),
                "gemMoonstone"));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(ThaumKraftu.MOD_ID, "shadow_fortress_helm"), new InfusionRecipe(
                "SHADOW_FORTRESS_ARMOR",
                new ItemStack(KItems.shadow_helm),
                16,
                new AspectList().add(Aspect.METAL, 100).add(Aspect.PROTECT, 80).add(Aspect.MAGIC, 45).add(Aspect.DARKNESS, 120).add(Aspect.VOID, 85),
                new ItemStack(ItemsTC.voidHelm),
                new ItemStack(KItems.shadow_ingot),
                new ItemStack(KItems.shadow_ingot),
                "ingotIron",
                "ingotIron",
                "gemMoonstone"));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(ThaumKraftu.MOD_ID, "shadow_fortress_chest"), new InfusionRecipe(
                "SHADOW_FORTRESS_ARMOR",
                new ItemStack(KItems.shadow_chest),
                16,
                new AspectList().add(Aspect.METAL, 150).add(Aspect.PROTECT, 100).add(Aspect.MAGIC, 70).add(Aspect.DARKNESS, 150).add(Aspect.VOID, 100),
                new ItemStack(ItemsTC.voidChest),
                new ItemStack(KItems.shadow_ingot),
                new ItemStack(KItems.shadow_ingot),
                new ItemStack(KItems.shadow_ingot),
                new ItemStack(KItems.shadow_ingot),
                "ingotIron",
                "leather"));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(ThaumKraftu.MOD_ID, "shadow_fortress_legs"), new InfusionRecipe(
                "SHADOW_FORTRESS_ARMOR",
                new ItemStack(KItems.shadow_legs),
                16,
                new AspectList().add(Aspect.METAL, 125).add(Aspect.PROTECT, 90).add(Aspect.MAGIC, 65).add(Aspect.DARKNESS, 125).add(Aspect.VOID, 90),
                new ItemStack(ItemsTC.voidLegs),
                new ItemStack(KItems.shadow_ingot),
                new ItemStack(KItems.shadow_ingot),
                new ItemStack(KItems.shadow_ingot),
                "ingotIron",
                "leather"));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(ThaumKraftu.MOD_ID, "shadow_helm_goggles"), new InfusionRecipe(
                "SHADOW_FORTRESS_ARMOR",
                new Object[]{"goggles", new NBTTagInt(1)},
                5,
                (new AspectList()).add(Aspect.SENSES, 40).add(Aspect.AURA, 20).add(Aspect.PROTECT, 20),
                new ItemStack(KItems.shadow_helm, 1, 32767),
                new ItemStack(Items.SLIME_BALL),
                new ItemStack(ItemsTC.goggles, 1, 32767)));

        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(ThaumKraftu.MOD_ID, "shadow_helm_warp"), new InfusionRecipe(
                "SHADOW_FORTRESS_ARMOR",
                new Object[]{"mask", new NBTTagInt(0)},
                8,
                new AspectList().add(Aspect.MIND, 80).add(Aspect.LIFE, 80).add(Aspect.PROTECT, 20),
                new ItemStack(KItems.shadow_helm, 1, 32767),
                "plateIron",
                "dyeBlack",
                "plateIron",
                "leather",
                BlocksTC.shimmerleaf,
                ItemsTC.brain));

        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(ThaumKraftu.MOD_ID, "shadow_helm_wither"), new InfusionRecipe(
                "SHADOW_FORTRESS_ARMOR",
                new Object[]{"mask", new NBTTagInt(1)},
                8,
                new AspectList().add(Aspect.ENTROPY, 80).add(Aspect.DEATH, 80).add(Aspect.PROTECT, 20),
                new ItemStack(KItems.shadow_helm, 1, 32767),
                "plateIron",
                "dyeWhite",
                "plateIron",
                "leather",
                Items.POISONOUS_POTATO,
                new ItemStack(Items.SKULL, 1, 1)));

        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(ThaumKraftu.MOD_ID, "shadow_helm_lifesteal"), new InfusionRecipe(
                "SHADOW_FORTRESS_ARMOR",
                new Object[]{"mask", new NBTTagInt(2)},
                8,
                new AspectList().add(Aspect.UNDEAD, 80).add(Aspect.LIFE, 80).add(Aspect.PROTECT, 20),
                new ItemStack(KItems.shadow_helm, 1, 32767),
                "plateIron",
                "dyeRed",
                "plateIron",
                "leather",
                Items.GHAST_TEAR,
                Items.MILK_BUCKET));
    }
}
