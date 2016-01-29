package com.oitsjustjose.criss_cross.jei;

import com.oitsjustjose.criss_cross.gui.GUICropomator;
import com.oitsjustjose.criss_cross.lib.Config;
import com.oitsjustjose.criss_cross.lib.Lib;
import com.oitsjustjose.criss_cross.tileentity.TileCropomator;

import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.StatCollector;

public class CropomatorCategory implements IRecipeCategory
{
	private static final int INPUT_SLOT = 0;
	private static final int OUTPUT_SLOT = 1;
	private static final int FUEL_SLOT = 2;

	IDrawableStatic fuelDrawable = JEIPluginManager.jeiHelper.getGuiHelper().createDrawable(GUICropomator.texture, 176, 17, 16, 46);
	IDrawableStatic progressDrawable = JEIPluginManager.jeiHelper.getGuiHelper().createDrawable(GUICropomator.texture, 177, 0, 23, 16);

	private final IDrawable background = JEIPluginManager.jeiHelper.getGuiHelper().createDrawable(GUICropomator.texture, 7, 6, 130, 67);
	protected final IDrawableAnimated fuel = JEIPluginManager.jeiHelper.getGuiHelper().createAnimatedDrawable(fuelDrawable, Config.cropomatorProcessTime, IDrawableAnimated.StartDirection.TOP, true);
	protected final IDrawableAnimated progress = JEIPluginManager.jeiHelper.getGuiHelper().createAnimatedDrawable(progressDrawable, Config.cropomatorProcessTime, IDrawableAnimated.StartDirection.LEFT, false);
	private final String localizedName = StatCollector.translateToLocal("recipe.cropomator");

	@Override
	public String getUid()
	{
		return Lib.CROPOMATOR_UID;
	}

	@Override
	public String getTitle()
	{
		return localizedName;
	}

	@Override
	public IDrawable getBackground()
	{
		return background;
	}

	@Override
	public void drawExtras(Minecraft minecraft)
	{

	}

	@Override
	public void drawAnimations(Minecraft minecraft)
	{
		fuel.draw(minecraft, 1, 0);
		progress.draw(minecraft, 73, 28);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper)
	{
		recipeLayout.getItemStacks().init(INPUT_SLOT, true, 48, 28);
		recipeLayout.getItemStacks().init(OUTPUT_SLOT, false, 108, 28);
		recipeLayout.getItemStacks().init(FUEL_SLOT, true, 0, 49);

		if (recipeWrapper instanceof CropomatorRecipeJEI)
		{
			CropomatorRecipeJEI cropomatorRecipeWrapper = (CropomatorRecipeJEI) recipeWrapper;
			recipeLayout.getItemStacks().set(INPUT_SLOT, cropomatorRecipeWrapper.getInputs());
			recipeLayout.getItemStacks().set(OUTPUT_SLOT, cropomatorRecipeWrapper.getOutputs());
			recipeLayout.getItemStacks().set(FUEL_SLOT, TileCropomator.getFuels());
		}
	}
}
