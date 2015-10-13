package com.oitsjustjose.criss_cross.Recipes;

import com.oitsjustjose.criss_cross.TileEntity.TileEntityCropomator;
import com.oitsjustjose.criss_cross.Util.ConfigHandler;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;

public class CropomatorRecipes
{
	public static void initRecipes()
	{
		//Iterates through the inputItems String array
		for(int i = 0; i < ConfigHandler.cropomatorInputs.length; i++)
		{
			//Done in a try loop just in case there's an issue with reading a value in.
			try
			{
				//Splits the modid:item / modid:item:metadata string at the ':' and makes a 
				//new Array of strings, each element containing the newly separated parts.
				String[] unlocStack = ConfigHandler.cropomatorInputs[i].split(":");
				//Checks the read in string as modid:item
				if(unlocStack.length == 2)
				{
					//Attempts to find the Itemstack with given info modid and unlocalizedname
					if(GameRegistry.findItemStack(unlocStack[0], unlocStack[1], 1) != null)
						TileEntityCropomator.addItemStackToOutputs(GameRegistry.findItemStack(unlocStack[0], unlocStack[1], 1));
					//If it fails, it prints out an error message letting the user know exactly where they made a mistake
					else
						System.out.println("[CrissCross] Item " + ConfigHandler.cropomatorInputs[i] + " could not be added to the Cropomator's recipe list. Please confirm you have the name and formatting correct.");
				}
				//Checks the read in string as modid:item:metadata
				if(unlocStack.length == 3)
				{
					//Attempts to find the Itemstack with given info modid and unlocalizedname
					if(GameRegistry.findItemStack(unlocStack[0], unlocStack[1], 1) != null)
					{
						//It then creates a new itemstack with the itemstack's getItem, qty 1, with a parsed int value of the 3rd argument
						ItemStack newStack = new ItemStack(GameRegistry.findItemStack(unlocStack[0], unlocStack[1], 1).getItem(), 1, Integer.parseInt(unlocStack[2]));
						TileEntityCropomator.addItemStackToOutputs(newStack);
					}
					//If it fails, it prints out an error message letting the user know exactly where they made a mistake
					else
						System.out.println("[CrissCross] Item " + ConfigHandler.cropomatorInputs[i] + " could not be added to the Cropomator's recipe list. Please confirm you have the name and formatting correct.");
				}

			}
			//Gotta catch em all
			catch(Exception e)
			{
				System.out.println("[CrissCross]: Error reading itemstack for inputs from input file at item: " + (i + 1));
			}
			
		}
	}
	
	//Iterates through the catalystItems String array
	public static void initCatalysts()
	{
		//Done in a try loop just in case there's an issue with reading a value in.
		for(int i = 0; i < ConfigHandler.cropomatorCatalysts.length; i++)
		{
			try
			{
				//Splits the modid:item / modid:item:metadata string at the ':' and makes a 
				//new Array of strings, each element containing the newly separated parts.
				String[] unlocStack = ConfigHandler.cropomatorCatalysts[i].split(":");
				//Checks the read in string as modid:item
				if(unlocStack.length == 2)
				{
					//Attempts to find the Itemstack with given info modid and unlocalizedname
					if(GameRegistry.findItemStack(unlocStack[0], unlocStack[1], 1) != null)
						TileEntityCropomator.addItemStackToCatalysts(GameRegistry.findItemStack(unlocStack[0], unlocStack[1], 1));
					//If it fails, it prints out an error message letting the user know exactly where they made a mistake
					else
						System.out.println("[CrissCross] Item " + ConfigHandler.cropomatorInputs[i] + " could not be added to the Cropomator's catalyst list. Please confirm you have the name and formatting correct.");
				}
				
				//Checks the read in string as modid:item:metadata
				if(unlocStack.length == 3)
				{
					//Attempts to find the Itemstack with given info modid and unlocalizedname
					if(GameRegistry.findItemStack(unlocStack[0], unlocStack[1], 1) != null)
					{
						//It then creates a new itemstack with the itemstack's getItem, qty 1, with a parsed int value of the 3rd argument
						ItemStack newStack = new ItemStack(GameRegistry.findItemStack(unlocStack[0], unlocStack[1], 1).getItem(), 1, Integer.parseInt(unlocStack[2]));
						TileEntityCropomator.addItemStackToCatalysts(newStack);
					}
					//If it fails, it prints out an error message letting the user know exactly where they made a mistake
					else
						System.out.println("[CrissCross] Item " + ConfigHandler.cropomatorInputs[i] + " could not be added to the Cropomator's catalyst list. Please confirm you have the name and formatting correct.");
				}
			}
			//Gotta catch em all
			catch(Exception e)
			{
				System.out.println("[CrissCross]: Error reading itemstack for catalysts from input file at item: " + (i + 1));				
			}
		}
	}
}
