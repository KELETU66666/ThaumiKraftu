package com.keletu.thaumkraftu.init;

import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ScanEntity;
import thaumcraft.api.research.ScanningManager;
import thaumcraft.common.entities.projectile.EntityAlumentum;
import thaumcraft.common.entities.projectile.EntityCausalityCollapser;

public class KResearch {

    public static void registerResearch() {
        ResearchCategories.registerCategory("THAUMKRAFTU", "HEDGEALCHEMY", null, new ResourceLocation("thaumkraftu", "textures/models/research/r_pech_thaum.png"), new ResourceLocation("thaumkraftu", "textures/models/research/greatwood.png"));

        ThaumcraftApi.registerResearchLocation(new ResourceLocation("thaumkraftu", "research/research.json"));

        ScanningManager.addScannableThing(new ScanMoon());
        ScanningManager.addScannableThing(new ScanEntity("!Alumentum", EntityAlumentum.class, true));
        ScanningManager.addScannableThing(new ScanEntity("!Collapser", EntityCausalityCollapser.class, true));
    }
}
