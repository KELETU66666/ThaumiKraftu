package com.keletu.thaumkraftu.entity;

import com.keletu.thaumkraftu.init.KItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXSlash;

public class EntityEnergyBall extends EntityThrowable
{
    public Entity shooter;
    public ItemStack revolver;

    public EntityEnergyBall(World w)
    {
        super(w);
        this.setEntityBoundingBox(new AxisAlignedBB(2, 2, 2, 2, 2, 2));
    }

    public EntityEnergyBall(World w, EntityLivingBase shooter)
    {
        super(w, shooter);
        revolver = shooter.getHeldItemMainhand();
        this.shooter = shooter;
        if(!revolver.isEmpty() && revolver.getItem() == KItems.moonstone_claymore)
        {
            float speedIndex = 1;

            this.motionX *=3;
            this.motionY *=3;
            this.motionZ *=3;

            this.motionX *= speedIndex;
            this.motionY *= speedIndex;
            this.motionZ *= speedIndex;
        }
    }

    public void onUpdate()
    {
        if(this.world instanceof WorldServer)
            PacketHandler.INSTANCE.sendToAllAround(new PacketFXSlash(this.getEntityId(), this.getEntityId()), new NetworkRegistry.TargetPoint(this.world.provider.getDimension(), this.posX, this.posY, this.posZ, 64.0));

        if(this.ticksExisted >= 200) //<- loop exit for primal
            this.setDead();

        super.onUpdate();

        if(isDead)
        {
            world.playSound(null, this.getPosition(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.AMBIENT, 3, 1F);
        }
    }

    protected float getGravityVelocity()
    {
        return 0.02F;
    }

    @Override
    protected void onImpact(RayTraceResult object)
    {
        if(this.isDead)
            return;

        if(world.isRemote)
            return;

        if(object.typeOfHit == RayTraceResult.Type.BLOCK) {
            if (noClip)
                return;
            if (this.world.isBlockNormalCube(object.getBlockPos(), true)) {
                if (this.world instanceof WorldServer)
                    ((WorldServer) world).spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX, this.posY + 0.5, this.posZ, 6, 0, 0, 0, 1D);
                this.setDead();
            }
        }

        if(object.typeOfHit == RayTraceResult.Type.ENTITY)
        {
            Entity e = object.entityHit;
            if(e == shooter)
                return;

            if(e instanceof EntityLivingBase)
            {
                EntityLivingBase elb = (EntityLivingBase) e;
                float initialDamage = 10;

                elb.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, shooter), initialDamage);
                if(this.world instanceof WorldServer)
                    ((WorldServer) world).spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX, this.posY + 0.5, this.posZ, 6, 0, 0, 0, 1D);

                this.setDead();
            }
        }
    }
}