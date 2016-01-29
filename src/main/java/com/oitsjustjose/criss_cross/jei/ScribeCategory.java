package com.oitsjustjose.criss_cross.jei;

import com.oitsjustjose.criss_cross.gui.GUIScribe;
import com.oitsjustjose.criss_cross.lib.Lib;

import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
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

	private final IDrawableStatic progressDrawable;

	private final IDrawable background;
	private final String localizedName;

	public ScribeCategory()
	{
		super();
		progressDrawable = JEIPluginManager.jeiHelper.getGuiHelper().createDrawable(GUIScribe.texture, 177, 14, 23, 16);
		background = JEIPluginManager.jeiHelper.getGuiHelper().createDrawable(GUIScribe.texture, 39, 16, 98, 50);
		localizedName = StatCollector.translateToLocal("recipe.scribe");
	}

	@Override
	public String getUid()
	{
		return Lib.SCRIBE_UID;
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
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper)
	{
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(INPUT_SLOT, true, 80, 30);
		guiItemStacks.init(OUTPUT_SLOT, false, 40, 8);
		guiItemStacks.init(FUEL_SLOT, true, 0, 30);

		if (recipeWrapper instanceof ScribeRecipeJEI)
		{
			ScribeRecipeJEI scribeRecipeWrapper = (ScribeRecipeJEI) recipeWrapper;
			guiItemStacks.setFromRecipe(INPUT_SLOT, scribeRecipeWrapper.getInputs());
			guiItemStacks.setFromRecipe(OUTPUT_SLOT, scribeRecipeWrapper.getOutputs());
			recipeLayout.getItemStacks().set(FUEL_SLOT, new ItemStack(Items.writable_book));
		}
	}
}
