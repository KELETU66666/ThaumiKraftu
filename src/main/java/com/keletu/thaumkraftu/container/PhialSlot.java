package com.keletu.thaumkraftu.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import thaumcraft.api.blocks.BlocksTC;

public class PhialSlot extends Slot {
    public PhialSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.equals(new ItemStack(BlocksTC.crystalAir)) || stack.equals(new ItemStack(BlocksTC.crystalFire)) || stack.equals(new ItemStack(BlocksTC.crystalWater)) || stack.equals(new ItemStack(BlocksTC.crystalEarth)) || stack.equals(new ItemStack(BlocksTC.crystalEntropy)) || stack.equals(new ItemStack(BlocksTC.crystalOrder)) || stack.equals(new ItemStack(BlocksTC.crystalTaint));
    }
}
