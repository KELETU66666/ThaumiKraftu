// Decompiled with: CFR 0.152
// Class Version: 8
package com.keletu.thaumkraftu.client.render;

import com.keletu.thaumkraftu.ThaumKraftu;
import com.keletu.thaumkraftu.client.model.ModelCraftingStation;
import com.keletu.thaumkraftu.tile.TileTKCraftingStation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class BlockCraftingStationRenderer extends TileEntitySpecialRenderer<TileTKCraftingStation> {
    private final ModelCraftingStation model = new ModelCraftingStation();
    private static final ResourceLocation field_110631_g = new ResourceLocation(ThaumKraftu.MOD_ID, "textures/models/block/crafting_station.png");

    public BlockCraftingStationRenderer() {
    }

    public void render(TileTKCraftingStation var1, double var2, double var4, double var6, float var8, int blockDamage, float partialTick) {
        int rotation = 0;
        if (var1 != null && var1.getPos() != BlockPos.ORIGIN) {
            rotation = var1.getBlockMetadata() % 4;
        }
        GlStateManager.pushMatrix();
        GlStateManager.enableCull();
        GlStateManager.translate((float)var2 + 0.5f, (float)var4 + 1.4f, (float)var6 + 0.5f);
        GlStateManager.scale(0.95f, 0.95f, 0.95f);
        GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate((float)(90 * rotation), 0.0f, 1.0f, 0.0f);
        this.bindTexture(field_110631_g);
        this.model.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        GlStateManager.disableCull();
        GlStateManager.popMatrix();
    }
}
