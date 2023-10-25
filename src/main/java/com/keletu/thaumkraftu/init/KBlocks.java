package com.keletu.thaumkraftu.init;

import com.google.common.base.Strings;
import com.keletu.thaumkraftu.ThaumKraftu;
import com.keletu.thaumkraftu.block.BlockCraftingStation;
import com.keletu.thaumkraftu.block.BlockPechhead;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class KBlocks {
    public static Block crafting_station;
    public static Block pechHead_normal;
    public static Block pechHead_hunter;
    public static Block pechHead_thaumaturge;

    public static void preInit() {
        crafting_station = new BlockCraftingStation().setTranslationKey("crafting_station").setCreativeTab(ThaumKraftu.tab);
        pechHead_normal = new BlockPechhead().setTranslationKey("pech_skull_forage").setCreativeTab(ThaumKraftu.tab);
        pechHead_hunter = new BlockPechhead().setTranslationKey("pech_skull_stalker").setCreativeTab(ThaumKraftu.tab);
        pechHead_thaumaturge = new BlockPechhead().setTranslationKey("pech_skull_thaum").setCreativeTab(ThaumKraftu.tab);
    }

    public static void registerBlocks() {
        KBlocks.registerBlock(crafting_station, crafting_station.getTranslationKey().substring(5));
        KBlocks.registerBlock(pechHead_normal, pechHead_normal.getTranslationKey().substring(5));
        KBlocks.registerBlock(pechHead_hunter, pechHead_hunter.getTranslationKey().substring(5));
        KBlocks.registerBlock(pechHead_thaumaturge, pechHead_thaumaturge.getTranslationKey().substring(5));
    }

    public static Block registerBlock(Block block) {
        ForgeRegistries.BLOCKS.register(block);
        ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
        return block;
    }

    public static Block registerBlock(Block block, String name) {
        if (block.getRegistryName() == null && Strings.isNullOrEmpty(name))
            throw new IllegalArgumentException("Attempted to register a Block with no name: " + block);

        return registerBlock(block.getRegistryName() != null ? block : block.setRegistryName(name));
    }

    public static void Render(){
        registerRender(crafting_station);
        registerRender(pechHead_normal);
        registerRender(pechHead_hunter);
        registerRender(pechHead_thaumaturge);
    }

    public static void registerRender(Block block) {
        Item item = Item.getItemFromBlock(block);
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(ThaumKraftu.MOD_ID + ":" + item.getTranslationKey().substring(5), "inventory"));
    }

}
