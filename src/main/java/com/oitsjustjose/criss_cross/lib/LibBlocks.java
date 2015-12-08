package com.oitsjustjose.criss_cross.lib;

import com.oitsjustjose.criss_cross.CrissCross;
import com.oitsjustjose.criss_cross.blocks.BlockCobblegen;
import com.oitsjustjose.criss_cross.blocks.BlockCropomator;
import com.oitsjustjose.criss_cross.blocks.BlockElectroextractor;
import com.oitsjustjose.criss_cross.blocks.BlockStonegen;
import com.oitsjustjose.criss_cross.blocks.BlockWoodchipper;
import com.oitsjustjose.criss_cross.gui.GUIHandler;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class LibBlocks
{
	public static Block cropomator;
	public static Block electroextractor;
	public static Block woodchipper;
	public static Block cobblegen;
	public static Block stonegen;
	public static Block mobGrinder;

	public static void init()
	{
		cropomator = new BlockCropomator();
		electroextractor = new BlockElectroextractor();
		woodchipper = new BlockWoodchipper();
		cobblegen = new BlockCobblegen();
		stonegen = new BlockStonegen();
		// mobGrinder = new BlockMobGrinder();
		NetworkRegistry.INSTANCE.registerGuiHandler(CrissCross.instance, new GUIHandler());
	}
}