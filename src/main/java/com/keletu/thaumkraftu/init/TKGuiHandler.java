package com.keletu.thaumkraftu.init;

import com.keletu.thaumkraftu.client.gui.GuiCraftingStation;
import com.keletu.thaumkraftu.container.ContainerTKCraftingStation;
import com.keletu.thaumkraftu.tile.TileTKCraftingStation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class TKGuiHandler implements IGuiHandler {

    public static int CraftingStation = 1;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == CraftingStation) {
            return new ContainerTKCraftingStation(player.inventory, (TileTKCraftingStation) world.getTileEntity(new BlockPos(x, y, z)));
        }
        return null;
    }


    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == CraftingStation) {
            return new GuiCraftingStation(new ContainerTKCraftingStation(player.inventory, (TileTKCraftingStation) world.getTileEntity(new BlockPos(x, y, z))));
        }
        return null;
    }


}
