package com.oitsjustjose.criss_cross.Recipes;

import com.oitsjustjose.criss_cross.Blocks.CCBlocks;
import com.oitsjustjose.criss_cross.Items.CCItems;
import com.oitsjustjose.criss_cross.Util.CCLog;
import com.oitsjustjose.criss_cross.Util.ConfigHandler;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class CCCraftingRecipes
{
	public static void init()
	{
		cropomatorRecipe();
		electroextractorRecipe();
		woodChipperRecipe();
		initBucketRecipes();
	}
	
	static void initBucketRecipes()
	{
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(CCItems.infiniBucket, 1, 0),
			"DFD", "WBW", "DFD", 'W', Items.water_bucket, 'B', Items.bucket, 'D', "gemDiamond", 'F', "blockDiamond"
		));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(CCItems.infiniBucket, 1, 1),
				"OGO",
				"GBG",
				"OGO",
				'B', Items.bucket, 'G', "blockGlass", 'O', Blocks.obsidian
			));
		
	}
	
	static void woodChipperRecipe()
	{
		String[] unlocItem = ConfigHandler.woodchipperRecipeItem.split(":");
		ItemStack centerItem = new ItemStack(Blocks.diamond_block);
		
		if(GameRegistry.findItemStack(unlocItem[0], unlocItem[1], 1) != null)
		{
			ItemStack newStack = GameRegistry.findItemStack(unlocItem[0], unlocItem[1], 1);
			if(unlocItem.length == 2)
				centerItem = newStack;
			if(unlocItem.length == 3)
				centerItem = new ItemStack(newStack.getItem(), 1, Integer.parseInt(unlocItem[2]));
		}
		else
		{
			CCLog.warn("Item " + ConfigHandler.woodchipperRecipeItem
					+ "could not be added as part of the recipe of the Woodchipper.");
			CCLog.warn("Please confirm you have the name and formatting correct.");
		}
		
		GameRegistry.addRecipe(new ItemStack(CCBlocks.woodchipper), new Object[]
		{
			"I#I", "SCS", "I#I", '#', Items.diamond_axe, 'I', Blocks.iron_bars, 'S', Items.golden_axe, 'C',
			centerItem
		});
	}
	
	static void electroextractorRecipe()
	{
		String[] unlocItem = ConfigHandler.electroextractorRecipeItem.split(":");
		ItemStack centerItem = new ItemStack(Blocks.diamond_block);
		
		if(GameRegistry.findItemStack(unlocItem[0], unlocItem[1], 1) != null)
		{
			ItemStack newStack = GameRegistry.findItemStack(unlocItem[0], unlocItem[1], 1);
			if(unlocItem.length == 2)
				centerItem = newStack;
			if(unlocItem.length == 3)
				centerItem = new ItemStack(newStack.getItem(), 1, Integer.parseInt(unlocItem[2]));
		}
		else
		{
			CCLog.warn("Item " + ConfigHandler.electroextractorRecipeItem
					+ " could not be added as part of the recipe of the electroextractor.");
			CCLog.warn("Please confirm you have the name and formatting correct.");
			
		}
		
		GameRegistry.addRecipe(new ItemStack(CCBlocks.electroextractor), new Object[]
		{
			"I#I", "SCS", "I#I", '#', Blocks.piston, 'I', Blocks.iron_bars, 'S', Items.golden_pickaxe, 'C',
			centerItem
		});
	}
	
	static void cropomatorRecipe()
	{
		String[] unlocItem = ConfigHandler.cropomatorRecipeItem.split(":");
		ItemStack centerItem = new ItemStack(Blocks.diamond_block);
		
		if(GameRegistry.findItemStack(unlocItem[0], unlocItem[1], 1) != null)
		{
			ItemStack newStack = GameRegistry.findItemStack(unlocItem[0], unlocItem[1], 1);
			if(unlocItem.length == 2)
				centerItem = newStack;
			if(unlocItem.length == 3)
				centerItem = new ItemStack(newStack.getItem(), 1, Integer.parseInt(unlocItem[2]));
		}
		else
		{
			CCLog.warn("Item " + ConfigHandler.cropomatorRecipeItem
					+ " could not be added as part of the recipe of the cropomator.");
			CCLog.warn("Please confirm you have the name and formatting correct.");
		}
		
		GameRegistry.addRecipe(new ItemStack(CCBlocks.cropomator), new Object[]
		{
			"I#I", "SCS", "I#I", '#', Blocks.hay_block, 'I', Items.iron_ingot, 'S', Items.golden_hoe, 'C',
			centerItem
		});
	}
}