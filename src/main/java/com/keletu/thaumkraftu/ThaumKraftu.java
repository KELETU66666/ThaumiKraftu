package com.keletu.thaumkraftu;

import com.keletu.thaumkraftu.init.*;
import com.keletu.thaumkraftu.proxy.CommonProxy;
import com.keletu.thaumkraftu.tile.TileTK;
import com.keletu.thaumkraftu.village.*;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.golems.EnumGolemTrait;
import thaumcraft.api.golems.parts.GolemHead;
import thaumcraft.api.golems.parts.PartModel;

@Mod(
        modid = ThaumKraftu.MOD_ID,
        name = ThaumKraftu.MOD_NAME,
        version = ThaumKraftu.VERSION,
        dependencies = ThaumKraftu.dependencies
)
public class ThaumKraftu {

    public static final String MOD_ID = "thaumkraftu";
    public static final String MOD_NAME = "ThaumKraftu";
    public static final String VERSION = "0.1";
    public static final String dependencies = "required-after:thaumcraft@[6.1.BETA26,)";

    public static ThaumKraftu instance;

    @SidedProxy(clientSide = "com.keletu.thaumkraftu.proxy.ClientProxy", serverSide = "com.keletu.thaumkraftu.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static CreativeTabs tab = new KTab("thaumkraftu.tab");


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        instance = this;
        proxy.preInit(event);
        TKEvents.infernalMobList();
        KBlocks.preInit();
        KBlocks.registerBlocks();
        KItems.preInit();
        KItems.registerItem();

        OreDictionary.registerOre("ingotInfusedGold", KItems.electrum_ingot);
        OreDictionary.registerOre("gemMoonstone", KItems.moon_stone);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        TileTK.setUp();
        KRecipes.TKStationRecipes();
        KResearch.registerResearch();

        VillageWizardManager.registerUselessVillager();
        VillagerRegistry.instance().registerVillageCreationHandler(new VillageWizardManager());
        MapGenStructureIO.registerStructureComponent(ComponentWizardTower.class, "TKWizTower");

        VillageBankerManager.registerUselessVillager();
        VillagerRegistry.instance().registerVillageCreationHandler(new VillageBankerManager());
        MapGenStructureIO.registerStructureComponent(ComponentBankerHome.class, "Bank");
        MinecraftForge.EVENT_BUS.register(new LootHandler());

        NetworkRegistry.INSTANCE.registerGuiHandler(ThaumKraftu.instance, new TKGuiHandler());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        GolemHead.register(new GolemHead("FORAGE", new String[] { "PECHY_GOLEMANCY" }, new ResourceLocation("thaumkraftu", "textures/models/research/r_pech.png"), new PartModel(new ResourceLocation("thaumkraftu", "models/obj/pech_skull_stalker.obj"), new ResourceLocation("thaumkraftu", "textures/blocks/pech_skull_forage.png"), PartModel.EnumAttachPoint.HEAD), new Object[] { new ItemStack(KBlocks.pechHead_normal) }, new EnumGolemTrait[] { EnumGolemTrait.BREAKER, EnumGolemTrait.LIGHT }));
        GolemHead.register(new GolemHead("STALKER", new String[] { "PECHY_GOLEMANCY" }, new ResourceLocation("thaumkraftu", "textures/models/research/r_pech_stalker.png"), new PartModel(new ResourceLocation("thaumkraftu", "models/obj/pech_skull_stalker.obj"), new ResourceLocation("thaumkraftu", "textures/blocks/pech_skull_stalker.png"), PartModel.EnumAttachPoint.HEAD), new Object[] { new ItemStack(KBlocks.pechHead_hunter) }, new EnumGolemTrait[] { EnumGolemTrait.FIGHTER, EnumGolemTrait.LIGHT }));
        GolemHead.register(new GolemHead("THAUMIUM", new String[] { "PECHY_GOLEMANCY" }, new ResourceLocation("thaumkraftu", "textures/models/research/r_pech_thaum.png"), new PartModel(new ResourceLocation("thaumkraftu", "models/obj/pech_skull_stalker.obj"), new ResourceLocation("thaumkraftu", "textures/blocks/pech_skull_thaum.png"), PartModel.EnumAttachPoint.HEAD), new Object[] { new ItemStack(KBlocks.pechHead_thaumaturge) }, new EnumGolemTrait[] { EnumGolemTrait.SMART, EnumGolemTrait.LIGHT }));
    }

    @Mod.EventBusSubscriber
    public static class ObjectRegistryHandler {


        @SubscribeEvent
        public static void addBlocks(RegistryEvent.Register<Block> event) {
            proxy.registerBlocks(event);
        }

        @SubscribeEvent
        public static void addItems(RegistryEvent.Register<Item> event) {
            proxy.registerItems(event);
        }

        @SubscribeEvent
        @SideOnly(Side.CLIENT)
        public static void modelRegistryEvent(ModelRegistryEvent event) {
            proxy.modelRegistryEvent(event);
        }
    }
}
