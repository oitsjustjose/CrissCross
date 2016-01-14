package com.oitsjustjose.criss_cross.jei;

import javax.annotation.Nonnull;

import com.oitsjustjose.criss_cross.lib.Lib;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class CropomatorRecipeHandler implements IRecipeHandler<CropomatorRecipeJEI>
{

	@Nonnull
	@Override
	public Class<CropomatorRecipeJEI> getRecipeClass()
	{
		return CropomatorRecipeJEI.class;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid()
	{
		return Lib.CROPOMATOR_UID;
	}

	@Nonnull
	@Override
	public IRecipeWrapper getRecipeWrapper(@Nonnull CropomatorRecipeJEI recipe)
	{
		return recipe;
	}

	@Override
	public boolean isRecipeValid(@Nonnull CropomatorRecipeJEI recipe)
	{
		return recipe.getInputs().size() > 0 && recipe.getOutputs().size() > 0;
	}
}