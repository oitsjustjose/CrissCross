package com.oitsjustjose.criss_cross.NEI;

import com.oitsjustjose.criss_cross.GUI.GUICropomator;
import com.oitsjustjose.criss_cross.GUI.GUIElectroextractor;
import com.oitsjustjose.criss_cross.GUI.GUIWoodchipper;
import com.oitsjustjose.criss_cross.Util.Reference;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class NEICrissCrossConfig implements IConfigureNEI
{
	@Override
	public void loadConfig()
	{
		registerHandler(new CropomatorFuelHandler());
		registerHandler(new CropomatorRecipeHandler());
		registerHandler(new ElectroextractorFuelHandler());
		registerHandler(new ElectroextractorRecipeHandler());
		registerHandler(new WoodchipperFuelHandler());
		registerHandler(new WoodchipperRecipeHandler());
		API.registerGuiOverlay(GUICropomator.class, "cropomator");
		API.registerGuiOverlay(GUIElectroextractor.class, "electroextractor");
		API.registerGuiOverlay(GUIWoodchipper.class, "woodchipper");
	}
	
	@Override
	public String getName()
	{
		return Reference.modid;
	}
	
	@Override
	public String getVersion()
	{
		return Reference.version;
	}
	
	private static void registerHandler(TemplateRecipeHandler handler)
	{
		API.registerRecipeHandler(handler);
		API.registerUsageHandler(handler);
	}
}