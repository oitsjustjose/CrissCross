package com.oitsjustjose.criss_cross.NEI;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import com.oitsjustjose.criss_cross.Recipes.ElectroextractorRecipes;

import codechicken.nei.PositionedStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ElectroextractorFuelHandler extends ElectroextractorRecipeHandler
{
	public class CachedFuelRecipe extends CachedRecipe
	{
		public FuelPair fuel;

		public CachedFuelRecipe(FuelPair fuel)
		{
			this.fuel = fuel;
		}

		@Override
		public PositionedStack getIngredient()
		{
			return mElectroextractor.get(cycleticks / 48 % mElectroextractor.size()).ingred;
		}

		@Override
		public PositionedStack getResult()
		{
			return mElectroextractor.get(cycleticks / 48 % mElectroextractor.size()).result;
		}

		@Override
		public PositionedStack getOtherStack()
		{
			return fuel.stack;
		}
	}

	private ArrayList<ElectroextractorPair> mElectroextractor = new ArrayList<ElectroextractorRecipeHandler.ElectroextractorPair>();

	public ElectroextractorFuelHandler()
	{
		super();
		loadAllRecipes();
	}

	public String getRecipeName()
	{
		return StatCollector.translateToLocal("recipe.electroextractorfuel");
	}

	private void loadAllRecipes()
	{
		Map<ItemStack, ItemStack> recipes = ElectroextractorRecipes.getInstance().getRecipeList();
		
		for(Entry<ItemStack, ItemStack> recipe : recipes.entrySet())
			this.mElectroextractor.add(new ElectroextractorPair(recipe.getKey(), recipe.getValue()));
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results)
	{
		if (outputId.equals("Electroextractorfuel"))
			for (FuelPair fuel : afuels)
				arecipes.add(new CachedFuelRecipe(fuel));
	}

	public void loadUsageRecipes(ItemStack ingredient)
	{
		for (FuelPair fuel : afuels)
			if (fuel.stack.contains(ingredient))
				arecipes.add(new CachedFuelRecipe(fuel));
	}

	public String getOverlayIdentifier()
	{
		return "electroextractorfuel";
	}
}