package com.oitsjustjose.criss_cross.Recipes;

import com.oitsjustjose.criss_cross.CrissCross;
import com.oitsjustjose.criss_cross.TileEntity.TileEntityCropomator;
import com.oitsjustjose.criss_cross.Util.CCLog;
import com.oitsjustjose.criss_cross.Util.ConfigHandler;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;

public class CropomatorRecipes
{
	public static void initRecipes()
	{
		for (int i = 0; i < ConfigHandler.cropomatorInputs.length; i++)
		{
			try
			{
				String[] unlocStack = ConfigHandler.cropomatorInputs[i].split(":");
				if (unlocStack.length == 2)
				{
					if (GameRegistry.findItemStack(unlocStack[0], unlocStack[1], 1) != null)
						TileEntityCropomator
								.addItemStackToOutputs(GameRegistry.findItemStack(unlocStack[0], unlocStack[1], 1));
					else
					{
						CCLog.warn("Item " + ConfigHandler.cropomatorInputs[i]
								+ " could not be added to the Cropomator's recipe list.");
						CCLog.warn("Please confirm you have the name and formatting correct.");

					}
				}
				if (unlocStack.length == 3)
				{
					if (GameRegistry.findItemStack(unlocStack[0], unlocStack[1], 1) != null)
					{
						ItemStack newStack = new ItemStack(
								GameRegistry.findItemStack(unlocStack[0], unlocStack[1], 1).getItem(), 1,
								Integer.parseInt(unlocStack[2]));
						TileEntityCropomator.addItemStackToOutputs(newStack);
					} else
					{
						CCLog.warn("Item " + ConfigHandler.cropomatorInputs[i]
								+ " could not be added to the Cropomator's recipe list.");
						CCLog.warn("Please confirm you have the name and formatting correct.");
					}
				}

			} catch (Exception e)
			{
				CCLog.warn("[CrissCross]: Error reading itemstack for inputs from input file at item: " + (i + 1));
			}

		}
	}

	public static void initCatalysts()
	{
		for (int i = 0; i < ConfigHandler.cropomatorCatalysts.length; i++)
		{
			try
			{
				String[] unlocStack = ConfigHandler.cropomatorCatalysts[i].split(":");
				if (unlocStack.length == 2)
				{
					if (GameRegistry.findItemStack(unlocStack[0], unlocStack[1], 1) != null)
						TileEntityCropomator
								.addItemStackToCatalysts(GameRegistry.findItemStack(unlocStack[0], unlocStack[1], 1));
					else
					{
						CCLog.warn("Item " + ConfigHandler.cropomatorInputs[i]
								+ " could not be added to the Cropomator's catalyst list.");
						CCLog.warn("Please confirm you have the name and formatting correct.");
					}
				}

				if (unlocStack.length == 3)
				{
					if (GameRegistry.findItemStack(unlocStack[0], unlocStack[1], 1) != null)
					{
						ItemStack newStack = new ItemStack(
								GameRegistry.findItemStack(unlocStack[0], unlocStack[1], 1).getItem(), 1,
								Integer.parseInt(unlocStack[2]));
						TileEntityCropomator.addItemStackToCatalysts(newStack);
					} else
					{
						CCLog.warn("Item " + ConfigHandler.cropomatorInputs[i]
								+ " could not be added to the Cropomator's catalyst list.");
						CCLog.warn("Please confirm you have the name and formatting correct.");
					}
				}
			} catch (Exception e)
			{
				CCLog.warn("Error reading itemstack for catalysts from input file at item: " + (i + 1));
			}
		}
	}
}
