package com.keletu.thaumkraftu;

import net.minecraftforge.common.config.Config;

@Config(modid = ThaumKraftu.MOD_ID)
public class ConfigsTK
{
	@Config.LangKey("Pods Aspect amuont")
	@Config.RangeInt(min = 1, max = 128)
	@Config.RequiresMcRestart
	public static int podAspect = 5;
}