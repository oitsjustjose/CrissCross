package com.oitsjustjose.criss_cross.lib;

import java.io.File;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Config
{
	public static Configuration config;
	public static ConfigCategory Cropomator;
	public static ConfigCategory Electroextractor;
	public static ConfigCategory Woodchipper;
	public static ConfigCategory OtherMachines;
	// Cropomator Configs
	public static int cropomatorProcessTime;
	public static int cropomatorOutput;
	public static String cropomatorRecipeItem;
	public static String[] cropomatorInputs;
	public static String[] cropomatorCatalysts;
	public static String[] defaultCropomatorInputs = new String[] { "minecraft:apple", "minecraft:reeds", "minecraft:nether_wart", "minecraft:carrot", "minecraft:cactus", "minecraft:potato", "minecraft:sapling:0", "minecraft:sapling:1", "minecraft:sapling:2", "minecraft:sapling:3", "minecraft:sapling:4", "minecraft:sapling:5", "minecraft:melon", "minecraft:brown_mushroom", "minecraft:red_mushroom", "minecraft:dye:3", "minecraft:pumpkin", "minecraft:wheat", "minecraft:wheat_seeds" };
	public static String[] defaultCropomatorCatalysts = new String[] { "minecraft:dye:15" };
	// Electroextractor Configs
	public static int electroextractorProcessTime;
	public static int eeOutput;
	public static String eeRecipeItem;
	public static String[] eeFuels;
	public static String[] eeOreDictInputs;
	public static String[] defaultEEOreDictInputs = new String[] { "Iron:12428902", "Gold:16772608", "Aluminum:15987699", "Cobalt:17663", "Ardite:13938485", "Copper:16751872", "Tin:15132390", "Silver:13297640", "Lead:5722979", "Platinum:11067903", "Mithril:4433113", "Nickel:16580553" };
	public static String[] defaultEEFuels = new String[] { "minecraft:redstone_block" };
	// Woodchipper Configs
	public static int woodchipperProcessTime;
	public static int woodchipperOutput;
	public static String woodchipperRecipeItem;
	public static String[] woodchipperFuels;
	public static String[] defaultWoodchipperFuels = new String[] { "minecraft:water_bucket", "minecraft:potion:0", "crisscross:special_bucket:0" };
	// Scribe Configs
	public static String[] scribeRecipes;
	public static String[] defaultScribeRecipes = new String[] { "minecraft:fish:3*16=6", "minecraft:spider_eye:0*8=18", "minecraft:obsidian:0*8=3", "minecraft:prismarine_shard:0*16=8", "minecraft:feather:0*32=2", "minecraft:blaze_rod:0*8=20", "minecraft:blaze_powder:0*16=50", "minecraft:piston:0*4=19", "minecraft:prismarine_crystals:0*16=61", "minecraft:fish:0*16=62", "minecraft:potion:8269*1=5", "minecraft:slime:0*8=33", "minecraft:rotten_flesh:0*48=17", "minecraft:double_plant:4*64=7", "ingotIron*24=34", "ingotGold*12=49", "gemQuartz*16=48", "gemDiamond*8=51", "gemEmerald*3=21", "logWood*16=4", "blockIron*4=0", "blockQuartz*16=16", "blockLapis*12=35", "blockRedstone*8=32" };
	// Block Generator Configs (stone and cobble)
	public static int blockGeneratorProcessTimes;

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
		Property property;

		// Cropomator
		String category = "cropomator";
		List<String> propertyOrder = Lists.newArrayList();
		Cropomator = config.getCategory(category);
		config.setCategoryRequiresMcRestart(category, true);

		property = config.get(category, "Cropomator Process Time", 150, "Amount of time for the Cropomator to complete a process", 20, Short.MAX_VALUE);
		cropomatorProcessTime = property.getInt();
		propertyOrder.add(property.getName());

		property = config.get(category, "Cropomator Recipe Items", defaultCropomatorInputs, "Items to be processed by the Cropomator. Formatted as: <modid>:<item>:<meta> or <modid>:<item>.");
		cropomatorInputs = property.getStringList();
		propertyOrder.add(property.getName());

		property = config.get(category, "Cropomator Catalyst Items", defaultCropomatorCatalysts, "Items considered as catalysts by the Cropomator. Formatted as: <modid>:<item>:<meta> or <modid>:<item>.");
		cropomatorCatalysts = property.getStringList();
		propertyOrder.add(property.getName());

		property = config.get(category, "Cropomator Output Quantity", 3);
		property.comment = "Quantity output by the Cropomator";
		cropomatorOutput = property.getInt();
		propertyOrder.add(property.getName());

		property = config.get(category, "Cropomator Crafting Item", "minecraft:diamond_block");
		property.comment = "Key crafting ingredient in Cropomator's Recipe. Formatted as: <modid>:<item>:<meta> or <modid>:<item>.";
		cropomatorRecipeItem = property.getString();
		propertyOrder.add(property.getName());

		Cropomator.setPropertyOrder(propertyOrder);

		// ElectroExtractor
		category = "electroextractor";
		propertyOrder = Lists.newArrayList();
		Electroextractor = config.getCategory(category);
		config.setCategoryRequiresMcRestart(category, true);

		property = config.get(category, "Electroextractor Process Time", 250, "Amount of time for the Electroextractor to complete a process", 20, Short.MAX_VALUE);
		electroextractorProcessTime = property.getInt();
		propertyOrder.add(property.getName());

		property = config.get(category, "Electroextractor OreDictionary Entries", defaultEEOreDictInputs, "Formatted as <OreDictName>:<DecimalColorYouWant>." + "\n" + " Remove the 'ore' part of the oredict name (i.e. 'oreIron' should be entered as 'Iron')" + "\n" + "Use http://www.colorpicker.com/ to find a color and http://bit.ly/1RhPhcX to convert it from Hex to Decimal.");
		eeOreDictInputs = property.getStringList();
		propertyOrder.add(property.getName());
		
		property = config.get(category, "Electroextractor's Fuel Items", defaultEEFuels, "Items considered as fuel for the Electroextractor. Formatted as: <modid>:<item>:<meta> or <modid>:<item>.");
		eeFuels = property.getStringList();
		propertyOrder.add(property.getName());
		
		property = config.get(category, "Electroextractor Output Quantity", 2);
		property.comment = "Quantity output by the Electroextractor";
		eeOutput = property.getInt();
		propertyOrder.add(property.getName());
		
		property = config.get(category, "Electroextractor Crafting Item", "minecraft:diamond_block");
		property.comment = "Key crafting ingredient in Electroextractor's Recipe. Formatted as: <modid>:<item>:<meta> or <modid>:<item>.";
		eeRecipeItem = property.getString();
		propertyOrder.add(property.getName());
		
		Electroextractor.setPropertyOrder(propertyOrder);
		
		category = "woodchipper";
		propertyOrder = Lists.newArrayList();
		Woodchipper = config.getCategory(category);
		config.setCategoryRequiresMcRestart(category, true);
		
		property = config.get(category, "Woodchipper Process Time", 400, "Amount of time for the Woodchipper to complete a process", 20, Short.MAX_VALUE);
		woodchipperProcessTime = property.getInt();
		propertyOrder.add(property.getName());

		property = config.get(category, "Woodchipper Fuel Items", defaultWoodchipperFuels, "Items considered as fuel for the Woodchipper. Formatted as: <modid>:<item>:<meta> or <modid>:<item>.");
		woodchipperFuels = property.getStringList();
		propertyOrder.add(property.getName());
		
		property = config.get(category, "Woodchipper Output Quantity", 6, "Amount of planks yielded from a log", 0, 64);
		woodchipperOutput = property.getInt();
		propertyOrder.add(property.getName());
		
		property = config.get(category, "Woodchipper Crafting Item", "minecraft:diamond_block");
		property.comment = "Key crafting ingredient in Woodchipper's Recipe. Formatted as: <modid>:<item>:<meta> or <modid>:<item>.";
		woodchipperRecipeItem = property.getString();
		propertyOrder.add(property.getName());
		
		Woodchipper.setPropertyOrder(propertyOrder);
	
		//Everything Else
		category = "other machines";
		propertyOrder = Lists.newArrayList();
		OtherMachines = config.getCategory(category);
		config.setCategoryRequiresMcRestart(category, true);
		
		property = config.get(category, "Scribe Recipes", defaultScribeRecipes, "Formatted as <modid>:<item>:<metadata>*<qty>=<enchantment ID>" + "\n" + "Also can be <oreDict>*qty=<enchantment ID>");
		scribeRecipes = property.getStringList();
		propertyOrder.add(property.getName());
		
		property = config.get(category, "Block Generators' Process Times", 20, "Amount of time for Stone and Cobble Generators to produce a block", 20, Short.MAX_VALUE);
		blockGeneratorProcessTimes = property.getInt();
		propertyOrder.add(property.getName());

		OtherMachines.setPropertyOrder(propertyOrder);
		


		if (config.hasChanged())
			config.save();

	}

	@SubscribeEvent
	public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if (event.modID.equalsIgnoreCase(Lib.MODID))
			loadConfiguration();
	}

	// put this here because most references to finditemstack are due to this confighandler
	public static ItemStack findItemStack(String modid, String name)
	{
		if (GameRegistry.findItem(modid, name) != null)
			return new ItemStack(GameRegistry.findItem(modid, name), 1);
		else if (GameRegistry.findBlock(modid, name) != null)
			if (Item.getItemFromBlock(GameRegistry.findBlock(modid, name)) != null)
				return new ItemStack(Item.getItemFromBlock(GameRegistry.findBlock(modid, name)), 1);

		return null;
	}
}
