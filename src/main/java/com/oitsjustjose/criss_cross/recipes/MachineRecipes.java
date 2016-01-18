package com.oitsjustjose.criss_cross.recipes;

import java.util.ArrayList;
import java.util.List;

import com.oitsjustjose.criss_cross.items.ItemDust;
import com.oitsjustjose.criss_cross.lib.Config;
import com.oitsjustjose.criss_cross.lib.LibItems;
import com.oitsjustjose.criss_cross.recipes.machine.CropomatorRecipes;
import com.oitsjustjose.criss_cross.recipes.machine.ElectroextractorRecipes;
import com.oitsjustjose.criss_cross.recipes.machine.ScribeRecipes;
import com.oitsjustjose.criss_cross.recipes.machine.WoodchipperRecipes;
import com.oitsjustjose.criss_cross.tileentity.TileCropomator;
import com.oitsjustjose.criss_cross.tileentity.TileElectroextractor;
import com.oitsjustjose.criss_cross.tileentity.TileWoodchipper;
import com.oitsjustjose.criss_cross.util.ItemHelper;
import com.oitsjustjose.criss_cross.util.LogHelper;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class MachineRecipes
{
	public static void init()
	{
		parseCropomatorRecipes();
		parseCropomatorFuels();
		parseElectroextractorFuels();
		parseElectroextractorRecipes();
		parseWoodchipperFuels();
		parseWoodchipperRecipes();
		parseScribeRecipes();

		ElectroextractorRecipes.getInstance().addRecipe(new ItemStack(Blocks.diamond_ore), new ItemStack(Items.diamond, Config.electroextractorOutput));
		ElectroextractorRecipes.getInstance().addRecipe(new ItemStack(Blocks.emerald_ore), new ItemStack(Items.emerald, Config.electroextractorOutput));
		ElectroextractorRecipes.getInstance().addRecipe(new ItemStack(Blocks.coal_ore), new ItemStack(Items.coal, Config.electroextractorOutput));
		ElectroextractorRecipes.getInstance().addRecipe(new ItemStack(Blocks.gravel), new ItemStack(Items.flint));
		ElectroextractorRecipes.getInstance().addRecipe(new ItemStack(Blocks.cobblestone, 1), new ItemStack(Blocks.sand, 2));
		ElectroextractorRecipes.getInstance().addRecipe(new ItemStack(Blocks.sandstone, 1, Short.MAX_VALUE), new ItemStack(Blocks.sand, 4));
		ElectroextractorRecipes.getInstance().addRecipe(new ItemStack(Blocks.red_sandstone, 1, Short.MAX_VALUE), new ItemStack(Blocks.sand, 4, 1));

		registerOreDict();
	}

	static void registerOreDict()
	{
		ArrayList<String> dustNames = ItemDust.getDusts();

		for (int i = 0; i < dustNames.size(); i++)
			OreDictionary.registerOre("dust" + dustNames.get(i), new ItemStack(LibItems.dusts, 1, i));
	}

	static void parseScribeRecipes()
	{
		for (int i = 0; i < Config.scribeRecipes.length; i++)
			try
			{
				String[] parts = Config.scribeRecipes[i].split("[\\W]");
				if (parts.length == 5)
				{
					String modid = parts[0];
					String object = parts[1];
					int metadata = Integer.parseInt(parts[2]);
					int qty = Integer.parseInt(parts[3]);
					int enchID = Integer.parseInt(parts[4]);

					if (Config.findItemStack(modid, object) != null)
					{
						if (Enchantment.getEnchantmentById(enchID) != null)
						{
							ItemStack newStack = Config.findItemStack(modid, object);
							ScribeRecipes.getInstance().addRecipe(new ItemStack(newStack.getItem(), qty, metadata), getEnchantedBook(enchID));
						}
						else
							LogHelper.warn("Enchantment ID " + enchID + " does not appear to be valid. Skipping Scribe recipe addition.");
					}
					else
						LogHelper.warn("Item " + (modid + " " + object) + " does not appear to be valid. Skipping Scribe recipe addition.");
				}
				else if (parts.length == 3)
				{
					String oreDict = parts[0];
					int qty = Integer.parseInt(parts[1]);
					int enchID = Integer.parseInt(parts[2]);

					if (OreDictionary.getOres(oreDict).size() > 0)
					{
						if (Enchantment.getEnchantmentById(enchID) != null)
							if (qty > 0)
								ScribeRecipes.getInstance().addRecipe(oreDict, qty, getEnchantedBook(enchID));

							else
								LogHelper.warn("Enchantment ID " + enchID + " does not appear to be valid. Skipping Scribe recipe addition.");
					}
					else
						LogHelper.warn("Ore Dictionary Name " + oreDict + " does not appear to be valid. Skipping Scribe recipe addition.");
				}
				else
					LogHelper.warn("Your formatting for entry " + (i + 1) + " does not appear to be correct.");
			}
			catch (Exception e)
			{
				LogHelper.warn("Error reading itemstack for Scribe from input file at item: " + (i + 1));
			}
	}

	static void parseCropomatorRecipes()
	{
		for (int i = 0; i < Config.cropomatorInputs.length; i++)
			try
			{
				String[] parts = Config.cropomatorInputs[i].split(":");
				if (parts.length == 2)
					if (Config.findItemStack(parts[0], parts[1]) != null)
					{
						ItemStack inputStack = Config.findItemStack(parts[0], parts[1]);
						ItemStack outputStack = inputStack.copy();
						outputStack.stackSize = Config.cropomatorOutput;
						CropomatorRecipes.getInstance().addRecipe(inputStack, outputStack);
					}
					else
					{
						LogHelper.warn("Item " + Config.cropomatorInputs[i] + " could not be added to the Cropomator's recipe list.");
						LogHelper.warn("Please confirm you have the name and formatting correct.");

					}
				if (parts.length == 3)
					if (Config.findItemStack(parts[0], parts[1]) != null)
					{
						ItemStack inputStack = new ItemStack(Config.findItemStack(parts[0], parts[1]).getItem(), 1, Integer.parseInt(parts[2]));
						ItemStack outputStack = inputStack.copy();
						outputStack.stackSize = Config.cropomatorOutput;
						CropomatorRecipes.getInstance().addRecipe(inputStack, outputStack);
					}
					else
					{
						LogHelper.warn("Item " + Config.cropomatorInputs[i] + " could not be added to the Cropomator's recipe list.");
						LogHelper.warn("Please confirm you have the name and formatting correct.");
					}
			}
			catch (Exception e)
			{
				LogHelper.warn("Error reading itemstack for Cropomator from input file at item: " + (i + 1));
			}
	}

	static void parseCropomatorFuels()
	{
		for (int i = 0; i < Config.cropomatorCatalysts.length; i++)
			try
			{
				String[] parts = Config.cropomatorCatalysts[i].split(":");
				if (parts.length == 2)
					if (Config.findItemStack(parts[0], parts[1]) != null)
						TileCropomator.addFuel(Config.findItemStack(parts[0], parts[1]));
					else
					{
						LogHelper.warn("Item " + Config.cropomatorInputs[i] + " could not be added to the Cropomator's catalyst list.");
						LogHelper.warn("Please confirm you have the name and formatting correct.");
					}

				if (parts.length == 3)
					if (Config.findItemStack(parts[0], parts[1]) != null)
					{
						ItemStack newStack = new ItemStack(Config.findItemStack(parts[0], parts[1]).getItem(), 1, Integer.parseInt(parts[2]));
						TileCropomator.addFuel(newStack);
					}
					else
					{
						LogHelper.warn("Item " + Config.cropomatorInputs[i] + " could not be added to the Cropomator's catalyst list.");
						LogHelper.warn("Please confirm you have the name and formatting correct.");
					}
			}
			catch (Exception e)
			{
				LogHelper.warn("Error reading itemstack for Cropomator Cataylsts from input file at item: " + (i + 1));
			}
	}

	static void parseWoodchipperRecipes()
	{
		Container container = new Container()
		{
			@Override
			public boolean canInteractWith(EntityPlayer p)
			{
				return false;
			}
		};

		InventoryCrafting invCraft = new InventoryCrafting(container, 3, 3);

		for (int i = 0; i < 9; i++)
			invCraft.setInventorySlotContents(i, null);

		List<ItemStack> oreLogs = OreDictionary.getOres("logWood");

		for (int j = 0; j < oreLogs.size(); j++)
		{
			ItemStack currentLog = oreLogs.get(j);
			ItemStack metaStack, plank;
			if (ItemHelper.getItemDamage(currentLog) == 32767)
				for (int k = 0; k < 16; k++)
				{
					metaStack = ItemHelper.cloneStack(currentLog, 1);
					metaStack.setItemDamage(k);
					invCraft.setInventorySlotContents(0, metaStack);
					plank = ItemHelper.findMatchingRecipe(invCraft, null);
					if (plank != null)
					{
						ItemStack metaPlank = plank.copy();
						ItemStack temp = metaPlank;
						temp.stackSize = Config.woodchipperOutput;
						WoodchipperRecipes.getInstance().addRecipe(metaStack, metaPlank);
					}
				}
			else
			{
				ItemStack nonMetaStack = currentLog.copy();
				invCraft.setInventorySlotContents(0, nonMetaStack);
				metaStack = ItemHelper.findMatchingRecipe(invCraft, null);
				if (metaStack != null)
				{
					plank = metaStack.copy();
					ItemStack temp = plank;
					temp.stackSize = Config.woodchipperOutput;
					WoodchipperRecipes.getInstance().addRecipe(nonMetaStack, plank);
				}
			}
		}
	}

	static void parseWoodchipperFuels()
	{
		for (int i = 0; i < Config.woodchipperFuels.length; i++)
			try
			{
				String[] parts = Config.woodchipperFuels[i].split(":");
				if (parts.length == 2)
					if (Config.findItemStack(parts[0], parts[1]) != null)
						TileWoodchipper.addFuel(Config.findItemStack(parts[0], parts[1]));
					else
					{
						LogHelper.warn("Item " + Config.woodchipperFuels[i] + " could not be added to the Woodchipper's fuel list.");
						LogHelper.warn("Please confirm you have the name and formatting correct.");
					}

				if (parts.length == 3)
					if (Config.findItemStack(parts[0], parts[1]) != null)
					{
						ItemStack newStack = new ItemStack(Config.findItemStack(parts[0], parts[1]).getItem(), 1, Integer.parseInt(parts[2]));
						TileWoodchipper.addFuel(newStack);
					}
					else
					{
						LogHelper.warn("Item " + Config.woodchipperFuels[i] + " could not be added to the Woodchipper's fuel list.");
						LogHelper.warn("Please confirm you have the name and formatting correct.");
					}
			}
			catch (Exception e)
			{
				LogHelper.warn("Error reading itemstack for Woodchipper's energy sources at item: " + (i + 1));
			}
	}
	
	static void parseElectroextractorRecipes()
	{
		ArrayList<String> dustNames = ItemDust.getDusts();
		for (int i = 0; i < Config.electroextractorOreDictInputs.length; i++)
		{
			String[] entry = Config.electroextractorOreDictInputs[i].split(":");
			if (doesOreNameExist(entry[0]) && doesOreNameExist(entry[0].replace("ore", "ingot")))
			{
				List<ItemStack> ores = OreDictionary.getOres(entry[0]);
				ItemDust.addDustType(entry[0].replace("ore", ""), Integer.parseInt(entry[1]));
				for (int j = 0; j < ores.size(); j++)
					ElectroextractorRecipes.getInstance().addRecipe(ores.get(j), new ItemStack(LibItems.dusts, Config.electroextractorOutput, dustNames.size() - 1));
			}
			else
			{
				LogHelper.warn("Your oreDictionary Entry " + Config.electroextractorOreDictInputs[i] + " could not be registered.");
				if (!doesOreNameExist(entry[0]))
					LogHelper.warn("This is because your oreDictionary Entry was not valid or registered as " + entry[0] + ".");
				else if (!doesOreNameExist(entry[0].replace("ore", "ingot")))
					LogHelper.warn("This is because your oreDictionary Entry does not have an ingot for " + entry[0] + ".");
			}
		}

		for (int i = 0; i < dustNames.size(); i++)
		{
			ItemStack ingot = null;

			if (doesOreNameExist("ingot" + dustNames.get(i)))
				ingot = OreDictionary.getOres("ingot" + dustNames.get(i)).get(0);
			if (ingot != null)
				GameRegistry.addSmelting(new ItemStack(LibItems.dusts, 1, i), ingot, FurnaceRecipes.instance().getSmeltingExperience(ingot));
		}
	}

	static void parseElectroextractorFuels()
	{
		for (int i = 0; i < Config.electroextractorFuels.length; i++)
			try
			{
				String[] parts = Config.electroextractorFuels[i].split(":");
				if (parts.length == 2)
					if (Config.findItemStack(parts[0], parts[1]) != null)
						TileElectroextractor.addFuel(Config.findItemStack(parts[0], parts[1]));
					else
					{
						LogHelper.warn("Item " + Config.electroextractorFuels[i] + " could not be added to the Electroextractor's fuel list.");
						LogHelper.warn("Please confirm you have the name and formatting correct.");
					}

				if (parts.length == 3)
					if (Config.findItemStack(parts[0], parts[1]) != null)
					{
						ItemStack newStack = new ItemStack(Config.findItemStack(parts[0], parts[1]).getItem(), 1, Integer.parseInt(parts[2]));
						TileElectroextractor.addFuel(newStack);
					}
					else
					{
						LogHelper.warn("Item " + Config.electroextractorFuels[i] + " could not be added to the Electroextractor's fuel list.");
						LogHelper.warn("Please confirm you have the name and formatting correct.");
					}
			}
			catch (Exception e)
			{
				LogHelper.warn("Error reading itemstack for electroextractor's energy sources at item: " + (i + 1));
			}
	}

	static ItemStack getEnchantedBook(int id)
	{
		ItemStack retBook = new ItemStack(Items.enchanted_book);
		Items.enchanted_book.addEnchantment(retBook, new EnchantmentData(Enchantment.getEnchantmentById(id), 1));

		return retBook;
	}

	static boolean doesOreNameExist(String oreName)
	{
		List<ItemStack> ores = OreDictionary.getOres(oreName);
		return ores.size() > 0 ? true : false;
	}
}