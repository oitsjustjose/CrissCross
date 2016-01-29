package com.oitsjustjose.criss_cross.jei;

import com.oitsjustjose.criss_cross.lib.Lib;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class ScribeRecipeHandler implements IRecipeHandler<ScribeRecipeJEI>
{
	@Override
	public Class<ScribeRecipeJEI> getRecipeClass()
	{
		return ScribeRecipeJEI.class;
	}

	@Override
	public String getRecipeCategoryUid()
	{
		return Lib.SCRIBE_UID;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(ScribeRecipeJEI recipe)
	{
		return recipe;
	}

	@Override
	public boolean isRecipeValid(ScribeRecipeJEI recipe)
	{
		return recipe.getInputs().size() != 0 && recipe.getOutputs().size() > 0;
	}
}