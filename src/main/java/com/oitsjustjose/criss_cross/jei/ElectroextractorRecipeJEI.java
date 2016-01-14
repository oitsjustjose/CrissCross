package com.oitsjustjose.criss_cross.jei;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ElectroextractorRecipeJEI extends BlankRecipeWrapper
{

	@Nonnull
	private final ItemStack input;

	@Nonnull
	private final ItemStack output;

	public ElectroextractorRecipeJEI(@Nonnull ItemStack input, @Nonnull ItemStack output)
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
	public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight)
	{

	}
}