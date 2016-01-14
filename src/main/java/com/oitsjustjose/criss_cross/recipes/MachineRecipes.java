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
import com.oitsjustjose.criss_cross.tileentity.TileCobblegen;
import com.oitsjustjose.criss_cross.tileentity.TileCropomator;
import com.oitsjustjose.criss_cross.tileentity.TileElectroextractor;
import com.oitsjustjose.criss_cross.tileentity.TileStonegen;
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
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class MachineRecipes
{
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

		ElectroextractorRecipes.getInstance().addRecipe(new ItemStack(Blocks.diamond_ore), new ItemStack(Items.diamond, Config.eeOutput));
		ElectroextractorRecipes.getInstance().addRecipe(new ItemStack(Blocks.emerald_ore), new ItemStack(Items.emerald, Config.eeOutput));
		ElectroextractorRecipes.getInstance().addRecipe(new ItemStack(Blocks.coal_ore), new ItemStack(Items.coal, Config.eeOutput));
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

	public static void initCropomatorRecipes()
	{
		for (int i = 0; i < Config.cropomatorInputs.length; i++)
			try
			{
				String[] parts = Config.cropomatorInputs[i].split(":");
				if (parts.length == 2)
					if (Config.findItemStack(parts[0], parts[1]) != null)
					{
						ItemStack newStack = Config.findItemStack(parts[0], parts[1]);
						CropomatorRecipes.getInstance().addRecipe(newStack, new ItemStack(newStack.getItem(), Config.cropomatorOutput, newStack.getItemDamage()));
					}
					else
					{
						LogHelper.warn("Item " + Config.cropomatorInputs[i] + " could not be added to the Cropomator's recipe list.");
						LogHelper.warn("Please confirm you have the name and formatting correct.");

					}
				if (parts.length == 3)
					if (Config.findItemStack(parts[0], parts[1]) != null)
					{
						ItemStack newStack = new ItemStack(Config.findItemStack(parts[0], parts[1]).getItem(), 1, Integer.parseInt(parts[2]));
						newStack.stackSize = Config.cropomatorOutput;
						CropomatorRecipes.getInstance().addRecipe(newStack, new ItemStack(newStack.getItem(), Config.cropomatorOutput, newStack.getItemDamage()));
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

	public static void initCropomatorFuels()
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

	// All Credit goes to TeamCoFH for the code. After so many algorithms
	// attempted, I gave up and used one that worked:

	public static void initWoodchipperOreDictionary()
	{
		Container local1 = new Container()
		{
			@Override
			public boolean canInteractWith(EntityPlayer p)
			{
				return false;
			}
		};
		InventoryCrafting localInventoryCrafting = new InventoryCrafting(local1, 3, 3);
		for (int i = 0; i < 9; i++)
			localInventoryCrafting.setInventorySlotContents(i, null);
		List<ItemStack> localArrayList = OreDictionary.getOres("logWood");
		for (int j = 0; j < localArrayList.size(); j++)
		{
			ItemStack localItemStack1 = localArrayList.get(j);
			ItemStack localItemStack3;
			ItemStack localItemStack4;
			if (ItemHelper.getItemDamage(localItemStack1) == 32767)
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
						tmp129_127.stackSize = Config.woodchipperOutput;
						WoodchipperRecipes.getInstance().addRecipe(localItemStack3, localItemStack5);
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
					temp.stackSize = Config.woodchipperOutput;
					WoodchipperRecipes.getInstance().addRecipe(localItemStack2, localItemStack4);
				}
			}
		}
	}

	public static void initWoodchipperFuels()
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

	static void initElectroextractorFuels()
	{
		for (int i = 0; i < Config.eeFuels.length; i++)
			try
			{
				String[] parts = Config.eeFuels[i].split(":");
				if (parts.length == 2)
					if (Config.findItemStack(parts[0], parts[1]) != null)
						TileElectroextractor.addFuel(Config.findItemStack(parts[0], parts[1]));
					else
					{
						LogHelper.warn("Item " + Config.eeFuels[i] + " could not be added to the Electroextractor's fuel list.");
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
						LogHelper.warn("Item " + Config.eeFuels[i] + " could not be added to the Electroextractor's fuel list.");
						LogHelper.warn("Please confirm you have the name and formatting correct.");
					}
			}
			catch (Exception e)
			{
				LogHelper.warn("Error reading itemstack for electroextractor's energy sources at item: " + (i + 1));
			}
	}

	static void initElectroextractorOreDictionary()
	{
		ArrayList<String> dustNames = ItemDust.getDusts();
		for (int i = 0; i < Config.eeOreDictInputs.length; i++)
		{
			String[] entry = Config.eeOreDictInputs[i].split(":");
			if (doesOreNameExist("ore" + entry[0]) && doesOreNameExist("ingot" + entry[0]))
			{
				List<ItemStack> ores = OreDictionary.getOres("ore" + entry[0]);
				ItemDust.addDustType(entry[0], Integer.parseInt(entry[1]));
				for (int j = 0; j < ores.size(); j++)
					ElectroextractorRecipes.getInstance().addRecipe(ores.get(j), new ItemStack(LibItems.dusts, Config.eeOutput, dustNames.size() - 1));
			}
			else
			{
				LogHelper.warn("Your oreDictionary Entry " + Config.eeOreDictInputs[i] + " could not be registered.");
				if (!doesOreNameExist("ore" + entry[0]))
					LogHelper.warn("This is because your oreDictionary Entry was not valid or registered as " + entry[0] + ".");
				else if (!doesOreNameExist("ingot" + entry[0]))
					LogHelper.warn("This is because your oreDictionary Entry does not have an ingot for " + entry[0] + ".");
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

	static boolean doesOreNameExist(String oreName)
	{
		List<ItemStack> ores = OreDictionary.getOres(oreName);
		return ores.size() > 0 ? true : false;
	}
}