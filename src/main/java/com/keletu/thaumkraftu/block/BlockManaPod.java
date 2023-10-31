package com.keletu.thaumkraftu.block;

import com.keletu.thaumkraftu.init.KItems;
import com.keletu.thaumkraftu.item.ItemManaBean;
import com.keletu.thaumkraftu.tile.TileManaPod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCarrot;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.internal.WorldCoordinates;
import thaumcraft.common.config.ConfigItems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class BlockManaPod extends Block {
  public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, AGE);
  }

  protected int getAge(IBlockState state) {
    return state.getValue(this.getAgeProperty());
  }

  public IBlockState withAge(int age) {
    return this.getDefaultState().withProperty(this.getAgeProperty(), age);
  }

  public IBlockState getStateFromMeta(int meta) {
    return this.withAge(meta);
  }

  public int getMetaFromState(IBlockState state) {
    return this.getAge(state);
  }

  public BlockManaPod() {
    super(Material.PLANTS);
    this.setDefaultState(this.blockState.getBaseState().withProperty(this.getAgeProperty(), 0));
    setTickRandomly(true);
    this.blockHardness = 0.5F;
  }

  protected PropertyInteger getAgeProperty() {
    return AGE;
  }

  public float getBlockHardness(IBlockState blockState, World world, BlockPos pos) {
    float md = (8 - getMetaFromState(blockState));
    return super.getBlockHardness(blockState, world, pos) / md;
  }

  @SideOnly(Side.CLIENT)
  public BlockRenderLayer getRenderLayer()
  {
    return BlockRenderLayer.CUTOUT;
  }
  
  public boolean isFullCube(IBlockState state) {
    return false;
  }
  
  public boolean isOpaqueCube(IBlockState state) {
    return false;
  }

  //public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
  //  getBoundingBox(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
  //  return super.getCollisionBoundingBox(blockState, worldIn, pos);
  //}
//
  //public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
  //  int l = getMetaFromState(source.getBlockState(pos));
  //  switch (l) {
  //    case 0:
  //      return new AxisAlignedBB(0.25F, BlockRenderer.W12, 0.25F, 0.75F, 1.0F, 0.75F);
  //      break;
  //    case 1:
  //      func_149676_a(0.25F, BlockRenderer.W10, 0.25F, 0.75F, 1.0F, 0.75F);
  //      break;
  //    case 2:
  //      func_149676_a(0.25F, BlockRenderer.W8, 0.25F, 0.75F, 1.0F, 0.75F);
  //      break;
  //    case 3:
  //      func_149676_a(0.25F, BlockRenderer.W6, 0.25F, 0.75F, 1.0F, 0.75F);
  //      break;
  //    case 4:
  //      func_149676_a(0.25F, BlockRenderer.W5, 0.25F, 0.75F, 1.0F, 0.75F);
  //      break;
  //    case 5:
  //      func_149676_a(0.25F, BlockRenderer.W4, 0.25F, 0.75F, 1.0F, 0.75F);
  //      break;
  //    case 6:
  //      func_149676_a(0.25F, BlockRenderer.W3, 0.25F, 0.75F, 1.0F, 0.75F);
  //      break;
  //    case 7:
  //      func_149676_a(0.25F, BlockRenderer.W2, 0.25F, 0.75F, 1.0F, 0.75F);
  //      break;
  //  }
  //}
  //
  //@SideOnly(Side.CLIENT)
  //public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
  //  getBoundingBox(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
  //  return super.getSelectedBoundingBox(state, worldIn, pos);
  //}

  @Override
  public void updateTick(World par1World, BlockPos pos, IBlockState state, Random rand) {
    if (!canBlockStay(par1World, pos, state)) {
      dropBlockAsItem(par1World, pos, state, 0);
      par1World.setBlockToAir(pos);
    } else if (par1World.rand.nextInt(30) == 0) {
      TileEntity tile = par1World.getTileEntity(pos);
      if (tile != null && tile instanceof TileManaPod)
        ((TileManaPod)tile).checkGrowth(); 
      st.remove(new WorldCoordinates(pos, par1World.provider.getDimension()));
    } 
  }
  
  public boolean canBlockStay(World par1World, BlockPos pos, IBlockState state) {
    Biome biome = par1World.getBiome(pos);
    boolean magicBiome = false;
    if (biome != null)
      magicBiome = BiomeDictionary.hasType(biome, BiomeDictionary.Type.MAGICAL);
    Block i1 = par1World.getBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ())).getBlock();
    return (magicBiome && (i1 == Blocks.LOG || i1 == Blocks.LOG2 || i1 == BlocksTC.logGreatwood));
  }

  public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side) {
    Biome biome = world.getBiome(pos);
    boolean magicBiome = false;
    if (biome != null)
      magicBiome = BiomeDictionary.hasType(biome, BiomeDictionary.Type.MAGICAL);
    Block i1 = world.getBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ())).getBlock();
    boolean b = (i1 == Blocks.LOG || i1 == Blocks.LOG2 || i1 == BlocksTC.logGreatwood);
    return (side.getIndex() == 0 && b && magicBiome);
  }
  
  public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
    return getMetaFromState(state);
  }

  public void neighborChanged(IBlockState state, World par1World, BlockPos pos, Block blockIn, BlockPos fromPos) {
    if (!canBlockStay(par1World, pos, state)) {
      dropBlockAsItem(par1World, pos, state, 0);
      par1World.setBlockToAir(pos);
    } 
  }
  
  static HashMap<WorldCoordinates, Aspect> st = new HashMap<>();
  
  public void breakBlock(World world, BlockPos pos, IBlockState state) {
    TileEntity tile = world.getTileEntity(pos);
    if (tile != null && tile instanceof TileManaPod && ((TileManaPod)tile).aspect != null)
      st.put(new WorldCoordinates(pos, world.provider.getDimension()), ((TileManaPod)tile).aspect);
    super.breakBlock(world, pos, state);
  }
  
  public ArrayList<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
    ArrayList<ItemStack> dropped = new ArrayList<ItemStack>();
    int metadata = getMetaFromState(state);
    if (metadata < 2)
      return dropped; 
    byte b0 = 1;
    if (metadata == 7 && ((World) world).rand.nextFloat() > 0.33F)
      b0 = 2; 
    Aspect aspect = Aspect.PLANT;
    if (st.containsKey(new WorldCoordinates(pos, ((World) world).provider.getDimension()))) {
      aspect = st.get(new WorldCoordinates(pos, ((World) world).provider.getDimension()));
    } else {
      TileEntity tile = world.getTileEntity(pos);
      if (tile != null && tile instanceof TileManaPod && ((TileManaPod)tile).aspect != null)
        aspect = ((TileManaPod)tile).aspect; 
    } 
    for (int k1 = 0; k1 < b0; k1++) {
      ItemStack i = new ItemStack(KItems.mana_bean);
      ((ItemManaBean)i.getItem()).setAspects(i, (new AspectList()).add(aspect, 1));
      dropped.add(i);
    } 
    st.remove(new WorldCoordinates(pos, ((World) world).provider.getDimension()));
    return dropped;
  }
  
  @SideOnly(Side.CLIENT)
  public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
    return KItems.mana_bean.getDefaultInstance();
  }
  
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return Item.getItemById(0);
  }
  
  public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
    return true;
  }
  
  public boolean hasTileEntity(IBlockState state) {
    return true;
  }
  
  public TileEntity createTileEntity(World world, IBlockState state) {
    return new TileManaPod();
  }
}
