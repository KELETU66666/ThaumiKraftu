package com.keletu.thaumkraftu;

import com.keletu.thaumkraftu.init.KBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class KTab extends CreativeTabs
{
    public KTab(String label)
    {
        super(label);
    }

    @Override
    public ItemStack createIcon()
    {
        return Item.getItemFromBlock(KBlocks.crafting_station).getDefaultInstance();
    }
}