
package com.keletu.thaumkraftu.proxy;

import com.keletu.thaumkraftu.ThaumKraftu;
import com.keletu.thaumkraftu.entity.EntityEnergyBall;
import com.keletu.thaumkraftu.network.PacketHandler;
import com.keletu.thaumkraftu.village.TKWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.common.world.ThaumcraftWorldGenerator;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        PacketHandler.init();

        GameRegistry.registerWorldGenerator(TKWorldGenerator.INSTANCE, 0);
        EntityRegistry.registerModEntity(new ResourceLocation(ThaumKraftu.MOD_ID + ":" + "energy_ball"), EntityEnergyBall.class, "energy_ball", 0, ThaumKraftu.instance, 32, 1, true);
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    public void registerBlocks(RegistryEvent.Register<Block> event) {
    }

    public void registerItems(RegistryEvent.Register<Item> event) {
    }

    @SideOnly(Side.CLIENT)
    public void modelRegistryEvent(ModelRegistryEvent event) {

    }
}
