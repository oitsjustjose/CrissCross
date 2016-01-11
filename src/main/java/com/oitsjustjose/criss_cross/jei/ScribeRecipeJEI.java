package com.oitsjustjose.criss_cross.jei;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ScribeRecipeJEI extends BlankRecipeWrapper
{
	@Nonnull
	private final List<List<ItemStack>> inputs;
	@Nonnull
	private final List<ItemStack> outputs;

	public ScribeRecipeJEI(@Nonnull List<ItemStack> input, @Nonnull ItemStack output)
	{
		this.inputs = Collections.singletonList(input);
		this.outputs = Collections.singletonList(output);
	}

	@Override
	public List getInputs()
	{
		return inputs;
	}

	@Override
	public List getOutputs()
	{
		return outputs;
	}

	@Override
	public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight)
	{

	}
}