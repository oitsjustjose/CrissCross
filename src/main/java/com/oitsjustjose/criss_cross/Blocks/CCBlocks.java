package com.oitsjustjose.criss_cross.Blocks;

import com.oitsjustjose.criss_cross.CrissCross;
import com.oitsjustjose.criss_cross.GUI.GUIHandler;
import com.oitsjustjose.criss_cross.Recipes.CropomatorRecipes;
import com.oitsjustjose.criss_cross.Recipes.ElectroextractorRecipes;
import com.oitsjustjose.criss_cross.Recipes.CCMachineRecipes;
import com.oitsjustjose.criss_cross.Recipes.WoodchipperRecipes;
import com.oitsjustjose.criss_cross.TileEntity.TileEntityElectroextractor;
import com.oitsjustjose.criss_cross.Util.CCLog;
import com.oitsjustjose.criss_cross.Util.ConfigHandler;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CCBlocks
{
	public static Block cropomator;
	public static Block electroextractor;
	public static Block woodchipper;
	public static Block autosmith;
	
	public static void init()
	{
		cropomator = new BlockCropomator();
		electroextractor = new BlockElectroextractor();
		woodchipper = new BlockWoodchipper();
		autosmith = new BlockAutosmith();
		
		NetworkRegistry.INSTANCE.registerGuiHandler(CrissCross.instance, new GUIHandler());
	}
}