package com.keletu.thaumkraftu.tile;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileTK {
    public static void setUp(){
        //GameRegistry.registerTileEntity(TileCraftingStation.class, "crafting_station");
        GameRegistry.registerTileEntity(TileTKCraftingStation.class, "tk_crafting_station");
    }
}
