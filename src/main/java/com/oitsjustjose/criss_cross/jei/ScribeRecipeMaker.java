package com.oitsjustjose.criss_cross.jei;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import com.oitsjustjose.criss_cross.recipes.machine.ScribeRecipes;

import net.minecraft.item.ItemStack;

public class ScribeRecipeMaker
{
	@Nonnull
	public static List<ScribeRecipeJEI> getRecipes()
	{
		Map<ItemStack, ItemStack> scribeMap = ScribeRecipes.getInstance().getRecipeList();

		ArrayList<ScribeRecipeJEI> recipes = new ArrayList<ScribeRecipeJEI>();

		for (Map.Entry<ItemStack, ItemStack> entry : scribeMap.entrySet())
		{
			ItemStack input = entry.getKey();
			ItemStack output = entry.getValue();

			ScribeRecipeJEI recipe = new ScribeRecipeJEI(input, output);
			recipes.add(recipe);
		}

		return recipes;
	}
}