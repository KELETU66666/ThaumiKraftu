package com.keletu.thaumkraftu.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import thaumcraft.api.items.ItemsTC;

public class CrystalEssenceSlot extends Slot {
    public CrystalEssenceSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem() == ItemsTC.crystalEssence;
    }
}
