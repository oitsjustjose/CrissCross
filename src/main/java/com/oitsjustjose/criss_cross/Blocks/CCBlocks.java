package com.oitsjustjose.criss_cross.Blocks;

import com.oitsjustjose.criss_cross.CrissCross;
import com.oitsjustjose.criss_cross.GUI.GUIHandler;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.block.Block;

public class CCBlocks
{
	public static Block cropomator;
	public static Block electroextractor;
	public static Block woodchipper;
	public static Block cobblegen;
	public static Block stonegen;
	
	public static void init()
	{
		cropomator = new BlockCropomator();
		electroextractor = new BlockElectroextractor();
		woodchipper = new BlockWoodchipper();
		cobblegen = new BlockCobblegen();
		stonegen = new BlockStonegen();
		
		NetworkRegistry.INSTANCE.registerGuiHandler(CrissCross.instance, new GUIHandler());
	}
}