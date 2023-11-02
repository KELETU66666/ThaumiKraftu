package com.keletu.thaumkraftu.item;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemGolemAmulet extends Item implements IBauble {

    public ItemGolemAmulet() {
        this.setMaxStackSize(1);
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return BaubleType.CHARM;
    }

    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {

    }
}