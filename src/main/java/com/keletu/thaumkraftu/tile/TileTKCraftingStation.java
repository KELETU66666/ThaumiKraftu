package com.keletu.thaumkraftu.tile;

import com.keletu.thaumkraftu.inventory.InventoryCraftingStation;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import thaumcraft.api.crafting.ContainerDummy;
import thaumcraft.common.tiles.TileThaumcraft;

public class TileTKCraftingStation extends TileThaumcraft
{
    public InventoryCraftingStation inventoryCraft;
    
    public TileTKCraftingStation() {
        inventoryCraft = new InventoryCraftingStation(this, new ContainerDummy());
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbtCompound) {
        super.readFromNBT(nbtCompound);
        NonNullList<ItemStack> stacks = NonNullList.withSize(inventoryCraft.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbtCompound, stacks);
        for (int a = 0; a < stacks.size(); ++a) {
            inventoryCraft.setInventorySlotContents(a, stacks.get(a));
        }
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbtCompound) {
        super.writeToNBT(nbtCompound);
        NonNullList<ItemStack> stacks = NonNullList.withSize(inventoryCraft.getSizeInventory(), ItemStack.EMPTY);
        for (int a = 0; a < stacks.size(); ++a) {
            stacks.set(a, inventoryCraft.getStackInSlot(a));
        }
        ItemStackHelper.saveAllItems(nbtCompound, stacks);
        return nbtCompound;
    }
    
    @Override
    public void readSyncNBT(NBTTagCompound nbtCompound) {
    }
    
    @Override
    public NBTTagCompound writeSyncNBT(NBTTagCompound nbtCompound) {
        return nbtCompound;
    }
}