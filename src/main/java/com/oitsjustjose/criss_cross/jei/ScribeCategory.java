package com.oitsjustjose.criss_cross.jei;

import java.util.List;

import javax.annotation.Nonnull;

import com.oitsjustjose.criss_cross.gui.GUIElectroextractor;
import com.oitsjustjose.criss_cross.gui.GUIScribe;
import com.oitsjustjose.criss_cross.lib.Config;
import com.oitsjustjose.criss_cross.lib.Lib;

import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ScribeCategory implements IRecipeCategory
{
	private static final int INPUT_SLOT = 0;
	private static final int OUTPUT_SLOT = 1;
	private static final int FUEL_SLOT = 2;

	IDrawableStatic progressDrawable = JEIPluginManager.jeiHelper.getGuiHelper().createDrawable(GUIScribe.backgroundTexture, 176, 15, 24, 16);

	@Nonnull
	protected final IDrawableAnimated progress = JEIPluginManager.jeiHelper.getGuiHelper().createAnimatedDrawable(progressDrawable, 700, IDrawableAnimated.StartDirection.LEFT, false);
	@Nonnull
	private final IDrawable background = JEIPluginManager.jeiHelper.getGuiHelper().createDrawable(GUIScribe.backgroundTexture, 33, 30, 110, 34);
	@Nonnull
	private final String localizedName = StatCollector.translateToLocal("recipe.scribe");

	@Nonnull
	@Override
	public String getUid()
	{
		return Lib.SCRIBE_UID;
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
		progress.draw(minecraft, 56, 7);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper)
	{

		recipeLayout.getItemStacks().init(INPUT_SLOT, true, 34, 4);
		recipeLayout.getItemStacks().init(OUTPUT_SLOT, false, 88, 4);
		recipeLayout.getItemStacks().init(FUEL_SLOT, true, 0, 4);

		if (recipeWrapper instanceof ScribeRecipeJEI)
		{
			ScribeRecipeJEI scribeRecipeWrapper = (ScribeRecipeJEI) recipeWrapper;
			recipeLayout.getItemStacks().set(INPUT_SLOT, scribeRecipeWrapper.getInputs());
			recipeLayout.getItemStacks().set(OUTPUT_SLOT, scribeRecipeWrapper.getOutputs());
			recipeLayout.getItemStacks().set(FUEL_SLOT, new ItemStack(Items.writable_book));
		}
	}
}
