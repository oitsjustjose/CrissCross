package com.oitsjustjose.criss_cross.jei;

import com.oitsjustjose.criss_cross.lib.Lib;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class WoodchipperRecipeHandler implements IRecipeHandler<WoodchipperRecipeJEI>
{
	@Override
	public Class<WoodchipperRecipeJEI> getRecipeClass()
	{
		return WoodchipperRecipeJEI.class;
	}

	@Override
	public String getRecipeCategoryUid()
	{
		return Lib.WOODCHIPPER_UID;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(WoodchipperRecipeJEI recipe)
	{
		return recipe;
	}

	@Override
	public boolean isRecipeValid(WoodchipperRecipeJEI recipe)
	{
		return recipe.getInputs().size() > 0 && recipe.getOutputs().size() > 0;
	}
}