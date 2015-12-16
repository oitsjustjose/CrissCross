package com.oitsjustjose.criss_cross.recipes;

import java.util.ArrayList;
import java.util.List;

import com.oitsjustjose.criss_cross.items.ItemDust;
import com.oitsjustjose.criss_cross.lib.ConfigHandler;
import com.oitsjustjose.criss_cross.lib.LibItems;
import com.oitsjustjose.criss_cross.tileentity.TileCobblegen;
import com.oitsjustjose.criss_cross.tileentity.TileCropomator;
import com.oitsjustjose.criss_cross.tileentity.TileElectroextractor;
import com.oitsjustjose.criss_cross.tileentity.TileStonegen;
import com.oitsjustjose.criss_cross.tileentity.TileWoodchipper;
import com.oitsjustjose.criss_cross.util.CCLog;
import com.oitsjustjose.criss_cross.util.ItemHelper;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
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
		initScribeRecipes();

		TileStonegen.addFuel(new ItemStack(Items.water_bucket));
		TileStonegen.addFuel(new ItemStack(LibItems.buckets));
		TileCobblegen.addFuel(new ItemStack(Items.water_bucket));
		TileCobblegen.addFuel(new ItemStack(LibItems.buckets));

		ElectroextractorRecipes.getInstance().addRecipe(new ItemStack(Blocks.diamond_ore), new ItemStack(Items.diamond, electroQTY));
		ElectroextractorRecipes.getInstance().addRecipe(new ItemStack(Blocks.emerald_ore), new ItemStack(Items.emerald, electroQTY));
		ElectroextractorRecipes.getInstance().addRecipe(new ItemStack(Blocks.coal_ore), new ItemStack(Items.coal, electroQTY));
		ElectroextractorRecipes.getInstance().addRecipe(new ItemStack(Blocks.gravel), new ItemStack(Items.flint));
		ElectroextractorRecipes.getInstance().addRecipe(new ItemStack(Blocks.cobblestone, 16), new ItemStack(Blocks.sand, 16));
		ElectroextractorRecipes.getInstance().addRecipe(new ItemStack(Blocks.sandstone, 1, Short.MAX_VALUE), new ItemStack(Blocks.sand, 4));
		ElectroextractorRecipes.getInstance().addRecipe(new ItemStack(Blocks.red_sandstone, 1, Short.MAX_VALUE), new ItemStack(Blocks.sand, 4, 1));

		registerOreDict();
	}

	public static void registerOreDict()
	{
		ArrayList<String> dustNames = ItemDust.getDusts();

		for (int i = 0; i < dustNames.size(); i++)
			OreDictionary.registerOre("dust" + dustNames.get(i), new ItemStack(LibItems.dusts, 1, i));
	}

	public static void initScribeRecipes()
	{
		for (int i = 0; i < ConfigHandler.scribeRecipes.length; i++)
		{
			try
			{
				String[] parts = ConfigHandler.scribeRecipes[i].split("[\\W]");
				if (parts.length == 5)
				{
					String modid = parts[0];
					String object = parts[1];
					int metadata = Integer.parseInt(parts[2]);
					int qty = Integer.parseInt(parts[3]);
					int enchID = Integer.parseInt(parts[4]);

					if (ConfigHandler.findItemStack(modid, object) != null && Enchantment.getEnchantmentById(enchID) != null)
					{
						ItemStack newStack = ConfigHandler.findItemStack(modid, object);
						ScribeRecipes.getInstance().addRecipe(new ItemStack(newStack.getItem(), qty, metadata), getEnchantedBook(enchID));
					}
					else
					{
						CCLog.warn("Item " + ConfigHandler.scribeRecipes[i] + " could not be added to the Scribe's recipe list.");
						CCLog.warn("Please confirm you have the name and formatting and ID's correct.");
					}
				}
				else if (parts.length == 3)
				{
					String oreDict = parts[0];
					int qty = Integer.parseInt(parts[1]);
					int enchID = Integer.parseInt(parts[2]);

					if (OreDictionary.getOres(oreDict).size() > 0)
					{
						if (Enchantment.getEnchantmentById(enchID) != null)
							ScribeRecipes.getInstance().addRecipe(oreDict, qty, getEnchantedBook(enchID));
						else
							CCLog.warn("Enchantment ID " + enchID + " does not appear to be valid. Skipping Scribe recipe addition.");
					}
					else
						CCLog.warn("Ore Dictionary Name " + oreDict + " does not appear to be valid. Skipping Scribe recipe addition.");
				}
				else
					CCLog.warn("Your formatting for entry " + (i + 1) + " does not appear to be correct.");
			}
			catch (Exception e)
			{
				CCLog.warn("Error reading itemstack for Scribe from input file at item: " + (i + 1));
			}
		}
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
						CropomatorRecipes.getInstance().addRecipe(newStack, new ItemStack(newStack.getItem(), cropomatorQTY, newStack.getItemDamage()));
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
						ItemStack newStack = new ItemStack(ConfigHandler.findItemStack(unlocStack[0], unlocStack[1]).getItem(), 1, Integer.parseInt(unlocStack[2]));
						newStack.stackSize = cropomatorQTY;
						CropomatorRecipes.getInstance().addRecipe(newStack, new ItemStack(newStack.getItem(), cropomatorQTY, newStack.getItemDamage()));
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
				CCLog.warn("Error reading itemstack for Cropomator from input file at item: " + (i + 1));
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
						TileCropomator.addFuel(ConfigHandler.findItemStack(unlocStack[0], unlocStack[1]));
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
						ItemStack newStack = new ItemStack(ConfigHandler.findItemStack(unlocStack[0], unlocStack[1]).getItem(), 1, Integer.parseInt(unlocStack[2]));
						TileCropomator.addFuel(newStack);
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
				CCLog.warn("Error reading itemstack for Cropomator Cataylsts from input file at item: " + (i + 1));
			}
		}
	}

	// All Credit goes to TeamCoFH for the code. After so many algorithms
	// attempted, I gave up and used one that worked:

	public static void initWoodchipperOreDictionary()
	{
		Container local1 = new Container()
		{
			public boolean canInteractWith(EntityPlayer p)
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
					ItemStack temp = localItemStack4;
					temp.stackSize = woodchipperQTY;
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
						TileWoodchipper.addFuel(ConfigHandler.findItemStack(unlocStack[0], unlocStack[1]));
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
						ItemStack newStack = new ItemStack(ConfigHandler.findItemStack(unlocStack[0], unlocStack[1]).getItem(), 1, Integer.parseInt(unlocStack[2]));
						TileWoodchipper.addFuel(newStack);
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
						TileElectroextractor.addFuel(ConfigHandler.findItemStack(unlocStack[0], unlocStack[1]));
					else
					{
						CCLog.warn("Item " + ConfigHandler.electroextractorEnergySources[i] + " could not be added to the Electroextractor's fuel list.");
						CCLog.warn("Please confirm you have the name and formatting correct.");
					}
				}

				if (unlocStack.length == 3)
				{
					if (ConfigHandler.findItemStack(unlocStack[0], unlocStack[1]) != null)
					{
						ItemStack newStack = new ItemStack(ConfigHandler.findItemStack(unlocStack[0], unlocStack[1]).getItem(), 1, Integer.parseInt(unlocStack[2]));
						TileElectroextractor.addFuel(newStack);
					}
					else
					{
						CCLog.warn("Item " + ConfigHandler.electroextractorEnergySources[i] + " could not be added to the Electroextractor's fuel list.");
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
					ElectroextractorRecipes.getInstance().addRecipe(ores.get(j), new ItemStack(LibItems.dusts, electroQTY, dustNames.size() - 1));
			}
			else
			{
				CCLog.warn("Your oreDictionary Entry " + ConfigHandler.electroextractorOreDictInputs[i] + " could not be registered.");
				if (!doesOreNameExist("ore" + entry[0]))
					CCLog.warn("This is because your oreDictionary Entry was not valid or registered as " + entry[0] + ".");
				else if (!doesOreNameExist("ingot" + entry[0]))
					CCLog.warn("This is because your oreDictionary Entry does not have an ingot for " + entry[0] + ".");
			}
		}

		for (int i = 0; i < dustNames.size(); i++)
		{
			ItemStack ingot = null;
			if (doesOreNameExist("ingot" + dustNames.get(i)))
				ingot = OreDictionary.getOres("ingot" + dustNames.get(i)).get(0);
			if (ingot != null)
				GameRegistry.addSmelting(new ItemStack(LibItems.dusts, 1, i), ingot, 0.0F);
		}
	}

	static ItemStack getEnchantedBook(int id)
	{
		ItemStack retBook = new ItemStack(Items.enchanted_book);
		Items.enchanted_book.addEnchantment(retBook, new EnchantmentData(Enchantment.getEnchantmentById(id), 1));

		return retBook;
	}

	static ItemStack getEnchantedBook(Enchantment enchantment)
	{
		ItemStack retBook = new ItemStack(Items.enchanted_book);
		Items.enchanted_book.addEnchantment(retBook, new EnchantmentData(enchantment, 1));

		return retBook;
	}

	static boolean doesOreNameExist(String oreName)
	{
		List<ItemStack> ores = OreDictionary.getOres(oreName);
		return ores.size() > 0 ? true : false;
	}
}