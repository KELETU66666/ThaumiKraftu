package com.keletu.thaumkraftu.init;

import com.google.common.base.Strings;
import com.keletu.thaumkraftu.ThaumKraftu;
import com.keletu.thaumkraftu.item.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class KItems {

    public static Item moon_stone;
    public static Item electrum_ingot;
    public static Item shadow_ingot;
    public static Item shadow_nugget;
    public static Item moonstone_claymore;
    public static Item electrum_dagger;
    public static Item runic_greatsword;
    public static Item eternal_sword;
    public static Item shadow_helm;
    public static Item shadow_chest;
    public static Item shadow_legs;
    public static Item parchment;
    public static Item ancient_skull;
    public static Item golem_amulet;
    public static Item mana_bean;

    public static ItemArmor.ArmorMaterial SHADOW_FORTRESS = EnumHelper.addArmorMaterial("SHADOW_FORTRESS", "shadow", 300, new int[]{0, 6, 10, 4}, 15, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 4F);
    public static void preInit() {
        moon_stone = new Item().setTranslationKey("moon_gemstone").setCreativeTab(ThaumKraftu.tab);
        electrum_ingot = new Item().setTranslationKey("electrum_ingot").setCreativeTab(ThaumKraftu.tab);
        shadow_ingot = new Item().setTranslationKey("shadow_ingot").setCreativeTab(ThaumKraftu.tab);
        shadow_nugget = new Item().setTranslationKey("shadow_nugget").setCreativeTab(ThaumKraftu.tab);
        moonstone_claymore = new ItemMoonstoneSword().setTranslationKey("moonstone_sword").setCreativeTab(ThaumKraftu.tab);
        electrum_dagger = new ItemElectrumDagger().setTranslationKey("electrum_dagger").setCreativeTab(ThaumKraftu.tab);
        runic_greatsword = new ItemSwoeld().setTranslationKey("runic_greatsword").setCreativeTab(ThaumKraftu.tab);
        shadow_helm = new ShadowArmor(EntityEquipmentSlot.HEAD).setTranslationKey("shadow_fortress_helm").setCreativeTab(ThaumKraftu.tab);
        shadow_chest = new ShadowArmor(EntityEquipmentSlot.CHEST).setTranslationKey("shadow_fortress_chest").setCreativeTab(ThaumKraftu.tab);
        shadow_legs = new ShadowArmor(EntityEquipmentSlot.LEGS).setTranslationKey("shadow_fortress_legs").setCreativeTab(ThaumKraftu.tab);
        eternal_sword = new ItemEternalSword().setTranslationKey("eternal_sword").setCreativeTab(ThaumKraftu.tab);
        parchment = new Item().setTranslationKey("tk_parchment").setCreativeTab(ThaumKraftu.tab);
        ancient_skull = new Item().setTranslationKey("ancient_skull").setCreativeTab(ThaumKraftu.tab);
        golem_amulet = new ItemGolemAmulet().setTranslationKey("golem_amulet").setCreativeTab(ThaumKraftu.tab);
        mana_bean = new ItemManaBean().setTranslationKey("mana_bean").setCreativeTab(ThaumKraftu.tab);
       }

    public static void registerItem() {
        registerItem(moon_stone, moon_stone.getTranslationKey().substring(5));
        registerItem(electrum_ingot, electrum_ingot.getTranslationKey().substring(5));
        registerItem(moonstone_claymore, moonstone_claymore.getTranslationKey().substring(5));
        registerItem(electrum_dagger, electrum_dagger.getTranslationKey().substring(5));
        registerItem(runic_greatsword, runic_greatsword.getTranslationKey().substring(5));
        registerItem(shadow_helm, shadow_helm.getTranslationKey().substring(5));
        registerItem(shadow_chest, shadow_chest.getTranslationKey().substring(5));
        registerItem(shadow_legs, shadow_legs.getTranslationKey().substring(5));
        registerItem(shadow_ingot, shadow_ingot.getTranslationKey().substring(5));
        registerItem(shadow_nugget, shadow_nugget.getTranslationKey().substring(5));
        registerItem(eternal_sword, eternal_sword.getTranslationKey().substring(5));
        registerItem(parchment, parchment.getTranslationKey().substring(5));
        registerItem(ancient_skull, ancient_skull.getTranslationKey().substring(5));
        registerItem(golem_amulet, golem_amulet.getTranslationKey().substring(5));
        registerItem(mana_bean, mana_bean.getTranslationKey().substring(5));
    }

    public static void registerItem(Item item, String name)
    {
        if (item.getRegistryName() == null && Strings.isNullOrEmpty(name))
            throw new IllegalArgumentException("Attempted to register a item with no name: " + item);
        if (item.getRegistryName() != null && !item.getRegistryName().toString().equals(name))
            throw new IllegalArgumentException("Attempted to register a item with conflicting names. Old: " + item.getRegistryName() + " New: " + name);
        ForgeRegistries.ITEMS.register(item.getRegistryName() == null ? item.setRegistryName(name) : item);
    }

    public static void Render(){
        registerRender();
    }

    public static void registerRender() {
        renderItems(moon_stone);
        renderItems(electrum_ingot);
        renderItems(moonstone_claymore);
        renderItems(electrum_dagger);
        renderItems(runic_greatsword);
        renderItems(shadow_helm);
        renderItems(shadow_chest);
        renderItems(shadow_legs);
        renderItems(shadow_ingot);
        renderItems(shadow_nugget);
        renderItems(eternal_sword);
        renderItems(parchment);
        renderItems(ancient_skull);
        renderItems(golem_amulet);
        renderItems(mana_bean);
    }

    public static void renderItems(Item i){

        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(i, 0, new ModelResourceLocation(
                ThaumKraftu.MOD_ID + ":" + i.getTranslationKey().substring(5), "inventory"));

    }

}
