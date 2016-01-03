package com.oitsjustjose.criss_cross.jei;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import com.oitsjustjose.criss_cross.recipes.machine.ElectroextractorRecipes;

import net.minecraft.item.ItemStack;

public class ElectroextractorRecipeMaker
{
	@Nonnull
	public static List<ElectroextractorRecipeJEI> getRecipes()
	{
		Map<ItemStack, ItemStack> electroextractorMap = ElectroextractorRecipes.getInstance().getRecipeList();

		ArrayList<ElectroextractorRecipeJEI> recipes = new ArrayList<ElectroextractorRecipeJEI>();

		for (Map.Entry<ItemStack, ItemStack> entry : electroextractorMap.entrySet())
		{
			ItemStack input = entry.getKey();
			ItemStack output = entry.getValue();

			ElectroextractorRecipeJEI recipe = new ElectroextractorRecipeJEI(input, output);
			recipes.add(recipe);
		}

		return recipes;
	}
}