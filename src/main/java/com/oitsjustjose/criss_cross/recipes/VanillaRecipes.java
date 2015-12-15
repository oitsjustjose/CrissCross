package com.oitsjustjose.criss_cross.recipes;

import com.oitsjustjose.criss_cross.lib.ConfigHandler;
import com.oitsjustjose.criss_cross.lib.LibBlocks;
import com.oitsjustjose.criss_cross.lib.LibItems;
import com.oitsjustjose.criss_cross.util.CCLog;
import com.oitsjustjose.criss_cross.util.ColorUtils;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class VanillaRecipes
{
	public static void init()
	{
		cropomatorRecipe();
		electroextractorRecipe();
		woodChipperRecipe();
		initOthers();
		CraftingManager.getInstance().getRecipeList().add(new PouchColorRecipes());
	}

	static void initOthers()
	{

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LibBlocks.cobblegen), "CPC", "#$#", "CPC", '$', "blockIron", 'C', "cobblestone", 'P', Blocks.piston, '#', Items.stone_pickaxe));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LibBlocks.stonegen), "CPC", "#$#", "CPC", '$', "blockIron", 'C', "stone", 'P', Blocks.piston, '#', Items.stone_pickaxe));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LibBlocks.scribe), "BWB", "FES", "BWB", 'B', Items.book, 'W', "plankWood", 'F', Items.feather, 'S', "dyeBlack", 'E', Blocks.enchanting_table));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LibItems.buckets, 1, 0), "DFD", "WBW", "DFD", 'W', Items.water_bucket, 'B', Items.bucket, 'D', "gemDiamond", 'F', "blockDiamond"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LibItems.buckets, 1, 1), "OGO", "GBG", "OGO", 'B', Items.bucket, 'G', "blockGlass", 'O', Blocks.obsidian));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LibItems.infiniApple, 1, 0), "###", "#N#", "###", '#', new ItemStack(Items.golden_apple, 1, 1), 'N', Items.nether_star));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LibItems.mantleSmasherMkI), "## ", "#PI", " II", '#', "blockEmerald", 'I', "blockIron", 'P', Items.diamond_pickaxe));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LibItems.mantleSmasherMkII), "## ", "#PI", " IN", '#', "blockDiamond", 'I', "blockGold", 'P', LibItems.mantleSmasherMkI, 'N', Items.nether_star));
		for (int i = 0; i < 16; i++)
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LibItems.pouch, 1, i), "SDS", "WCW", "BWB", 'W', new ItemStack(Blocks.wool, 1, Short.MAX_VALUE), 'S', Items.string, 'D', ColorUtils.getOreDictFromMeta(i), 'C', Blocks.chest, 'B', Items.diamond));
	}

	static void woodChipperRecipe()
	{
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
			CCLog.warn("Item " + ConfigHandler.woodchipperRecipeItem + "could not be added as part of the recipe of the Woodchipper.");
			CCLog.warn("Please confirm you have the name and formatting correct.");
		}

		GameRegistry.addRecipe(new ItemStack(LibBlocks.woodchipper), new Object[] { "I#I", "SCS", "I#I", '#', Items.diamond_axe, 'I', Blocks.iron_bars, 'S', Items.golden_axe, 'C', centerItem });
	}

	static void electroextractorRecipe()
	{
		String[] unlocItem = ConfigHandler.electroextractorRecipeItem.split(":");
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
			CCLog.warn("Item " + ConfigHandler.electroextractorRecipeItem + " could not be added as part of the recipe of the electroextractor.");
			CCLog.warn("Please confirm you have the name and formatting correct.");

		}

		GameRegistry.addRecipe(new ItemStack(LibBlocks.electroextractor), new Object[] { "I#I", "SCS", "I#I", '#', Blocks.piston, 'I', Blocks.iron_bars, 'S', Items.golden_pickaxe, 'C', centerItem });
	}

	static void cropomatorRecipe()
	{
		String[] unlocItem = ConfigHandler.cropomatorRecipeItem.split(":");
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
			CCLog.warn("Item " + ConfigHandler.cropomatorRecipeItem + " could not be added as part of the recipe of the cropomator.");
			CCLog.warn("Please confirm you have the name and formatting correct.");
		}

		GameRegistry.addRecipe(new ItemStack(LibBlocks.cropomator), new Object[] { "I#I", "SCS", "I#I", '#', Blocks.hay_block, 'I', Items.iron_ingot, 'S', Items.golden_hoe, 'C', centerItem });
	}

}