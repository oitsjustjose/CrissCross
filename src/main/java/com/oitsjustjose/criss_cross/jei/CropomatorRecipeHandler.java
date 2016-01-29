package com.oitsjustjose.criss_cross.jei;

import com.oitsjustjose.criss_cross.lib.Lib;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class CropomatorRecipeHandler implements IRecipeHandler<CropomatorRecipeJEI>
{
	@Override
	public Class<CropomatorRecipeJEI> getRecipeClass()
	{
		return CropomatorRecipeJEI.class;
	}

	@Override
	public String getRecipeCategoryUid()
	{
		return Lib.CROPOMATOR_UID;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(CropomatorRecipeJEI recipe)
	{
		return recipe;
	}

	@Override
	public boolean isRecipeValid(CropomatorRecipeJEI recipe)
	{
		return recipe.getInputs().size() > 0 && recipe.getOutputs().size() > 0;
	}
}