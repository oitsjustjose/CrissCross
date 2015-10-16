package com.oitsjustjose.criss_cross.Blocks;

import com.oitsjustjose.criss_cross.CrissCross;
import com.oitsjustjose.criss_cross.GUI.GUIHandler;
import com.oitsjustjose.criss_cross.Recipes.CropomatorRecipes;
import com.oitsjustjose.criss_cross.Recipes.ElectroextractorRecipes;
import com.oitsjustjose.criss_cross.Recipes.WoodchipperRecipes;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.block.Block;

public class CCBlocks
{
	public static Block cropomator;
	public static Block electroextractor;
	public static Block woodchipper;
	
	public static void init()
	{
		cropomator = new BlockCropomator();
		CropomatorRecipes.initRecipes();
		CropomatorRecipes.initCatalysts();
		
		electroextractor = new BlockElectroextractor();
		ElectroextractorRecipes.initRecipes();
		ElectroextractorRecipes.initFuels();
		
		woodchipper = new BlockWoodchipper();
		WoodchipperRecipes.initRecipes();
		WoodchipperRecipes.initFuels();
		
		NetworkRegistry.INSTANCE.registerGuiHandler(CrissCross.instance, new GUIHandler());
	}
}