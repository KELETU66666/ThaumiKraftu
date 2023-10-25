package com.keletu.thaumkraftu.container;

import com.keletu.thaumkraftu.recipe.StationRecipes;
import com.keletu.thaumkraftu.tile.TileTKCraftingStation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.crafting.ContainerDummy;


public class ContainerTKCraftingStation extends Container
{
    private TileTKCraftingStation tileEntity;
    private InventoryPlayer ip;
    private World world;
    public InventoryCraftResult craftResult;
    private long lastCheck;
    
    public ContainerTKCraftingStation(InventoryPlayer par1InventoryPlayer, TileTKCraftingStation e) {
        craftResult = new InventoryCraftResult();
        world = e.getWorld();
        lastCheck = 0L;
        tileEntity = e;
        tileEntity.inventoryCraft.eventHandler = this;
        ip = par1InventoryPlayer;
        addSlotToContainer(new SlotCrafting(par1InventoryPlayer.player, tileEntity.inventoryCraft, this.craftResult, 0, 133, 41));
        for (int row = 0; row < 4; ++row) {
            for (int col = 0; col < 4; ++col) {
                if(row== 0 && col == 3){
                    this.addSlotToContainer(new Slot(tileEntity.inventoryCraft, col + row * 4, 17 + col * 18, 14 + row * 18));
                }else if(col == 3){
                    this.addSlotToContainer(new Slot(tileEntity.inventoryCraft, col + row * 4, 17 + col * 18, 14 + row * 18));
                }else{
                    this.addSlotToContainer(new Slot(tileEntity.inventoryCraft, col + row * 4, 17 + col * 18, 14 + row * 18));
                }
            }
        }


        // Player inventory slots
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, col + row * 9 + 9, 8 + col * 18, 98 + row * 18));
            }
        }

        // Player hotbar slots
        for (int col = 0; col < 9; ++col) {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, col, 8 + col * 18, 156));
        }

        onCraftMatrixChanged(tileEntity.inventoryCraft);
    }
    
    public void addListener(IContainerListener par1ICrafting) {
        super.addListener(par1ICrafting);
    }
    
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        long t = System.currentTimeMillis();
        if (t > lastCheck) {
            lastCheck = t + 500L;
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2) {
    }

    public void onCraftMatrixChanged(IInventory par1IInventory) {
        if (!this.world.isRemote) {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)this.ip.player;
            ItemStack itemstack = ItemStack.EMPTY;
            IRecipe irecipe = StationRecipes.match(this.tileEntity.inventoryCraft, this.world);
            if (irecipe != null && (irecipe.isDynamic() || !this.world.getGameRules().getBoolean("doLimitedCrafting") || entityplayermp.getRecipeBook().isUnlocked(irecipe))) {
                this.craftResult.setRecipeUsed(irecipe);
                itemstack = irecipe.getCraftingResult(this.tileEntity.inventoryCraft);
            }
            this.craftResult.setInventorySlotContents(0, itemstack);
            entityplayermp.connection.sendPacket(new SPacketSetSlot(this.windowId, 0, itemstack));
        }
    }
    
    public void onContainerClosed(EntityPlayer par1EntityPlayer) {
        super.onContainerClosed(par1EntityPlayer);
        if (!tileEntity.getWorld().isRemote) {
            tileEntity.inventoryCraft.eventHandler = new ContainerDummy();
        }
    }
    
    public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
        return tileEntity.getWorld().getTileEntity(tileEntity.getPos()) == tileEntity && par1EntityPlayer.getDistanceSqToCenter(tileEntity.getPos()) <= 64.0;
    }

    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par1) {
        ItemStack var2 = ItemStack.EMPTY;
        Slot var3 = this.inventorySlots.get(par1);
        if (var3 != null && var3.getHasStack()) {
            ItemStack var4 = var3.getStack();
            var2 = var4.copy();
            if (par1 == 0) {
                if (!this.mergeItemStack(var4, 17, 53, true)) {
                    return ItemStack.EMPTY;
                }
                var3.onSlotChange(var4, var2);
            } else if (par1 >= 17 && par1 < 44 ? !this.mergeItemStack(var4, 44, 53, false) : (par1 >= 44 && par1 < 53 ? !this.mergeItemStack(var4, 17, 44, false) : !this.mergeItemStack(var4, 17, 53, false))) {
                return ItemStack.EMPTY;
            }
            if (var4.getCount() == 0) {
                var3.putStack(ItemStack.EMPTY);
            } else {
                var3.onSlotChanged();
            }
            if (var4.getCount() == var2.getCount()) {
                return ItemStack.EMPTY;
            }
            var3.onTake(par1EntityPlayer, var4);
        }
        return var2;
    }
    
    public boolean canMergeSlot(ItemStack stack, Slot slot) {
        return slot.inventory != craftResult && super.canMergeSlot(stack, slot);
    }
}