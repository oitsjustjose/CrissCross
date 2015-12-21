package com.oitsjustjose.criss_cross.recipes;

import com.oitsjustjose.criss_cross.lib.ConfigHandler;
import com.oitsjustjose.criss_cross.lib.LibBlocks;
import com.oitsjustjose.criss_cross.lib.LibItems;
import com.oitsjustjose.criss_cross.util.LogHelper;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class CraftingRecipes
{
	public static void init()
	{
		initStaticRecipes();
		initUserRecipes();
		CraftingManager.getInstance().getRecipeList().add(new PouchColorRecipes());
		CraftingManager.getInstance().getRecipeList().add(new PouchRollbackRecipes());
		RecipeSorter.register("CrissCross:PouchColorRecipes", PouchColorRecipes.class, Category.SHAPELESS, "");
		RecipeSorter.register("CrissCross:PouchRollbackRecipes", PouchRollbackRecipes.class, Category.SHAPELESS, "");
	}

	static void initStaticRecipes()
	{
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LibBlocks.cobblegen), "CPC", "#$#", "CPC", '$', "blockIron", 'C', "cobblestone", 'P', Blocks.piston, '#', Items.stone_pickaxe));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LibBlocks.stonegen), "CPC", "#$#", "CPC", '$', "blockIron", 'C', "stone", 'P', Blocks.piston, '#', Items.stone_pickaxe));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LibBlocks.scribe), "BWB", "FES", "BWB", 'B', Items.book, 'W', "plankWood", 'F', Items.feather, 'S', "dyeBlack", 'E', Blocks.enchanting_table));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LibItems.buckets, 1, 0), "DFD", "WBW", "DFD", 'W', Items.water_bucket, 'B', Items.bucket, 'D', "gemDiamond", 'F', "blockDiamond"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LibItems.buckets, 1, 1), "OGO", "GBG", "OGO", 'B', Items.bucket, 'G', "blockGlass", 'O', Blocks.obsidian));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LibItems.infiniApple, 1, 0), "###", "#N#", "###", '#', new ItemStack(Items.golden_apple, 1, 1), 'N', Items.nether_star));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LibItems.mantleSmasherMkI), "## ", "#PI", " II", '#', "blockEmerald", 'I', "blockIron", 'P', Items.diamond_pickaxe));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LibItems.mantleSmasherMkII), "## ", "#PI", " IN", '#', "blockDiamond", 'I', "blockGold", 'P', LibItems.mantleSmasherMkI, 'N', Items.nether_star));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LibItems.pouch), "SDS", "LCL", "LLL", 'S', Items.string, 'D', "gemDiamond", 'L', Items.leather, 'C', Blocks.chest));
	}
	
	static void initUserRecipes()
	{
		//Woodchipper Recipe
		String[] unlocItem = ConfigHandler.woodchipperRecipeItem.split(":");
		ItemStack centerItem = new ItemStack(Blocks.diamond_block);

		if (ConfigHandler.findItemStack(unlocItem[0], unlocItem[1]) != null)
		{
			ItemStack newStack = ConfigHandler.findItemStack(unlocItem[0], unlocItem[1]);
			if (unlocItem.length == 2)
				centerItem = newStack;
			if (unlocItem.length == 3)
				centerItem = new ItemStack(newStack.getItem(), 1, Integer.parseInt(unlocItem[2]));
		}
		else
		{
			LogHelper.warn(ConfigHandler.woodchipperRecipeItem + " could not be implemented into the Woodchipper's Crafting Recipe.");
			LogHelper.warn("The fallback, " + centerItem.getDisplayName() + ", has been used instead");
			LogHelper.warn("Please confirm you have the name and formatting correct.");
			System.out.println();
		}

		GameRegistry.addRecipe(new ItemStack(LibBlocks.woodchipper), new Object[] { "I#I", "SCS", "I#I", '#', Items.diamond_axe, 'I', Blocks.iron_bars, 'S', Items.golden_axe, 'C', centerItem });
		
		//Electroextractor Recipe
		unlocItem = ConfigHandler.electroextractorRecipeItem.split(":");
		centerItem = new ItemStack(Blocks.diamond_block);

		if (ConfigHandler.findItemStack(unlocItem[0], unlocItem[1]) != null)
		{
			ItemStack newStack = ConfigHandler.findItemStack(unlocItem[0], unlocItem[1]);
			if (unlocItem.length == 2)
				centerItem = newStack;
			if (unlocItem.length == 3)
				centerItem = new ItemStack(newStack.getItem(), 1, Integer.parseInt(unlocItem[2]));
		}
		else
		{
			LogHelper.warn(ConfigHandler.electroextractorRecipeItem + " could not be implemented into the Electroextractor's Crafting Recipe.");
			LogHelper.warn("The fallback, " + centerItem.getDisplayName() + ", has been used instead");
			LogHelper.warn("Please confirm you have the name and formatting correct.");
			System.out.println();
		}

		GameRegistry.addRecipe(new ItemStack(LibBlocks.electroextractor), new Object[] { "I#I", "SCS", "I#I", '#', Blocks.piston, 'I', Blocks.iron_bars, 'S', Items.golden_pickaxe, 'C', centerItem });
		
		//Cropomator Recipe
		unlocItem = ConfigHandler.cropomatorRecipeItem.split(":");
		centerItem = new ItemStack(Blocks.diamond_block);

		if (ConfigHandler.findItemStack(unlocItem[0], unlocItem[1]) != null)
		{
			ItemStack newStack = ConfigHandler.findItemStack(unlocItem[0], unlocItem[1]);
			if (unlocItem.length == 2)
				centerItem = newStack;
			if (unlocItem.length == 3)
				centerItem = new ItemStack(newStack.getItem(), 1, Integer.parseInt(unlocItem[2]));
		}
		else
		{
			LogHelper.warn(ConfigHandler.cropomatorRecipeItem + " could not be implemented into the Cropomator's Crafting Recipe.");
			LogHelper.warn("The fallback, " + centerItem.getDisplayName() + ", has been used instead");
			LogHelper.warn("Please confirm you have the name and formatting correct.");
			System.out.println();
		}

		GameRegistry.addRecipe(new ItemStack(LibBlocks.cropomator), new Object[] { "I#I", "SCS", "I#I", '#', Blocks.hay_block, 'I', Items.iron_ingot, 'S', Items.golden_hoe, 'C', centerItem });
	}
}