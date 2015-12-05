package com.oitsjustjose.criss_cross.Recipes;

import java.util.ArrayList;
import java.util.List;

import com.oitsjustjose.criss_cross.Items.CCItems;
import com.oitsjustjose.criss_cross.Items.ItemDust;
import com.oitsjustjose.criss_cross.TileEntity.TileEntityCropomator;
import com.oitsjustjose.criss_cross.TileEntity.TileEntityElectroextractor;
import com.oitsjustjose.criss_cross.TileEntity.TileEntityWoodchipper;
import com.oitsjustjose.criss_cross.Util.CCLog;
import com.oitsjustjose.criss_cross.Util.ClientProxy;
import com.oitsjustjose.criss_cross.Util.ConfigHandler;
import com.oitsjustjose.criss_cross.Util.ItemHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class CCMachineRecipes
{
	public static int cropomatorQTY = ConfigHandler.cropomatorOutput;
	public static int electroQTY = ConfigHandler.electroextractorOutput;
	public static int woodchipperQTY = ConfigHandler.woodchipperOutput;

	public static void init()
	{
		initCropomatorRecipes();
		initCropomatorFuels();
		initElectroextractorFuels();
		initElectroextractorOreDictionary();
		initWoodchipperFuels();
		initWoodchipperOreDictionary();

		ElectroextractorRecipes.getInstance().addRecipe(new ItemStack(Blocks.diamond_ore), new ItemStack(Items.diamond, electroQTY));
		ElectroextractorRecipes.getInstance().addRecipe(new ItemStack(Blocks.emerald_ore), new ItemStack(Items.emerald, electroQTY));
		ElectroextractorRecipes.getInstance().addRecipe(new ItemStack(Blocks.coal_ore), new ItemStack(Items.coal, electroQTY));
	}

	public static void initCropomatorRecipes()
	{
		for (int i = 0; i < ConfigHandler.cropomatorInputs.length; i++)
		{
			try
			{
				String[] unlocStack = ConfigHandler.cropomatorInputs[i].split(":");
				if (unlocStack.length == 2)
				{
					if (ConfigHandler.findItemStack(unlocStack[0], unlocStack[1]) != null)
					{
						ItemStack newStack = ConfigHandler.findItemStack(unlocStack[0], unlocStack[1]);
						CropomatorRecipes.getInstance().addRecipe(newStack, new ItemStack(newStack.getItem(), cropomatorQTY, newStack
								.getItemDamage()));
					}
					else
					{
						CCLog.warn("Item " + ConfigHandler.cropomatorInputs[i] + " could not be added to the Cropomator's recipe list.");
						CCLog.warn("Please confirm you have the name and formatting correct.");

					}
				}
				if (unlocStack.length == 3)
				{
					if (ConfigHandler.findItemStack(unlocStack[0], unlocStack[1]) != null)
					{
						ItemStack newStack = new ItemStack(ConfigHandler.findItemStack(unlocStack[0], unlocStack[1]).getItem(), 1, Integer.parseInt(
								unlocStack[2]));
						newStack.stackSize = cropomatorQTY;
						CropomatorRecipes.getInstance().addRecipe(newStack, new ItemStack(newStack.getItem(), cropomatorQTY, newStack
								.getItemDamage()));
					}
					else
					{
						CCLog.warn("Item " + ConfigHandler.cropomatorInputs[i] + " could not be added to the Cropomator's recipe list.");
						CCLog.warn("Please confirm you have the name and formatting correct.");
					}
				}
			}
			catch (Exception e)
			{
				CCLog.warn("Error reading itemstack for inputs from input file at item: " + (i + 1));
			}

		}
	}

	public static void initCropomatorFuels()
	{
		for (int i = 0; i < ConfigHandler.cropomatorCatalysts.length; i++)
		{
			try
			{
				String[] unlocStack = ConfigHandler.cropomatorCatalysts[i].split(":");
				if (unlocStack.length == 2)
				{
					if (ConfigHandler.findItemStack(unlocStack[0], unlocStack[1]) != null)
						TileEntityCropomator.addFuel(ConfigHandler.findItemStack(unlocStack[0], unlocStack[1]));
					else
					{
						CCLog.warn("Item " + ConfigHandler.cropomatorInputs[i] + " could not be added to the Cropomator's catalyst list.");
						CCLog.warn("Please confirm you have the name and formatting correct.");
					}
				}

				if (unlocStack.length == 3)
				{
					if (ConfigHandler.findItemStack(unlocStack[0], unlocStack[1]) != null)
					{
						ItemStack newStack = new ItemStack(ConfigHandler.findItemStack(unlocStack[0], unlocStack[1]).getItem(), 1, Integer.parseInt(
								unlocStack[2]));
						TileEntityCropomator.addFuel(newStack);
					}
					else
					{
						CCLog.warn("Item " + ConfigHandler.cropomatorInputs[i] + " could not be added to the Cropomator's catalyst list.");
						CCLog.warn("Please confirm you have the name and formatting correct.");
					}
				}
			}
			catch (Exception e)
			{
				CCLog.warn("Error reading itemstack for catalysts from input file at item: " + (i + 1));
			}
		}
	}

	// All Credit goes to TeamCoFH for the code. After so many algorithms
	// attempted, I gave up and used one that worked:

	public static void initWoodchipperOreDictionary()
	{
		Container local1 = new Container()
		{
			public boolean canInteractWith(EntityPlayer paramAnonymousEntityPlayer)
			{
				return false;
			}
		};
		InventoryCrafting localInventoryCrafting = new InventoryCrafting(local1, 3, 3);
		for (int i = 0; i < 9; i++)
		{
			localInventoryCrafting.setInventorySlotContents(i, null);
		}
		List<ItemStack> localArrayList = OreDictionary.getOres("logWood");
		for (int j = 0; j < localArrayList.size(); j++)
		{
			ItemStack localItemStack1 = (ItemStack) localArrayList.get(j);
			ItemStack localItemStack3;
			ItemStack localItemStack4;
			if (ItemHelper.getItemDamage(localItemStack1) == 32767)
			{
				for (int k = 0; k < 16; k++)
				{
					localItemStack3 = ItemHelper.cloneStack(localItemStack1, 1);
					localItemStack3.setItemDamage(k);
					localInventoryCrafting.setInventorySlotContents(0, localItemStack3);
					localItemStack4 = ItemHelper.findMatchingRecipe(localInventoryCrafting, null);
					if (localItemStack4 != null)
					{
						ItemStack localItemStack5 = localItemStack4.copy();
						ItemStack tmp129_127 = localItemStack5;
						tmp129_127.stackSize = woodchipperQTY;
						WoodchipperRecipes.getInstance().addRecipe(localItemStack3, localItemStack5);
					}
				}
			}
			else
			{
				ItemStack localItemStack2 = localItemStack1.copy();
				localInventoryCrafting.setInventorySlotContents(0, localItemStack2);
				localItemStack3 = ItemHelper.findMatchingRecipe(localInventoryCrafting, null);
				if (localItemStack3 != null)
				{
					localItemStack4 = localItemStack3.copy();
					ItemStack tmp201_199 = localItemStack4;
					tmp201_199.stackSize = woodchipperQTY;
					WoodchipperRecipes.getInstance().addRecipe(localItemStack2, localItemStack4);
				}
			}
		}
	}

	public static void initWoodchipperFuels()
	{
		for (int i = 0; i < ConfigHandler.woodchipperFuels.length; i++)
		{
			try
			{
				String[] unlocStack = ConfigHandler.woodchipperFuels[i].split(":");
				if (unlocStack.length == 2)
				{
					if (ConfigHandler.findItemStack(unlocStack[0], unlocStack[1]) != null)
						TileEntityWoodchipper.addFuel(ConfigHandler.findItemStack(unlocStack[0], unlocStack[1]));
					else
					{
						CCLog.warn("Item " + ConfigHandler.woodchipperFuels[i] + " could not be added to the Woodchipper's fuel list.");
						CCLog.warn("Please confirm you have the name and formatting correct.");
					}
				}

				if (unlocStack.length == 3)
				{
					if (ConfigHandler.findItemStack(unlocStack[0], unlocStack[1]) != null)
					{
						ItemStack newStack = new ItemStack(ConfigHandler.findItemStack(unlocStack[0], unlocStack[1]).getItem(), 1, Integer.parseInt(
								unlocStack[2]));
						TileEntityWoodchipper.addFuel(newStack);
					}
					else
					{
						CCLog.warn("Item " + ConfigHandler.woodchipperFuels[i] + " could not be added to the Woodchipper's fuel list.");
						CCLog.warn("Please confirm you have the name and formatting correct.");
					}
				}
			}
			catch (Exception e)
			{
				CCLog.warn("Error reading itemstack for Woodchipper's energy sources at item: " + (i + 1));
			}
		}
	}

	static void initElectroextractorFuels()
	{
		for (int i = 0; i < ConfigHandler.electroextractorEnergySources.length; i++)
		{
			try
			{
				String[] unlocStack = ConfigHandler.electroextractorEnergySources[i].split(":");
				if (unlocStack.length == 2)
				{
					if (ConfigHandler.findItemStack(unlocStack[0], unlocStack[1]) != null)
						TileEntityElectroextractor.addFuel(ConfigHandler.findItemStack(unlocStack[0], unlocStack[1]));
					else
					{
						CCLog.warn("Item " + ConfigHandler.electroextractorEnergySources[i]
								+ " could not be added to the Electroextractor's fuel list.");
						CCLog.warn("Please confirm you have the name and formatting correct.");
					}
				}

				if (unlocStack.length == 3)
				{
					if (ConfigHandler.findItemStack(unlocStack[0], unlocStack[1]) != null)
					{
						ItemStack newStack = new ItemStack(ConfigHandler.findItemStack(unlocStack[0], unlocStack[1]).getItem(), 1, Integer.parseInt(
								unlocStack[2]));
						TileEntityElectroextractor.addFuel(newStack);
					}
					else
					{
						CCLog.warn("Item " + ConfigHandler.electroextractorEnergySources[i]
								+ " could not be added to the Electroextractor's fuel list.");
						CCLog.warn("Please confirm you have the name and formatting correct.");
					}
				}
			}
			catch (Exception e)
			{
				CCLog.warn("Error reading itemstack for electroextractor's energy sources at item: " + (i + 1));
			}
		}
	}

	static void initElectroextractorOreDictionary()
	{
		ArrayList<String> dustNames = ItemDust.getDusts();
		for (int i = 0; i < ConfigHandler.electroextractorOreDictInputs.length; i++)
		{
			String[] entry = ConfigHandler.electroextractorOreDictInputs[i].split(":");
			if (doesOreNameExist("ore" + entry[0]) && doesOreNameExist("ingot" + entry[0]))
			{
				List<ItemStack> ores = OreDictionary.getOres("ore" + entry[0]);
				ItemDust.addDustType(entry[0], Integer.parseInt(entry[1]));
				for (int j = 0; j < ores.size(); j++)
					ElectroextractorRecipes.getInstance().addRecipe(ores.get(j), new ItemStack(CCItems.dusts, electroQTY, dustNames.size() - 1));
			}
			else
			{
				CCLog.warn("Your oreDictionary Entry " + ConfigHandler.electroextractorOreDictInputs[i] + " could not be registered.");
				if (!doesOreNameExist("ore" + entry[0]))
					CCLog.warn("This is because your oreDictionary Entry was not valid or registered as " + entry[0] + ".");
				else
					if (!doesOreNameExist("ingot" + entry[0]))
						CCLog.warn("This is because your oreDictionary Entry does not have an ingot for " + entry[0] + ".");
			}
		}

		for (int i = 0; i < dustNames.size(); i++)
		{
			ItemStack ingot = null;
			if (doesOreNameExist("ingot" + dustNames.get(i)))
				ingot = OreDictionary.getOres("ingot" + dustNames.get(i)).get(0);
			if (ingot != null)
				GameRegistry.addSmelting(new ItemStack(CCItems.dusts, 1, i), ingot, 0.0F);
		}
	}

	static boolean doesOreNameExist(String oreName)
	{
		List<ItemStack> ores = OreDictionary.getOres(oreName);
		return ores.size() > 0 ? true : false;
	}
}