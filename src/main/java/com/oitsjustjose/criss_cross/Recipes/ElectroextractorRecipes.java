package com.oitsjustjose.criss_cross.Recipes;

import com.oitsjustjose.criss_cross.Items.CCItems;
import com.oitsjustjose.criss_cross.TileEntity.TileEntityElectroextractor;
import com.oitsjustjose.criss_cross.Util.ConfigHandler;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ElectroextractorRecipes
{
	public static void initRecipes()
	{
		TileEntityElectroextractor.addBlockRecipe(Blocks.diamond_ore, new ItemStack(Items.diamond));
		TileEntityElectroextractor.addBlockRecipe(Blocks.emerald_ore, new ItemStack(Items.emerald));
		TileEntityElectroextractor.addBlockRecipe(Blocks.coal_ore, new ItemStack(Items.coal));
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
						TileEntityElectroextractor.addItemStackToFuels(GameRegistry.findItemStack(unlocStack[0], unlocStack[1], 0));
					else
						System.out.println("[CrissCross] Item " + ConfigHandler.electroextractorEnergySources[i] + " could not be added to the Electroextractor's fuel list. Please confirm you have the name and formatting correct.");
				}
				
				if(unlocStack.length == 3)
				{
					if(GameRegistry.findItemStack(unlocStack[0], unlocStack[1], 1) != null)
					{
						ItemStack newStack = new ItemStack(GameRegistry.findItemStack(unlocStack[0], unlocStack[1], 1).getItem(), 1, Integer.parseInt(unlocStack[2]));
						TileEntityElectroextractor.addItemStackToFuels(newStack);
					}
					else
						System.out.println("[CrissCross] Item " + ConfigHandler.electroextractorEnergySources[i] + " could not be added to the Electroextractor's fuel list. Please confirm you have the name and formatting correct.");
				}
			}
			catch(Exception e)
			{
				System.out.println("[CrissCross]: Error reading itemstack for electroextractor's energy sources at item: " + (i + 1));
			}
		}
	}
}