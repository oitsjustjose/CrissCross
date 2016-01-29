package com.oitsjustjose.criss_cross.jei;

import com.oitsjustjose.criss_cross.lib.Lib;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class ElectroextractorRecipeHandler implements IRecipeHandler<ElectroextractorRecipeJEI>
{
	@Override
	public Class<ElectroextractorRecipeJEI> getRecipeClass()
	{
		return ElectroextractorRecipeJEI.class;
	}

	@Override
	public String getRecipeCategoryUid()
	{
		return Lib.ELECTROEXTRACTOR_UID;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(ElectroextractorRecipeJEI recipe)
	{
		return recipe;
	}

	@Override
	public boolean isRecipeValid(ElectroextractorRecipeJEI recipe)
	{
		return recipe.getInputs().size() > 0 && recipe.getOutputs().size() > 0;
	}
}