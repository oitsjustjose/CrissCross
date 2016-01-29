package com.oitsjustjose.criss_cross.recipes;

import com.oitsjustjose.criss_cross.lib.Config;
import com.oitsjustjose.criss_cross.lib.LibBlocks;
import com.oitsjustjose.criss_cross.lib.LibItems;
import com.oitsjustjose.criss_cross.util.LogHelper;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class CraftingRecipes
{
	public static void init()
	{
		if (Config.enableCropomator)
			cropomatorRecipe();
		if (Config.enableElectroextractor)
			electroextractorRecipe();
		if (Config.enableWoodchipper)
			woodchipperRecipe();
		if (Config.enableInfiniapple)
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LibItems.infiniApple, 1, 0), "###", "#N#", "###", '#', new ItemStack(Items.golden_apple, 1, 1), 'N', Items.nether_star));
		if(Config.enableBuckets)
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LibItems.buckets, 1, 0), "DFD", "WBW", "DFD", 'W', Items.water_bucket, 'B', Items.bucket, 'D', "gemDiamond", 'F', "blockDiamond"));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LibItems.buckets, 1, 1), "OGO", "GBG", "OGO", 'B', Items.bucket, 'G', "blockGlass", 'O', Blocks.obsidian));
		}
		if (Config.enableMantlesmashers)
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LibItems.mantleSmasherMkI), "## ", "#PI", " II", '#', "blockEmerald", 'I', "blockIron", 'P', Items.diamond_pickaxe));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LibItems.mantleSmasherMkII), "## ", "#PI", " IN", '#', "blockDiamond", 'I', "blockGold", 'P', LibItems.mantleSmasherMkI, 'N', Items.nether_star));
		}
	}

	static void electroextractorRecipe()
	{
		LogHelper.info(">>Loading Electroextractor Recipe");
		String[] unlocItem = Config.electroextractorRecipeItem.split(":");
		ItemStack centerItem = new ItemStack(Blocks.diamond_block);

		if (Config.findItemStack(unlocItem[0], unlocItem[1]) != null)
		{
			ItemStack newStack = Config.findItemStack(unlocItem[0], unlocItem[1]);
			if (unlocItem.length == 2)
				centerItem = newStack;
			if (unlocItem.length == 3)
				centerItem = new ItemStack(newStack.getItem(), 1, Integer.parseInt(unlocItem[2]));
		}
		else
		{
			LogHelper.warn(Config.electroextractorRecipeItem + " could not be implemented into the Electroextractor's Crafting Recipe.");
			LogHelper.warn("The fallback, " + centerItem.getDisplayName() + ", has been used instead");
			LogHelper.warn("Please confirm you have the name and formatting correct.");
			System.out.println();
		}

		GameRegistry.addRecipe(new ItemStack(LibBlocks.electroextractor), new Object[] { "I#I", "SCS", "I#I", '#', Blocks.piston, 'I', Blocks.iron_bars, 'S', Items.golden_pickaxe, 'C', centerItem });
		LogHelper.info(">>Electroextractor Recipe Loaded!");
	}

	static void cropomatorRecipe()
	{
		LogHelper.info(">>Loading Cropomator Recipe");
		String[] unlocItem = Config.cropomatorRecipeItem.split(":");
		ItemStack centerItem = new ItemStack(Blocks.diamond_block);

		if (Config.findItemStack(unlocItem[0], unlocItem[1]) != null)
		{
			ItemStack newStack = Config.findItemStack(unlocItem[0], unlocItem[1]);
			if (unlocItem.length == 2)
				centerItem = newStack;
			if (unlocItem.length == 3)
				centerItem = new ItemStack(newStack.getItem(), 1, Integer.parseInt(unlocItem[2]));
		}
		else
		{
			LogHelper.warn(Config.cropomatorRecipeItem + " could not be implemented into the Cropomator's Crafting Recipe.");
			LogHelper.warn("The fallback, " + centerItem.getDisplayName() + ", has been used instead");
			LogHelper.warn("Please confirm you have the name and formatting correct.");
			System.out.println();
		}
		GameRegistry.addRecipe(new ItemStack(LibBlocks.cropomator), new Object[] { "I#I", "SCS", "I#I", '#', Blocks.hay_block, 'I', Items.iron_ingot, 'S', Items.golden_hoe, 'C', centerItem });
		LogHelper.info(">>Cropomator Recipe Loaded!");
	}

	static void woodchipperRecipe()
	{
		LogHelper.info(">>Loading Woodchipper Recipe");
		String[] unlocItem = Config.woodchipperRecipeItem.split(":");
		ItemStack centerItem = new ItemStack(Blocks.diamond_block);

		if (Config.findItemStack(unlocItem[0], unlocItem[1]) != null)
		{
			ItemStack newStack = Config.findItemStack(unlocItem[0], unlocItem[1]);
			if (unlocItem.length == 2)
				centerItem = newStack;
			if (unlocItem.length == 3)
				centerItem = new ItemStack(newStack.getItem(), 1, Integer.parseInt(unlocItem[2]));
		}
		else
		{
			LogHelper.warn(Config.woodchipperRecipeItem + " could not be implemented into the Woodchipper's Crafting Recipe.");
			LogHelper.warn("The fallback, " + centerItem.getDisplayName() + ", has been used instead");
			LogHelper.warn("Please confirm you have the name and formatting correct.");
			System.out.println();
		}
		GameRegistry.addRecipe(new ItemStack(LibBlocks.woodchipper), new Object[] { "I#I", "SCS", "I#I", '#', Items.diamond_axe, 'I', Blocks.iron_bars, 'S', Items.golden_axe, 'C', centerItem });
		LogHelper.info(">>Woodchipper Recipe Loaded!");

	}
}