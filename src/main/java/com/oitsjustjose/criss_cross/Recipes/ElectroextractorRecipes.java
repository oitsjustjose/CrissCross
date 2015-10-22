package com.oitsjustjose.criss_cross.Recipes;

import java.util.ArrayList;

import com.oitsjustjose.criss_cross.CrissCross;
import com.oitsjustjose.criss_cross.Items.CCItems;
import com.oitsjustjose.criss_cross.Items.ItemDust;
import com.oitsjustjose.criss_cross.TileEntity.TileEntityElectroextractor;
import com.oitsjustjose.criss_cross.Util.CCLog;
import com.oitsjustjose.criss_cross.Util.ConfigHandler;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ElectroextractorRecipes
{
	public static void initRecipes()
	{
		oreDictionaryInit();
		TileEntityElectroextractor.addRecipe(Blocks.diamond_ore, new ItemStack(Items.diamond));
		TileEntityElectroextractor.addRecipe(Blocks.emerald_ore, new ItemStack(Items.emerald));
		TileEntityElectroextractor.addRecipe(Blocks.coal_ore, new ItemStack(Items.coal));
	}
	
	public static void oreDictionaryInit()
	{		
		ArrayList<String> dustNames = ItemDust.getDusts();
		for(int i = 0; i < ConfigHandler.electroextractorOreDictInputs.length; i++)
		{
			String[] entry = ConfigHandler.electroextractorOreDictInputs[i].split(":");
			if(OreDictionary.doesOreNameExist("ore" + entry[0]) && OreDictionary.doesOreNameExist("ingot" + entry[0]))
			{
				ArrayList<ItemStack> ores = OreDictionary.getOres("ore" + entry[0]);
				ItemDust.addDustType(entry[0], Integer.parseInt(entry[1]));
				for(int j = 0; j < ores.size(); j++)
					TileEntityElectroextractor.addRecipe(ores.get(j), new ItemStack(CCItems.dusts, 1, i));
			}
			else
			{
				CCLog.warn("Your oreDictionary Entry " + ConfigHandler.electroextractorOreDictInputs[i] + " could not be registered.");
				if(!OreDictionary.doesOreNameExist("ore" + entry[0]))
					CCLog.warn("This is because your oreDictionary Entry was not valid or registered as " + entry[0] + ".");
				else if(!OreDictionary.doesOreNameExist("ingot" + entry[0]))
					CCLog.warn("This is because your oreDictionary Entry does not have an ingot for " + entry[0] + ".");
			}
		}
		
		for(int i = 0; i < dustNames.size(); i++)
		{
			ItemStack ingot = null;
			if(OreDictionary.doesOreNameExist("ingot" + dustNames.get(i)))
				ingot = OreDictionary.getOres("ingot" + dustNames.get(i)).get(0);
			if(ingot != null)
				GameRegistry.addSmelting(new ItemStack(CCItems.dusts, 1, i), ingot, 0.0F);
			OreDictionary.registerOre("dust" + dustNames.get(i), new ItemStack(CCItems.dusts, 1, i));
		}
	}
	
	public static void initFuels()
	{
		for(int i = 0; i < ConfigHandler.electroextractorEnergySources.length; i++)
		{
			try
			{
				String[] unlocStack = ConfigHandler.electroextractorEnergySources[i].split(":");
				if(unlocStack.length == 2)
				{
					if(GameRegistry.findItemStack(unlocStack[0], unlocStack[1], 1) != null)
						TileEntityElectroextractor.addFuel(GameRegistry.findItemStack(unlocStack[0], unlocStack[1], 0));
					else
					{
						CCLog.warn("Item " + ConfigHandler.electroextractorEnergySources[i] + " could not be added to the Electroextractor's fuel list.");
						CCLog.warn("Please confirm you have the name and formatting correct.");
					}
				}
				
				if(unlocStack.length == 3)
				{
					if(GameRegistry.findItemStack(unlocStack[0], unlocStack[1], 1) != null)
					{
						ItemStack newStack = new ItemStack(GameRegistry.findItemStack(unlocStack[0], unlocStack[1], 1).getItem(), 1, Integer.parseInt(unlocStack[2]));
						TileEntityElectroextractor.addFuel(newStack);
					}
					else
					{
						CCLog.warn("Item " + ConfigHandler.electroextractorEnergySources[i] + " could not be added to the Electroextractor's fuel list.");
						CCLog.warn("Please confirm you have the name and formatting correct.");
					}
				}
			}
			catch(Exception e)
			{
				CCLog.warn("Error reading itemstack for electroextractor's energy sources at item: " + (i + 1));
			}
		}
	}
}