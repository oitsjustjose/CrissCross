package com.oitsjustjose.criss_cross.Recipes;

import java.util.ArrayList;

import com.oitsjustjose.criss_cross.Items.CCItems;
import com.oitsjustjose.criss_cross.Items.ItemDust;
import com.oitsjustjose.criss_cross.TileEntity.TileEntityCropomator;
import com.oitsjustjose.criss_cross.TileEntity.TileEntityElectroextractor;
import com.oitsjustjose.criss_cross.TileEntity.TileEntityWoodchipper;
import com.oitsjustjose.criss_cross.Util.CCLog;
import com.oitsjustjose.criss_cross.Util.ConfigHandler;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
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

		ElectroextractorRecipes.getInstance().addRecipe(new ItemStack(Blocks.diamond_ore),
				new ItemStack(Items.diamond, electroQTY));
		ElectroextractorRecipes.getInstance().addRecipe(new ItemStack(Blocks.emerald_ore),
				new ItemStack(Items.emerald, electroQTY));
		ElectroextractorRecipes.getInstance().addRecipe(new ItemStack(Blocks.coal_ore),
				new ItemStack(Items.coal, electroQTY));
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
					if (GameRegistry.findItemStack(unlocStack[0], unlocStack[1], 1) != null)
					{
						ItemStack newStack = GameRegistry.findItemStack(unlocStack[0], unlocStack[1], 1);
						CropomatorRecipes.getInstance().addRecipe(newStack, new ItemStack(newStack.getItem(), cropomatorQTY, newStack.getItemDamage()));
					}
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
						newStack.stackSize = cropomatorQTY;
						CropomatorRecipes.getInstance().addRecipe(newStack, new ItemStack(newStack.getItem(), cropomatorQTY, newStack.getItemDamage()));
					}
					else
					{
						CCLog.warn("Item " + ConfigHandler.cropomatorInputs[i]
								+ " could not be added to the Cropomator's recipe list.");
						CCLog.warn("Please confirm you have the name and formatting correct.");
					}
				}
			}
			catch (Exception e)
			{
				CCLog.warn("[CrissCross]: Error reading itemstack for inputs from input file at item: " + (i + 1));
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
					if (GameRegistry.findItemStack(unlocStack[0], unlocStack[1], 1) != null)
						TileEntityCropomator.addFuel(GameRegistry.findItemStack(unlocStack[0], unlocStack[1], 1));
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
						TileEntityCropomator.addFuel(newStack);
					}
					else
					{
						CCLog.warn("Item " + ConfigHandler.cropomatorInputs[i]
								+ " could not be added to the Cropomator's catalyst list.");
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

	public static void initWoodchipperOreDictionary()
	{
		ArrayList<ItemStack> logs = OreDictionary.getOres("logWood");
		ArrayList<ItemStack> planks = OreDictionary.getOres("plankWood");

		String logName;
		String plankName;

		for (int i = 0; i < logs.size(); i++)
		{
			for (int logMeta = 0; logMeta < 16; logMeta++)
			{
				ItemStack log = new ItemStack(logs.get(i).getItem(), 1, logMeta);
				logName = log.getDisplayName();
				logName = logName.toLowerCase();
				logName = logName.replace("wood", "");
				logName = logName.trim();

				for (int j = 0; j < planks.size(); j++)
				{
					for (int plankMeta = 0; plankMeta < 16; plankMeta++)
					{
						ItemStack plank = new ItemStack(planks.get(j).getItem(), 1, plankMeta);
						plankName = plank.getDisplayName();
						plankName = plankName.toLowerCase();
						plankName = plankName.replace("wood", "");
						plankName = plankName.replace("planks", "");
						plankName = plankName.trim();
						plank.stackSize = woodchipperQTY;
						if (plankName.contains(logName))
						{
							//Prevents Weird Metadata Being Initialized
							if(log.getItemDamage() >= 4)
								break;
							//Prevents Natura Metadata Breakage
							if(Block.getBlockFromItem(log.getItem()).getClass().toString().contains("mods.natura.blocks.trees.DarkTreeBlock"))
								if(log.getItemDamage() >= 2)
									break;
							//Prevents ExtraUtils from breaking stuff too
							if(plank.getDisplayName().toLowerCase().contains("colored"))
								break;
							//Prevents Weird MINECRAFT metadata (thanks Jeb >_> )
							if(log.getDisplayName().contains("Acacia"))
							{
								if(log.getItemDamage() == 0)
									WoodchipperRecipes.getInstance().addRecipe(log, plank);
							}
							else
								WoodchipperRecipes.getInstance().addRecipe(log, plank);
						}
					}
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
					if (GameRegistry.findItemStack(unlocStack[0], unlocStack[1], 1) != null)
						TileEntityWoodchipper.addFuel(GameRegistry.findItemStack(unlocStack[0], unlocStack[1], 0));
					else
					{
						CCLog.warn("Item " + ConfigHandler.woodchipperFuels[i]
								+ " could not be added to the Woodchipper's fuel list.");
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
						TileEntityWoodchipper.addFuel(newStack);
					}
					else
					{
						CCLog.warn("Item " + ConfigHandler.woodchipperFuels[i]
								+ " could not be added to the Woodchipper's fuel list.");
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
					if (GameRegistry.findItemStack(unlocStack[0], unlocStack[1], 1) != null)
						TileEntityElectroextractor.addFuel(GameRegistry.findItemStack(unlocStack[0], unlocStack[1], 0));
					else
					{
						CCLog.warn("Item " + ConfigHandler.electroextractorEnergySources[i]
								+ " could not be added to the Electroextractor's fuel list.");
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
			if (OreDictionary.doesOreNameExist("ore" + entry[0]) && OreDictionary.doesOreNameExist("ingot" + entry[0]))
			{
				ArrayList<ItemStack> ores = OreDictionary.getOres("ore" + entry[0]);
				ItemDust.addDustType(entry[0], Integer.parseInt(entry[1]));
				for (int j = 0; j < ores.size(); j++)
					ElectroextractorRecipes.getInstance().addRecipe(ores.get(j),
							new ItemStack(CCItems.dusts, electroQTY, dustNames.size() - 1));
			}
			else
			{
				CCLog.warn("Your oreDictionary Entry " + ConfigHandler.electroextractorOreDictInputs[i]
						+ " could not be registered.");
				if (!OreDictionary.doesOreNameExist("ore" + entry[0]))
					CCLog.warn("This is because your oreDictionary Entry was not valid or registered as " + entry[0]
							+ ".");
				else
					if (!OreDictionary.doesOreNameExist("ingot" + entry[0]))
						CCLog.warn("This is because your oreDictionary Entry does not have an ingot for " + entry[0]
								+ ".");
			}
		}

		for (int i = 0; i < dustNames.size(); i++)
		{
			ItemStack ingot = null;
			if (OreDictionary.doesOreNameExist("ingot" + dustNames.get(i)))
				ingot = OreDictionary.getOres("ingot" + dustNames.get(i)).get(0);
			if (ingot != null)
				GameRegistry.addSmelting(new ItemStack(CCItems.dusts, 1, i), ingot, 0.0F);
		}
	}
}