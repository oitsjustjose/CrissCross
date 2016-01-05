package com.oitsjustjose.criss_cross.jei;

import javax.annotation.Nonnull;

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
	
	IDrawableStatic fuelDrawable = JEIPluginManager.jeiHelper.getGuiHelper().createDrawable(GUICropomator.backgroundTexture, 176, 0, 14, 14);
	IDrawableStatic progressDrawable = JEIPluginManager.jeiHelper.getGuiHelper().createDrawable(GUICropomator.backgroundTexture, 177, 14, 23, 16);

	@Nonnull
	private final IDrawable background = JEIPluginManager.jeiHelper.getGuiHelper().createDrawable(GUICropomator.backgroundTexture, 55, 16, 82, 54);
	@Nonnull
	protected final IDrawableAnimated fuel = JEIPluginManager.jeiHelper.getGuiHelper().createAnimatedDrawable(fuelDrawable, Config.cropomatorProcessTime, IDrawableAnimated.StartDirection.TOP, true);
	@Nonnull
	protected final IDrawableAnimated progress = JEIPluginManager.jeiHelper.getGuiHelper().createAnimatedDrawable(progressDrawable, Config.cropomatorProcessTime, IDrawableAnimated.StartDirection.LEFT, false);
	@Nonnull
	private final String localizedName = StatCollector.translateToLocal("recipe.cropomator");
	
	@Nonnull
	@Override
	public String getUid()
	{
		return Lib.CROPOMATOR_UID;
	}

	@Nonnull
	@Override
	public String getTitle()
	{
		return localizedName;
	}

	@Nonnull
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
		fuel.draw(minecraft, 1, 20);
		progress.draw(minecraft, 25, 18);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper)
	{

		recipeLayout.getItemStacks().init(INPUT_SLOT, true, 0, 0);
		recipeLayout.getItemStacks().init(OUTPUT_SLOT, false, 60, 18);
		recipeLayout.getItemStacks().init(FUEL_SLOT, true, 0, 36);

		if (recipeWrapper instanceof CropomatorRecipeJEI)
		{
			CropomatorRecipeJEI cropomatorRecipeWrapper = (CropomatorRecipeJEI) recipeWrapper;
			recipeLayout.getItemStacks().set(INPUT_SLOT, cropomatorRecipeWrapper.getInputs());
			recipeLayout.getItemStacks().set(OUTPUT_SLOT, cropomatorRecipeWrapper.getOutputs());
			recipeLayout.getItemStacks().set(FUEL_SLOT, TileCropomator.getFuels());
		}
	}
}
