// Decompiled with: CFR 0.152
// Class Version: 8
package com.keletu.thaumkraftu.client.gui;

import com.keletu.thaumkraftu.ThaumKraftu;
import com.keletu.thaumkraftu.container.ContainerTKCraftingStation;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

public class GuiCraftingStation extends GuiContainer {
    private final ResourceLocation resource = new ResourceLocation(ThaumKraftu.MOD_ID, "textures/gui/crafting_station.png");
    private final ContainerTKCraftingStation container;

    public GuiCraftingStation(ContainerTKCraftingStation container) {
        super(container);
        this.container = container;
        this.allowUserInput = false;
        this.ySize = 180;
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.renderEngine.bindTexture(this.resource);
        int l = (this.width - this.xSize) / 2;
        int i1 = (this.height - this.ySize) / 2;
        String title = I18n.translateToLocal("tile.tk.crafting_station.name");
        this.drawTexturedModalRect(l, i1, 0, 0, this.xSize, this.ySize);
        this.fontRenderer.drawString(title, this.guiLeft + 4, this.guiTop + 4, 0x404040);
        this.fontRenderer.drawString(I18n.translateToLocal("container.inventory"), this.guiLeft + 4, this.guiTop + 87, 0x404040);
    }
}
