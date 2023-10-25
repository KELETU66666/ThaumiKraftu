package com.keletu.thaumkraftu.block;

import com.keletu.thaumkraftu.ThaumKraftu;
import com.keletu.thaumkraftu.init.TKGuiHandler;
import com.keletu.thaumkraftu.recipe.ITKStation;
import com.keletu.thaumkraftu.tile.TileTKCraftingStation;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BlockCraftingStation extends BlockContainer implements ITKStation {
    public static final PropertyInteger ROTATION = PropertyInteger.create("rotation", 0, 3);

    public BlockCraftingStation() {
        super(Material.WOOD);
        this.setSoundType(SoundType.WOOD);
        this.setHardness(5.0f);
        this.setResistance(10.0f);
    }

    public boolean onBlockActivated(World par1World, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!par1World.isRemote) {
            player.openGui(ThaumKraftu.instance, TKGuiHandler.CraftingStation, par1World, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

    public int getMetaFromState(IBlockState state) {
        return state.getValue(ROTATION);
    }

    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(ROTATION, meta % 4);
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, ROTATION);
    }

    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack stack) {
        int var6 = MathHelper.floor((double)(entity.rotationYaw / 90.0f) + 0.5) & 3;
        world.setBlockState(pos, state.withProperty(ROTATION, var6), 2);
    }

    public TileEntity createNewTileEntity(World var1, int var2) {
        return new TileTKCraftingStation();
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity != null && tileEntity instanceof TileTKCraftingStation) {
            InventoryHelper.dropInventoryItems(world, pos, ((TileTKCraftingStation)tileEntity).inventoryCraft);
        }
        super.breakBlock(world, pos, state);
        world.removeTileEntity(pos);
    }
}
