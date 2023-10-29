package com.keletu.thaumkraftu;

import baubles.api.BaublesApi;
import com.keletu.thaumkraftu.init.KBlocks;
import com.keletu.thaumkraftu.init.KItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.entities.monster.EntityBrainyZombie;
import thaumcraft.common.entities.monster.EntityEldritchGuardian;
import thaumcraft.common.entities.monster.EntityPech;
import thaumcraft.common.golems.EntityThaumcraftGolem;

@Mod.EventBusSubscriber(modid = ThaumKraftu.MOD_ID)
public class TKEvents {

    @SubscribeEvent
    public static void SwordTickEvent(LivingHurtEvent event)
    {
        if(event.getEntity() == null || event.getSource().getTrueSource() == null )
            return;

        if(event.getSource().getTrueSource() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
            ItemStack stack = player.getHeldItemMainhand();

            double direction = Math.abs(player.rotationYaw - event.getEntity().rotationYaw) % 360;
            boolean flag = (direction > 180.0D ? 360.0D - direction : direction) < Math.abs(50.0D);

            if (stack.getItem() == KItems.electrum_dagger && flag) {
                event.setAmount(event.getAmount() * 3);
            }

            if(BaublesApi.isBaubleEquipped(player, KItems.golem_amulet) == 0)
            {
                if(event.getEntityLiving() instanceof EntityThaumcraftGolem)
                    event.setCanceled(true);
            }
        }

        if(event.getSource().getTrueSource() instanceof EntityThaumcraftGolem) {
            EntityThaumcraftGolem golem = (EntityThaumcraftGolem) event.getSource().getTrueSource();
            World world = golem.world;

            if (golem.world.isRemote)
                return;

            if (golem.getProperties().getHead().key.contains("FORAGE") && world.rand.nextInt(5) == 0) {
                golem.world.spawnEntity(new EntityItem(world, event.getEntity().posX, event.getEntity().posY + 0.5, event.getEntity().posZ, new ItemStack(Items.GOLD_NUGGET, world.rand.nextInt(2) + 1)));
            }
            if (golem.getProperties().getHead().key.contains("STALKER")) {
                event.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 100, 1));
            }
        }
    }


    @SubscribeEvent
    public static void GiveGoldEvent(PlayerInteractEvent.EntityInteract event){
        if(event.getTarget() == null || event.getEntityLiving() == null || !(event.getEntityLiving() instanceof EntityPlayer) || !(event.getTarget() instanceof EntityThaumcraftGolem))
            return;

        EntityPlayer player = (EntityPlayer) event.getEntityLiving();
        EntityThaumcraftGolem golem = (EntityThaumcraftGolem) event.getTarget();

        if(player.getHeldItem(event.getHand()).getItem() == Items.GOLD_INGOT) {
            if (golem.getProperties().getHead().key.contains("FORAGE") || golem.getProperties().getHead().key.contains("STALKER") || golem.getProperties().getHead().key.contains("THAUMIUM")) {
                player.getHeldItem(event.getHand()).setCount(player.getHeldItem(event.getHand()).getCount() - 1);
                if(!player.world.isRemote) {
                    golem.addPotionEffect(new PotionEffect(MobEffects.SPEED, 6000, 1));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityLivingDrops(LivingDropsEvent event) {
        if (event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof EntityPlayer) {
            if (((EntityPlayer) event.getSource().getTrueSource()).getHeldItemMainhand().getItem() == KItems.electrum_dagger && event.getEntityLiving() instanceof EntityPech  && Math.random() <= 1D / 2) {
                if(((EntityPech)event.getEntityLiving()).getPechType() == 1)
                    event.getDrops().add(new EntityItem(event.getEntityLiving().world, event.getEntityLiving().posX, event.getEntityLiving().posY, event.getEntityLiving().posZ, new ItemStack(KBlocks.pechHead_thaumaturge, 1, 0)));
                else if(((EntityPech)event.getEntityLiving()).getPechType() == 2){
                    event.getDrops().add(new EntityItem(event.getEntityLiving().world, event.getEntityLiving().posX, event.getEntityLiving().posY, event.getEntityLiving().posZ, new ItemStack(KBlocks.pechHead_hunter, 1, 0)));
                }
                else
                    event.getDrops().add(new EntityItem(event.getEntityLiving().world, event.getEntityLiving().posX, event.getEntityLiving().posY, event.getEntityLiving().posZ, new ItemStack(KBlocks.pechHead_normal, 1, 0)));
            }
        }

        if(event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof EntityThaumcraftGolem) {
            EntityThaumcraftGolem golem = (EntityThaumcraftGolem) event.getSource().getTrueSource();
            World world = golem.world;
            Entity target = event.getEntity();

            if (golem.world.isRemote)
                return;


            if (golem.getProperties().getHead().key.contains("THAUMIUM")) {
                if(world.rand.nextInt(5) == 0)
                    event.getDrops().add(new EntityItem(event.getEntityLiving().world, event.getEntityLiving().posX, event.getEntityLiving().posY, event.getEntityLiving().posZ, new ItemStack(ItemsTC.curio, 1, 4)));
                if(world.rand.nextInt(10) == 0) {
                    if (target instanceof EntitySkeleton)
                        event.getDrops().add(new EntityItem(event.getEntityLiving().world, event.getEntityLiving().posX, event.getEntityLiving().posY, event.getEntityLiving().posZ, new ItemStack(KItems.ancient_skull, 1)));
                    if (target instanceof EntityZombie)
                        event.getDrops().add(new EntityItem(event.getEntityLiving().world, event.getEntityLiving().posX, event.getEntityLiving().posY, event.getEntityLiving().posZ, new ItemStack(Items.SKULL, 1, 2)));
                    if (target instanceof EntityVillager)
                        event.getDrops().add(new EntityItem(event.getEntityLiving().world, event.getEntityLiving().posX, event.getEntityLiving().posY, event.getEntityLiving().posZ, new ItemStack(Items.EMERALD, world.rand.nextInt(2) + 1)));
                    if (target instanceof EntityEldritchGuardian)
                        event.getDrops().add(new EntityItem(event.getEntityLiving().world, event.getEntityLiving().posX, event.getEntityLiving().posY, event.getEntityLiving().posZ, new ItemStack(KItems.shadow_nugget, world.rand.nextInt(3) + 1)));
                    if (target instanceof EntityBrainyZombie)
                        target.entityDropItem(new ItemStack(ItemsTC.ingots, 1, 0), 0.5f);
                    if (target instanceof EntityWitch)
                        event.getDrops().add(new EntityItem(event.getEntityLiving().world, event.getEntityLiving().posX, event.getEntityLiving().posY, event.getEntityLiving().posZ, new ItemStack(KItems.parchment, 1)));
                }
            }
        }
    }

    @SubscribeEvent
    public static void TickEvent(TickEvent.PlayerTickEvent event){
        EntityPlayer player = event.player;
        for(EnumHand hand : EnumHand.values()) {
            ItemStack stack = player.getHeldItem(hand);

            if(player.ticksExisted % 10 == 0) {
                if (stack.getItem() == KItems.eternal_sword) {
                    if (stack.hasTagCompound() && stack.getTagCompound().hasKey("playerhealth")) {
                        if (player.getHealth() < stack.getTagCompound().getDouble("playerhealth")) {
                            player.setHealth((float) stack.getTagCompound().getDouble("playerhealth"));
                            stack.setItemDamage(stack.getItemDamage() + 1);
                        } else {
                            stack.getTagCompound().setDouble("playerhealth", player.getHealth());
                        }
                    } else {
                        if (!stack.hasTagCompound())
                            stack.setTagCompound(new NBTTagCompound());

                        stack.getTagCompound().setDouble("playerhealth", player.getHealth());
                    }

                }
            }
        }
    }
}
