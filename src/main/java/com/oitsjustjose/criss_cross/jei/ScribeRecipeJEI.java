package com.oitsjustjose.criss_cross.jei;

import java.util.Collections;
import java.util.List;

import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ScribeRecipeJEI extends BlankRecipeWrapper
{
	private final List<List<ItemStack>> inputs;
	private final List<ItemStack> outputs;

	public ScribeRecipeJEI(List<ItemStack> input, ItemStack output)
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
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight)
	{

	}
}