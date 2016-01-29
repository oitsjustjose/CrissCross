package com.oitsjustjose.criss_cross.jei;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.oitsjustjose.criss_cross.recipes.machine.CropomatorRecipes;

import net.minecraft.item.ItemStack;

public class CropomatorRecipeMaker
{
	public static List<CropomatorRecipeJEI> getRecipes()
	{
		Map<ItemStack, ItemStack> cropomatorMap = CropomatorRecipes.getInstance().getRecipeList();

		ArrayList<CropomatorRecipeJEI> recipes = new ArrayList<CropomatorRecipeJEI>();

		for (Map.Entry<ItemStack, ItemStack> entry : cropomatorMap.entrySet())
		{
			ItemStack input = entry.getKey();
			ItemStack output = entry.getValue();

			CropomatorRecipeJEI recipe = new CropomatorRecipeJEI(input, output);
			recipes.add(recipe);
		}

		return recipes;
	}
}