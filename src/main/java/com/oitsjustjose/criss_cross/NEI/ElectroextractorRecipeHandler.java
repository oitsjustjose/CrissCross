package com.oitsjustjose.criss_cross.NEI;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.oitsjustjose.criss_cross.GUI.GUIElectroextractor;
import com.oitsjustjose.criss_cross.Recipes.ElectroextractorRecipes;
import com.oitsjustjose.criss_cross.TileEntity.TileEntityElectroextractor;
import com.oitsjustjose.criss_cross.Util.ConfigHandler;

import codechicken.nei.ItemList;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ElectroextractorRecipeHandler extends TemplateRecipeHandler
{
	public class ElectroextractorPair extends CachedRecipe
	{
		public ElectroextractorPair(ItemStack ingred, ItemStack result)
		{
			ingred.stackSize = 1;
			this.ingred = new PositionedStack(ingred, 51, 6);
			this.result = new PositionedStack(result, 111, 24);
		}

		public List<PositionedStack> getIngredients()
		{
			return getCycledIngredients(cycleticks / 48, Arrays.asList(ingred));
		}

		public PositionedStack getResult()
		{
			return result;
		}

		public PositionedStack getOtherStack()
		{
			return afuels.get((cycleticks / 48) % afuels.size()).stack;
		}

		PositionedStack ingred;
		PositionedStack result;
	}

	public static class FuelPair
	{
		public FuelPair(ItemStack ingred)
		{
			this.stack = new PositionedStack(ingred, 51, 42, false);
			this.burnTime = ConfigHandler.electroextractorProcessTime;
		}

		public PositionedStack stack;
		public int burnTime;
	}

	public static ArrayList<FuelPair> afuels;
	public static HashSet<Block> efuels;

	@Override
	public void loadTransferRects()
	{
		transferRects.add(new RecipeTransferRect(new Rectangle(50, 23, 18, 18), "Electroextractorfuel"));
		transferRects.add(new RecipeTransferRect(new Rectangle(74, 23, 24, 18), "Electroextractor"));
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass()
	{
		return GUIElectroextractor.class;
	}

	@Override
	public String getRecipeName()
	{
		return StatCollector.translateToLocal("recipe.electroextractor");
	}

	@Override
	public TemplateRecipeHandler newInstance()
	{
		if (afuels == null || afuels.isEmpty())
			findFuels();
		return super.newInstance();
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results)
	{
		if (outputId.equals("Electroextractor") && getClass() == ElectroextractorRecipeHandler.class)
		{
			Map<ItemStack, ItemStack> recipes = (Map<ItemStack, ItemStack>) ElectroextractorRecipes.getInstance()
					.getRecipeList();
			for (Entry<ItemStack, ItemStack> recipe : recipes.entrySet())
				arecipes.add(new ElectroextractorPair(recipe.getKey(), recipe.getValue()));
		}
		else
			super.loadCraftingRecipes(outputId, results);
	}

	@Override
	public void loadCraftingRecipes(ItemStack result)
	{
		Map<ItemStack, ItemStack> recipes = (Map<ItemStack, ItemStack>) ElectroextractorRecipes.getInstance()
				.getRecipeList();
		for (Entry<ItemStack, ItemStack> recipe : recipes.entrySet())
			if (NEIServerUtils.areStacksSameType(recipe.getValue(), result))
				arecipes.add(new ElectroextractorPair(recipe.getKey(), recipe.getValue()));
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients)
	{
		if (inputId.equals("Electroextractorfuel") && getClass() == ElectroextractorRecipeHandler.class)
			loadCraftingRecipes("Electroextractor");
		else
			super.loadUsageRecipes(inputId, ingredients);
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient)
	{
		Map<ItemStack, ItemStack> recipes = (Map<ItemStack, ItemStack>) ElectroextractorRecipes.getInstance()
				.getRecipeList();
		for (Entry<ItemStack, ItemStack> recipe : recipes.entrySet())
		{
			if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getKey(), ingredient))
			{
				ElectroextractorPair arecipe = new ElectroextractorPair(recipe.getKey(), recipe.getValue());
				arecipe.setIngredientPermutation(Arrays.asList(arecipe.ingred), ingredient);
				arecipes.add(arecipe);
			}
		}
	}

	@Override
	public String getGuiTexture()
	{
		return GUIElectroextractor.texture.toString();
	}

	@Override
	public void drawExtras(int recipe)
	{
		drawProgressBar(51, 25, 176, 0, 14, 14, 48, 7);
		drawProgressBar(74, 23, 176, 14, 24, 16, 48, 0);
	}

	private static void findFuels()
	{
		afuels = new ArrayList<FuelPair>();
		for (ItemStack item : ItemList.items)
		{
			if (TileEntityElectroextractor.isItemEnergetic(item))
				afuels.add(new FuelPair(item));
		}
	}

	@Override
	public String getOverlayIdentifier()
	{
		return "electroextractor";
	}
}