package com.keletu.thaumkraftu.village;
import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import thaumcraft.common.world.aura.AuraHandler;
import thaumcraft.common.world.biomes.BiomeGenMagicalForest;


public class TKWorldGenerator implements IWorldGenerator
{
    public static TKWorldGenerator INSTANCE = new TKWorldGenerator();

    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        worldGeneration(random, chunkX, chunkZ, world, true);
    }
    
    public void worldGeneration(Random random, int chunkX, int chunkZ, World world, boolean newGen) {
        generateSurface(world, random, chunkX, chunkZ, newGen);
    }
    
    private void generateSurface(World world, Random random, int x, int z, boolean newGen) {
        int k = random.nextInt(3);
        int l;
        WorldGenManaPods worldgenpods = new WorldGenManaPods();
        for (k = 0; k < 10; k++) {
            l = x + random.nextInt(16) + 8;
            byte b0 = 64;
            int i1 = z + random.nextInt(16) + 8;
            if(world.getBiome(new BlockPos(l, b0, i1)) instanceof BiomeGenMagicalForest)
                worldgenpods.generate(world, random, new BlockPos(x, b0, z));
        }
    }
}