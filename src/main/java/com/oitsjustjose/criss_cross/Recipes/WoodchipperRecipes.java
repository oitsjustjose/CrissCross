package com.oitsjustjose.criss_cross.Recipes;

import java.util.ArrayList;

import com.oitsjustjose.criss_cross.TileEntity.TileEntityWoodchipper;
import com.oitsjustjose.criss_cross.Util.ConfigHandler;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class WoodchipperRecipes
{
	public static void initRecipes()
	{
		ArrayList<ItemStack> logs = OreDictionary.getOres("logWood");
		ArrayList<ItemStack> planks = OreDictionary.getOres("plankWood");
		
		for(int i = 0; i < logs.size(); i++)
		{
			for(int j = 0; j < planks.size(); j++)
			{
				ItemStack tempLog = logs.get(i);
				ItemStack tempPlank = planks.get(j);
				
				String logName = tempLog.getDisplayName().toLowerCase();
				String plankName = tempPlank.getDisplayName().toLowerCase();
				
				logName.replaceFirst("wood", "");
				logName.trim();
				
				if(plankName.contains(logName))
					TileEntityWoodchipper.addRecipe(tempLog, tempPlank);
			}
		}
	}
	
	public static void initFuels()
	{
		for(int i = 0; i < ConfigHandler.woodchipperFuels.length; i++)
		{
			try
			{
				String[] unlocStack = ConfigHandler.woodchipperFuels[i].split(":");
				if(unlocStack.length == 2)
				{
					if(GameRegistry.findItemStack(unlocStack[0], unlocStack[1], 1) != null)
						TileEntityWoodchipper.addItemStackToFuels(GameRegistry.findItemStack(unlocStack[0], unlocStack[1], 0));
					else
						System.out.println("[CrissCross] Item " + ConfigHandler.woodchipperFuels[i] + " could not be added to the Woodchipper's fuel list. Please confirm you have the name and formatting correct.");
				}
				
				if(unlocStack.length == 3)
				{
					if(GameRegistry.findItemStack(unlocStack[0], unlocStack[1], 1) != null)
					{
						ItemStack newStack = new ItemStack(GameRegistry.findItemStack(unlocStack[0], unlocStack[1], 1).getItem(), 1, Integer.parseInt(unlocStack[2]));
						TileEntityWoodchipper.addItemStackToFuels(newStack);
					}
					else
						System.out.println("[CrissCross] Item " + ConfigHandler.woodchipperFuels[i] + " could not be added to the Woodchipper's fuel list. Please confirm you have the name and formatting correct.");
				}
			}
			catch(Exception e)
			{
				System.out.println("[CrissCross]: Error reading itemstack for Woodchipper's energy sources at item: " + (i + 1));
			}
		}
	}
}
