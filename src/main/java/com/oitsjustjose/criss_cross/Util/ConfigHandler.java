package com.oitsjustjose.criss_cross.Util;

import java.io.File;
import java.util.regex.Pattern;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;

public class ConfigHandler
{
	public static Configuration config;
	public static int cropomatorProcessTime;
	public static int cropomatorOutput;
	public static String cropomatorRecipeItem;
	public static String[] cropomatorInputs;
	public static String[] cropomatorCatalysts;
	public static String[] defaultCropomatorInputs = new String[]
	{ "minecraft:apple", "minecraft:reeds", "minecraft:nether_wart", "minecraft:carrot", "minecraft:cactus",
			"minecraft:potato", "minecraft:sapling:0", "minecraft:sapling:1", "minecraft:sapling:2",
			"minecraft:sapling:3", "minecraft:sapling:4", "minecraft:sapling:5", "minecraft:melon",
			"minecraft:brown_mushroom", "minecraft:red_mushroom", "minecraft:cocoa", "minecraft:pumpkin",
			"minecraft:wheat", "minecraft:wheat_seeds" };
	public static String[] defaultCropomatorCatalystItems = new String[]
	{ "minecraft:dye:15" };

	public static int electroextractorProcessTime;
	public static int electroextractorOutput;
	public static String electroextractorRecipeItem;
	public static String[] electroextractorEnergySources;
	public static String[] electroextractorOreDictInputs;
	public static String[] defaultElectroextractorOreDictInputs = new String[]
	{ "Iron:12428902", "Gold:15400704", "Aluminum:15987699", "Cobalt:17663", "Ardite:13938485", "Copper:16751872",
			"Tin:15132390", "Silver:13297640", "Lead:5722979", "Platinum:11067903", "Mithril:4433113",
			"Nickel:16580553" };
	public static String[] defaultElectroextractorEnergySources = new String[]
	{ "minecraft:redstone_block" };

	public static int woodchipperProcessTime;
	public static int woodchipperOutput;
	public static String woodchipperRecipeItem;
	public static String[] woodchipperFuels;
	public static String[] defaultWoodchipperFuels = new String[]
	{ "minecraft:water_bucket", "minecraft:potion:0" };

	public static void init(File configFile)
	{
		// Create the configuration object from the given configuration file
		if (config == null)
		{
			config = new Configuration(configFile);
			loadConfiguration();
		}
	}

	private static void loadConfiguration()
	{
		// Cropomator Configs
		cropomatorInputs = config
				.get(config.CATEGORY_GENERAL, "Cropomator's Recipe Items.", defaultCropomatorInputs,
						"A list of items to be added to the Cropomator's recipe list. Formatted as: <modid>:<item>:<meta> or <modid>:<item>.")
				.getStringList();

		cropomatorCatalysts = config
				.get(config.CATEGORY_GENERAL, "Cropomator's Catalyst Items.", defaultCropomatorCatalystItems,
						"A list of items to be added to the Cropomator's catalyst list. Formatted as: <modid>:<item>:<meta> or <modid>:<item>.")
				.getStringList();

		cropomatorProcessTime = config.getInt("Cropomator Processing Time", config.CATEGORY_GENERAL, 150, 1,
				Short.MAX_VALUE,
				"Control how many ticks it takes the Cropomator to process an item. Default is 150; furnaces are 200.");

		cropomatorOutput = config.getInt("Cropomator Output Quantity", config.CATEGORY_GENERAL, 3, 0, 64,
				"Control how many of the input item you get as the output item. Default is 3.");

		cropomatorRecipeItem = config.getString("Cropomator Crafting Item.", config.CATEGORY_GENERAL,
				"minecraft:diamond_block",
				"This is the item located in the center of the crafting recipe for the Cropomator. Formatted as: <modid>:<item>:<meta> or <modid>:<item>.");

		// Electroextractor's Configs
		electroextractorEnergySources = config
				.get(config.CATEGORY_GENERAL, "Electroextractor's Fuel Items.", defaultElectroextractorEnergySources,
						"A list of items to be added as the Electroextractor's energy sources. Formatted as: <modid>:<item>:<meta> or <modid>:<item>.")
				.getStringList();

		electroextractorProcessTime = config.getInt("Electroextractor Processing Time", config.CATEGORY_GENERAL, 250, 0,
				Short.MAX_VALUE,
				"Control how many ticks it takes the Electroextractor to process an item. Default is 250; furnaces are 200.");

		electroextractorOutput = config.getInt("Electroextractor Output Quantity", config.CATEGORY_GENERAL, 2, 0, 64,
				"Control how many dusts you get as the output. Default is 2.");

		electroextractorRecipeItem = config.getString("Electroextractor Crafting Item.", config.CATEGORY_GENERAL,
				"minecraft:diamond_block",
				"This is the item located in the center of the crafting recipe for the Electroextractor. Formatted as: <modid>:<item>:<meta> or <modid>:<item>.");

		electroextractorOreDictInputs = config
				.get(config.CATEGORY_GENERAL, "Electroextractor OreDictionary Entries.",
						defaultElectroextractorOreDictInputs,
						"Formatted as <OreDictName>:<DecimalColorYouWant>." + "\n"
								+ " Remove the 'ore' part of the oredict name (i.e. 'oreIron' should be entered as 'Iron')"
								+ "\n"
								+ "Use http://www.colorpicker.com/ to find a color and http://bit.ly/1RhPhcX to convert it from Hex to Decimal.")
				.getStringList();

		// Woodchipper's Configs
		woodchipperFuels = config
				.get(config.CATEGORY_GENERAL, "Woodchipper Fuel Items", defaultWoodchipperFuels,
						"A list of items to be added as the Woodchipper's 'fuels'. Formatted as: <modid>:<item>:<meta> or <modid>:<item>.")
				.getStringList();

		woodchipperProcessTime = config.getInt("Woodchipper Process Time", config.CATEGORY_GENERAL, 400, 0,
				Short.MAX_VALUE,
				"Control how many ticks it takes the Woodchipper to process an item. Default is 400; furnaces are 200.");

		woodchipperOutput = config.getInt("Woodchipper Output Quantity", config.CATEGORY_GENERAL, 6, 0, 64,
				"Control how many planks are yielded from processing a log. Default is 6.");

		woodchipperRecipeItem = config.getString("Woodchipper Crafting Item", config.CATEGORY_GENERAL,
				"minecraft:diamond_block",
				"This is the item located in the center of the crafting recipe for the Woodchipper. Formatted as: <modid>:<item>:<meta> or <modid>:<item>.");

		if (config.hasChanged())
			config.save();
	}

	@SubscribeEvent
	public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if (event.modID.equalsIgnoreCase(Reference.modid))
			loadConfiguration();
	}
}
