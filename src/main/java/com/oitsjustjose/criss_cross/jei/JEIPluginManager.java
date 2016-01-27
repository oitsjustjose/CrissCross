package com.oitsjustjose.criss_cross.jei;

import com.oitsjustjose.criss_cross.gui.GUICropomator;
import com.oitsjustjose.criss_cross.gui.GUIElectroextractor;
import com.oitsjustjose.criss_cross.gui.GUIScribe;
import com.oitsjustjose.criss_cross.gui.GUIWoodchipper;
import com.oitsjustjose.criss_cross.lib.Lib;

import mezz.jei.api.IItemRegistry;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.JEIPlugin;

@JEIPlugin
public class JEIPluginManager implements IModPlugin
{

	public static IJeiHelpers jeiHelper;

	@Override
	public void onJeiHelpersAvailable(IJeiHelpers jeiHelpers)
	{
		jeiHelper = jeiHelpers;
	}

	@Override
	public void register(IModRegistry registry)
	{
		registry.addRecipeCategories(new CropomatorCategory());
		registry.addRecipeHandlers(new CropomatorRecipeHandler());
		registry.addRecipes(CropomatorRecipeMaker.getRecipes());

		registry.addRecipeCategories(new ElectroextractorCategory());
		registry.addRecipeHandlers(new ElectroextractorRecipeHandler());
		registry.addRecipes(ElectroextractorRecipeMaker.getRecipes());

		registry.addRecipeCategories(new WoodchipperCategory());
		registry.addRecipeHandlers(new WoodchipperRecipeHandler());
		registry.addRecipes(WoodchipperRecipeMaker.getRecipes());

		registry.addRecipeCategories(new ScribeCategory());
		registry.addRecipeHandlers(new ScribeRecipeHandler());
		registry.addRecipes(ScribeRecipeMaker.getRecipes());

		registry.addRecipeClickArea(GUICropomator.class, 78, 32, 28, 23, Lib.CROPOMATOR_UID);
		registry.addRecipeClickArea(GUIElectroextractor.class, 78, 32, 28, 23, Lib.ELECTROEXTRACTOR_UID);
		registry.addRecipeClickArea(GUIWoodchipper.class, 78, 32, 28, 23, Lib.WOODCHIPPER_UID);
		registry.addRecipeClickArea(GUIScribe.class, 88, 32, 28, 23, Lib.SCRIBE_UID);
	}

	@Override
	public void onItemRegistryAvailable(IItemRegistry itemRegistry)
	{

	}

	@Override
	public void onRecipeRegistryAvailable(IRecipeRegistry recipeRegistry)
	{

	}
}