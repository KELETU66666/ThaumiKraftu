package com.keletu.thaumkraftu.inventory;

import com.keletu.thaumkraftu.recipe.ITKStation;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.tileentity.TileEntity;


public class InventoryCraftingStation extends InventoryCrafting implements ITKStation
{
    TileEntity workbench;
    
    public InventoryCraftingStation(TileEntity tileEntity, Container container) {
        super(container, 4, 4);
        workbench = tileEntity;
    }
    
    public String getName() {
        return "container.craftingStation";
    }
    
    public void markDirty() {
        super.markDirty();
        workbench.markDirty();
    }
}