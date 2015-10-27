package com.oitsjustjose.criss_cross.NEI;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import com.oitsjustjose.criss_cross.Recipes.CropomatorRecipes;

import codechicken.nei.PositionedStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class CropomatorFuelHandler extends CropomatorRecipeHandler
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
			return mCropomator.get(cycleticks / 48 % mCropomator.size()).ingred;
		}
		
		@Override
		public PositionedStack getResult()
		{
			return mCropomator.get(cycleticks / 48 % mCropomator.size()).result;
		}
		
		@Override
		public PositionedStack getOtherStack()
		{
			return fuel.stack;
		}
	}
	
	private ArrayList<CropomatorPair> mCropomator = new ArrayList<CropomatorRecipeHandler.CropomatorPair>();
	
	public CropomatorFuelHandler()
	{
		super();
		loadAllRecipes();
	}
	
	public String getRecipeName()
	{
		return StatCollector.translateToLocal("recipe.cropomatorfuel");
	}
	
	private void loadAllRecipes()
	{
		Map<ItemStack, ItemStack> recipes = CropomatorRecipes.getInstance().getRecipeList();
		
		for(Entry<ItemStack, ItemStack> recipe : recipes.entrySet())
			this.mCropomator.add(new CropomatorPair(recipe.getKey(), recipe.getValue()));
	}
	
	@Override
	public void loadCraftingRecipes(String outputId, Object... results)
	{
		if(outputId.equals("cropomatorfuel"))
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
		return "cropomatorfuel";
	}
}