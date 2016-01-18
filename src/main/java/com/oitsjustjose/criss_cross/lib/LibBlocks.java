package com.oitsjustjose.criss_cross.lib;

import com.oitsjustjose.criss_cross.blocks.BlockCobblegen;
import com.oitsjustjose.criss_cross.blocks.BlockCropomator;
import com.oitsjustjose.criss_cross.blocks.BlockElectroextractor;
import com.oitsjustjose.criss_cross.blocks.BlockScribe;
import com.oitsjustjose.criss_cross.blocks.BlockStonegen;
import com.oitsjustjose.criss_cross.blocks.BlockWoodchipper;

import net.minecraft.block.Block;

public class LibBlocks
{
	public static Block cropomator;
	public static Block electroextractor;
	public static Block woodchipper;
	public static Block cobblegen;
	public static Block stonegen;
	public static Block scribe;
	public static Block atmosManipulator;

	public static void init()
	{
		cropomator = new BlockCropomator();
		electroextractor = new BlockElectroextractor();
		woodchipper = new BlockWoodchipper();
		cobblegen = new BlockCobblegen();
		stonegen = new BlockStonegen();
		scribe = new BlockScribe();
		// TODO: atmosManipulator = new BlockAtmosManipulator();
	}
}