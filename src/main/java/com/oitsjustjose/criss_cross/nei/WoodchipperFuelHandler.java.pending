package com.oitsjustjose.criss_cross.NEI;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import com.oitsjustjose.criss_cross.Recipes.WoodchipperRecipes;

import codechicken.nei.PositionedStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class WoodchipperFuelHandler extends WoodchipperRecipeHandler
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
			return mWoodchipper.get(cycleticks / 48 % mWoodchipper.size()).ingred;
		}
		
		@Override
		public PositionedStack getResult()
		{
			return mWoodchipper.get(cycleticks / 48 % mWoodchipper.size()).result;
		}
		
		@Override
		public PositionedStack getOtherStack()
		{
			return fuel.stack;
		}
	}
	
	private ArrayList<WoodchipperPair> mWoodchipper = new ArrayList<WoodchipperRecipeHandler.WoodchipperPair>();
	
	public WoodchipperFuelHandler()
	{
		super();
		loadAllRecipes();
	}
	
	public String getRecipeName()
	{
		return StatCollector.translateToLocal("recipe.woodchipperfuel");
	}
	
	private void loadAllRecipes()
	{
		Map<ItemStack, ItemStack> recipes = WoodchipperRecipes.getInstance().getRecipeList();
		
		for(Entry<ItemStack, ItemStack> recipe : recipes.entrySet())
			this.mWoodchipper.add(new WoodchipperPair(recipe.getKey(), recipe.getValue()));
	}
	
	@Override
	public void loadCraftingRecipes(String outputId, Object... results)
	{
		if(outputId.equals("woodchipperfuel"))
			for(FuelPair fuel : afuels)
				arecipes.add(new CachedFuelRecipe(fuel));
	}
	
	public void loadUsageRecipes(ItemStack ingredient)
	{
		for(FuelPair fuel : afuels)
			if(fuel.stack.contains(ingredient))
				arecipes.add(new CachedFuelRecipe(fuel));
	}
	
	public String getOverlayIdentifier()
	{
		return "woodchipperfuel";
	}
}