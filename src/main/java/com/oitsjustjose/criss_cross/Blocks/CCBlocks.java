package com.oitsjustjose.criss_cross.blocks;

import com.oitsjustjose.criss_cross.CrissCross;
import com.oitsjustjose.criss_cross.gui.GUIHandler;
import com.oitsjustjose.criss_cross.util.Reference;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.network.NetworkRegistry;

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