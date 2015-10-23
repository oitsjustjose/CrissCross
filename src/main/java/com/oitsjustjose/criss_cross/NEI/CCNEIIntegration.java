package com.oitsjustjose.criss_cross.NEI;

import com.oitsjustjose.criss_cross.Util.Reference;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class CCNEIIntegration implements IConfigureNEI
{
	@Override
	public void loadConfig()
	{
		registerHandler(new RecipeHandlerWoodchipper());
	}

	@Override
	public String getName()
	{
		return Reference.modid + " built-in NEI plugin.";
	}

	@Override
	public String getVersion()
	{
		return Reference.version;
	}

	public void registerHandler(RecipeHandlerBase handler)
	{
		API.registerRecipeHandler(handler);
		API.registerUsageHandler(handler);
	}
}