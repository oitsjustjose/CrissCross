package com.oitsjustjose.criss_cross.jei;

import java.util.Collections;
import java.util.List;

import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class WoodchipperRecipeJEI extends BlankRecipeWrapper
{
	private final ItemStack input;
	private final ItemStack output;

	public WoodchipperRecipeJEI(ItemStack input, ItemStack output)
	{
		this.input = input;
		this.output = output;
	}

	@Override
	public List getInputs()
	{
		return Collections.singletonList(input);
	}

	@Override
	public List getOutputs()
	{
		return Collections.singletonList(output);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight)
	{
	}
}