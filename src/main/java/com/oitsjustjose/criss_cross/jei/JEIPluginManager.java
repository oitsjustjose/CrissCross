package com.oitsjustjose.criss_cross.jei;

import mezz.jei.api.IItemRegistry;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.JEIPlugin;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class JEIPluginManager implements IModPlugin
{

    public static IJeiHelpers jeiHelper;

    @Override
    public boolean isModLoaded()
    {
        return true;
    }

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