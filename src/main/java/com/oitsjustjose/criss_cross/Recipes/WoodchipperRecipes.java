package com.oitsjustjose.criss_cross.Recipes;

import java.util.ArrayList;
import java.util.List;

import com.mojang.authlib.GameProfile;
import com.oitsjustjose.criss_cross.TileEntity.TileEntityCropomator;
import com.oitsjustjose.criss_cross.TileEntity.TileEntityWoodchipper;
import com.oitsjustjose.criss_cross.Util.ConfigHandler;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.oredict.OreDictionary;

public class WoodchipperRecipes
{
	public static void initRecipes()
	{
		
		
		//Iterates through the inputItems String array
		for(int i = 0; i < ConfigHandler.woodchipperLogs.length; i++)
		{
			//Done in a try loop just in case there's an issue with reading a value in.
			try
			{
				//Splits the modid:item / modid:item:metadata string at the ':' and makes a 
				//new Array of strings, each element containing the newly separated parts.
				String[] logs = ConfigHandler.woodchipperLogs[i].split(":");
				String[] planks = ConfigHandler.woodchipperPlanks[i].split(":");
				
				
				ItemStack log = GameRegistry.findItemStack(logs[0], logs[1], 1);
				ItemStack plank = GameRegistry.findItemStack(planks[0], planks[1], 1);
				
				boolean flag = (log != null) && (plank != null);
				
				if(!flag)
				{
					if(log == null)
						System.out.println("[CrissCross] Item " + ConfigHandler.woodchipperLogs[i] + " could not be added to the Woodchipper's recipe list. Please confirm you have the name and formatting correct.");
					if(plank == null)
						System.out.println("[CrissCross] Item " + ConfigHandler.woodchipperPlanks[i] + " could not be added to the Woodchipper's recipe list. Please confirm you have the name and formatting correct.");
					break;
				}
				
				//Checks the read in string as modid:item
				if(flag)
				{
					if(logs.length == 2)
					{
						if(planks.length == 2)
						{
							TileEntityWoodchipper.addRecipe(log, plank);
						}
						else if(planks.length == 3)
						{
							TileEntityWoodchipper.addRecipe(log, new ItemStack(plank.getItem(), 1, Integer.parseInt(planks[2])));
						}
					}
					else if(logs.length == 3)
					{
						if(planks.length == 2)
						{
							TileEntityWoodchipper.addRecipe(new ItemStack(log.getItem(), 1, Integer.parseInt(logs[2])), plank);
						}
						else if(planks.length == 3)
						{
							TileEntityWoodchipper.addRecipe(new ItemStack(log.getItem(), 1, Integer.parseInt(logs[2])), new ItemStack(plank.getItem(), 1, Integer.parseInt(planks[2])));
						}
					}
				}
			}
			//Gotta catch em all
			catch(Exception e)
			{
				System.out.println("[CrissCross]: Error reading itemstack for inputs from input file at item: " + (i + 1));
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
