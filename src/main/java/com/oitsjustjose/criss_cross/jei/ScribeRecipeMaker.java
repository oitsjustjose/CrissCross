package com.oitsjustjose.criss_cross.jei;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import com.oitsjustjose.criss_cross.recipes.machine.ScribeRecipes;

import mezz.jei.util.StackUtil;
import net.minecraft.item.ItemStack;

public class ScribeRecipeMaker
{
	@Nonnull
	public static List<ScribeRecipeJEI> getRecipes()
	{
		ScribeRecipes scribeRecipes = ScribeRecipes.getInstance();
		Map<ItemStack, ItemStack> scribeMap = getMap(scribeRecipes);

		List<ScribeRecipeJEI> recipes = new ArrayList<>();

		for (Map.Entry<ItemStack, ItemStack> entry : scribeMap.entrySet())
		{
			ItemStack input = entry.getKey();
			ItemStack output = entry.getValue();

			List<ItemStack> inputs = StackUtil.getSubtypes(input);
			ScribeRecipeJEI recipe = new ScribeRecipeJEI(inputs, output);
			recipes.add(recipe);
		}

		return recipes;
	}

	@SuppressWarnings("unchecked")
	private static Map<ItemStack, ItemStack> getMap(@Nonnull ScribeRecipes recipes)
	{
		return recipes.getRecipeList();
	}
}