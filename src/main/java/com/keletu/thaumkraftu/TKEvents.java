package com.keletu.thaumkraftu;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import com.keletu.thaumkraftu.init.KBlocks;
import com.keletu.thaumkraftu.init.KItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.entities.monster.EntityBrainyZombie;
import thaumcraft.common.entities.monster.EntityEldritchGuardian;
import thaumcraft.common.entities.monster.EntityPech;
import thaumcraft.common.entities.monster.boss.EntityThaumcraftBoss;
import thaumcraft.common.entities.monster.mods.ChampionModTainted;
import thaumcraft.common.entities.monster.mods.ChampionModifier;
import thaumcraft.common.golems.EntityThaumcraftGolem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static thaumcraft.common.lib.utils.EntityUtils.*;

@Mod.EventBusSubscriber(modid = ThaumKraftu.MOD_ID)
public class TKEvents {


    public static List<String> infernalMobs = new ArrayList<>();

    public static void infernalMobList() {
        infernalMobs.add("Zombie");
        infernalMobs.add("Spider");
        infernalMobs.add("Blaze");
        infernalMobs.add("Enderman");
        infernalMobs.add("Skeleton");
        infernalMobs.add("Witch");
        infernalMobs.add("Thaumcraft:EldritchCrab");
        infernalMobs.add("Thaumcraft:Taintacle");
        infernalMobs.add("Thaumcraft:BrainyZombie");
    }

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

            if(BaublesApi.isBaubleEquipped(player, KItems.golem_amulet) == BaubleType.CHARM.getValidSlots()[0])
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

    @SubscribeEvent
    public static void entitySpawns(EntityJoinWorldEvent event) {
        if (!event.getWorld().isRemote) {
            if (event.getEntity() instanceof EntityCreature && ((EntityCreature)event.getEntity()).getEntityAttribute(ThaumcraftApiHelper.CHAMPION_MOD) != null && ((EntityCreature)event.getEntity()).getEntityAttribute(ThaumcraftApiHelper.CHAMPION_MOD).getAttributeValue() == 13.0) {
                IAttributeInstance modai = ((EntityCreature)event.getEntity()).getEntityAttribute(ChampionModTainted.TAINTED_MOD);
                modai.removeModifier(new AttributeModifier(UUID.fromString("2cb22137-a9d8-4417-ae06-de0e70f11b4c"), "istainted", 1.0, 0));
                modai.applyModifier(new AttributeModifier(UUID.fromString("2cb22137-a9d8-4417-ae06-de0e70f11b4c"), "istainted", 0.0, 0));
            }
            if (event.getEntity() instanceof EntityMob) {
                EntityMob mob = (EntityMob)event.getEntity();
                if(infernalMobs.contains(mob.getName()) && event.getWorld().rand.nextInt(100) <= ConfigsTK.championChance - 1)
                        makeChampion(mob, false);
            }
        }
    }

    public static void makeChampion(EntityMob entity, boolean persist) {
        int type = entity.world.rand.nextInt(ChampionModifier.mods.length);
        if (entity instanceof EntityCreeper) {
            type = 0;
        }

        IAttributeInstance modai = entity.getEntityAttribute(ThaumcraftApiHelper.CHAMPION_MOD);
        modai.removeModifier(ChampionModifier.mods[type].attributeMod);
        modai.applyModifier(ChampionModifier.mods[type].attributeMod);
        IAttributeInstance sai;
        IAttributeInstance mai;
        if (!(entity instanceof EntityThaumcraftBoss)) {
            sai = entity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
            sai.removeModifier(CHAMPION_HEALTH);
            sai.applyModifier(CHAMPION_HEALTH);
            mai = entity.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
            mai.removeModifier(CHAMPION_DAMAGE);
            mai.applyModifier(CHAMPION_DAMAGE);
            entity.heal(25.0F);
            entity.setCustomNameTag(ChampionModifier.mods[type].getModNameLocalized() + " " + entity.getName());
        } else {
            ((EntityThaumcraftBoss)entity).generateName();
        }

        if (persist) {
            entity.enablePersistence();
        }

        switch (type) {
            case 0:
                sai = entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
                sai.removeModifier(BOLDBUFF);
                sai.applyModifier(BOLDBUFF);
                break;
            case 3:
                mai = entity.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
                mai.removeModifier(MIGHTYBUFF);
                mai.applyModifier(MIGHTYBUFF);
                break;
            case 5:
                int bh = (int)entity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue() / 2;
                entity.setAbsorptionAmount(entity.getAbsorptionAmount() + (float)bh);
        }

    }
}
