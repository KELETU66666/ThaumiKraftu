package com.keletu.thaumkraftu.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SuppressWarnings("rawtypes")
public class RenderEnergyBall extends Render{

    public RenderEnergyBall() {
        super(Minecraft.getMinecraft().getRenderManager());
    }

    @Override
    public void doRender(Entity p_76986_1_, double p_76986_2_,
                         double p_76986_4_, double p_76986_6_, float p_76986_8_,
                         float p_76986_9_) {

    }

    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return null;
    }

}