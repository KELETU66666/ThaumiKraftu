package com.keletu.thaumkraftu.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.keletu.thaumkraftu.entity.EntityEnergyBall;
import com.keletu.thaumkraftu.network.PacketHandler;
import com.keletu.thaumkraftu.network.PacketLeftClick;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemSword;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.ThaumcraftMaterials;

public class ItemMoonstoneSword extends ItemSword {
    public ItemMoonstoneSword() {
        super(ThaumcraftMaterials.TOOLMAT_ELEMENTAL);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void leftClick(PlayerInteractEvent.LeftClickEmpty evt) {
        if (!evt.getItemStack().isEmpty()
                && evt.getItemStack().getItem() == this) {
            PacketHandler.sendToServer(new PacketLeftClick());
        }
    }

    @SubscribeEvent
    public void attackEntity(AttackEntityEvent evt) {
        if (!evt.getEntityPlayer().world.isRemote) {
            trySpawnBurst(evt.getEntityPlayer());
        }
    }


    public void trySpawnBurst(EntityPlayer player) {
        if (!player.getHeldItemMainhand().isEmpty()
                && player.getHeldItemMainhand().getItem() == this
                && player.getCooledAttackStrength(0) == 1) {
            EntityEnergyBall ball = new EntityEnergyBall(player.getEntityWorld(), player);
            ball.shoot(player, player.rotationPitch, player.rotationYaw, 0F, 3F, 1.0F);
            player.world.spawnEntity(ball);
        }
    }



    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 9, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -3.2, 0));
        }

        return multimap;
    }
}
