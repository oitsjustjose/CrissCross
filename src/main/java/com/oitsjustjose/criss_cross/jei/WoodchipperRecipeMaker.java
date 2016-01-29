package com.oitsjustjose.criss_cross.jei;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.oitsjustjose.criss_cross.recipes.machine.WoodchipperRecipes;

import net.minecraft.item.ItemStack;

public class WoodchipperRecipeMaker
{
	public static List<WoodchipperRecipeJEI> getRecipes()
	{
		Map<ItemStack, ItemStack> woodchipperMap = WoodchipperRecipes.getInstance().getRecipeList();

		ArrayList<WoodchipperRecipeJEI> recipes = new ArrayList<WoodchipperRecipeJEI>();

		for (Map.Entry<ItemStack, ItemStack> entry : woodchipperMap.entrySet())
		{
			ItemStack input = entry.getKey();
			ItemStack output = entry.getValue();

			WoodchipperRecipeJEI recipe = new WoodchipperRecipeJEI(input, output);
			recipes.add(recipe);
		}

		return recipes;
	}
}