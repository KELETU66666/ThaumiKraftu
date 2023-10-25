package com.keletu.thaumkraftu.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.keletu.thaumkraftu.init.KItems;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.oredict.OreDictionary;

public class ItemElectrumDagger extends ItemSword {

    public ItemElectrumDagger() {
        super(ToolMaterial.GOLD);
        this.setMaxDamage(256);
    }

    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return OreDictionary.itemMatches(new ItemStack(KItems.electrum_ingot), repair, false);
    }

    public float getAttackDamage()
    {
        return 4;
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 2, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", 1, 0));
        }

        return multimap;
    }
}
