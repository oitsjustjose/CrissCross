package com.oitsjustjose.criss_cross.jei;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.oitsjustjose.criss_cross.recipes.machine.ScribeRecipes;

import net.minecraft.item.ItemStack;

public class ScribeRecipeMaker
{
	public static List<ScribeRecipeJEI> getRecipes()
	{
		ScribeRecipes scribeRecipes = ScribeRecipes.getInstance();
		Map<ItemStack, ItemStack> scribeMap = getMap(scribeRecipes);

		List<ScribeRecipeJEI> recipes = new ArrayList<ScribeRecipeJEI>();

		for (Map.Entry<ItemStack, ItemStack> entry : scribeMap.entrySet())
		{
			ItemStack input = entry.getKey();
			ItemStack output = entry.getValue();

			List<ItemStack> inputs = JEIPluginManager.jeiHelper.getStackHelper().getSubtypes(input);
			ScribeRecipeJEI recipe = new ScribeRecipeJEI(inputs, output);
			recipes.add(recipe);
		}

		return recipes;
	}

	private static Map<ItemStack, ItemStack> getMap(ScribeRecipes recipes)
	{
		return recipes.getRecipeList();
	}
}