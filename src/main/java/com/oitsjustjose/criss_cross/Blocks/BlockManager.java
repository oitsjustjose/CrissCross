package com.oitsjustjose.criss_cross.Blocks;

import com.oitsjustjose.criss_cross.CrissCross;
import com.oitsjustjose.criss_cross.GUI.GUIHandler;
import com.oitsjustjose.criss_cross.Util.CropomatorRecipes;
import com.oitsjustjose.criss_cross.Util.ElectroextractorRecipes;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.block.Block;

public class BlockManager
{
	public static Block cropomator;
	public static Block electroextractor;
	
	public static void init()
	{
		cropomator = new BlockCropomator();
		CropomatorRecipes.initRecipes();
		CropomatorRecipes.initCatalysts();
		
		electroextractor = new BlockElectroextractor();
		ElectroextractorRecipes.initRecipes();
		ElectroextractorRecipes.initFuels();
		
		NetworkRegistry.INSTANCE.registerGuiHandler(CrissCross.instance, new GUIHandler());
	}
}