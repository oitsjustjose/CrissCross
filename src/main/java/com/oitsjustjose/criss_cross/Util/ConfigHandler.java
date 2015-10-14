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
    		{
    		"minecraft:apple", "minecraft:reeds", "minecraft:nether_wart",
    		"minecraft:carrot", "minecraft:cactus", "minecraft:potato",
    		"minecraft:sapling:0", "minecraft:sapling:1", "minecraft:sapling:2",
    		"minecraft:sapling:3", "minecraft:sapling:4", "minecraft:sapling:5",
    		"minecraft:melon", "minecraft:brown_mushroom", "minecraft:red_mushroom",
    		"minecraft:cocoa", "minecraft:pumpkin", "minecraft:wheat",
    		"minecraft:wheat_seeds"
    		};
    public static String[] defaultCropomatorCatalystItems = new String[]
    		{
    			"minecraft:dye:15"	
    		};
    
    public static int electroextractorProcessTime;
    public static int electroextractorOutput;
    public static String electroextractorRecipeItem;
    public static String[] electroextractorEnergySources;

    public static String[] defaultElectroextractorEnergySources = new String[]
    		{
    				"minecraft:redstone_block"
    		};
    
    public static int woodchipperProcessTime;
    public static int woodchipperOutput;
    public static String woodchipperRecipeItem;
    public static String[] woodchipperFuels;
    public static String[] woodchipperLogs;
    public static String[] woodchipperPlanks;
    
    public static String[] defaultWoodchipperLogs = new String[]
    		{
    			"minecraft:log:0", "minecraft:log:1", "minecraft:log:2",
    			"minecraft:log:3", "minecraft:log2:0", "minecraft:log2:1"
    		};
    
    public static String[] defaultWoodchipperPlanks = new String[]
    		{
    			"minecraft:planks:0", "minecraft:planks:1", "minecraft:planks:2",
    			"minecraft:planks:3", "minecraft:planks:4", "minecraft:planks:5"
    		};
    
    public static String[] defaultWoodchipperFuels = new String[]
    		{
    			"minecraft:water_bucket", "minecraft:potion:0"
    		};
    
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
    	//Cropomator Configs
    	cropomatorInputs = config.get(config.CATEGORY_GENERAL, "Items to be added to the Cropomator's recipes.", defaultCropomatorInputs, 
    			"A list of items to be added to the Cropomator's recipe list. Formatted as: <modid>:<item>:<meta> or <modid>:<item>.").getStringList();
    	
    	cropomatorCatalysts = config.get(config.CATEGORY_GENERAL, "Items to be added to the Cropomator's Catalyst list.", defaultCropomatorCatalystItems, 
    			"A list of items to be added to the Cropomator's catalyst list. Formatted as: <modid>:<item>:<meta> or <modid>:<item>.").getStringList();
    	
    	cropomatorProcessTime = config.getInt("Cropomator Processing Time", config.CATEGORY_GENERAL, 150, 1, Short.MAX_VALUE,
    			"Control how many ticks it takes the Cropomator to process an item. Default is 150; furnaces are 200.");
    	
    	cropomatorOutput = config.getInt("Cropomator Output Quantity", config.CATEGORY_GENERAL, 3, 0, 64,
    			"Control how many of the input item you get as the output item. Default is 3.");
    	
    	cropomatorRecipeItem = config.getString("Key crafting item of the Cropomator.", config.CATEGORY_GENERAL, "minecraft:diamond_block",
    			"This is the item located in the center of the crafting recipe for the Cropomator. Formatted as: <modid>:<item>:<meta> or <modid>:<item>.");
    	
    	//Electroextractor's Configs
    	electroextractorEnergySources = config.get(config.CATEGORY_GENERAL, "Items to be added to the Electroextractor's energy sources.", defaultElectroextractorEnergySources,
    			"A list of items to be added as the Electroextractor's energy sources. Formatted as: <modid>:<item>:<meta> or <modid>:<item>.").getStringList();
    	
    	electroextractorProcessTime = config.getInt("Electroextractor Processing Time", config.CATEGORY_GENERAL, 250, 0, Short.MAX_VALUE, 
    			"Control how many ticks it takes the Electroextractor to process an item. Default is 250; furnaces are 200.");
    	
    	electroextractorOutput = config.getInt("Electroextractor Output Quantity", config.CATEGORY_GENERAL, 2, 0, 64, 
    			"Control how many dusts you get as the output. Default is 2.");
    	
    	electroextractorRecipeItem = config.getString("Key crafting item of the Electroextractor.", config.CATEGORY_GENERAL, "minecraft:diamond_block",
    			"This is the item located in the center of the crafting recipe for the Electroextractor. Formatted as: <modid>:<item>:<meta> or <modid>:<item>.");
    	
    	//Woodchipper's Configs
    	woodchipperFuels = config.get(config.CATEGORY_GENERAL, "Items to be added as the Woodchipper's 'fuels'", defaultWoodchipperFuels,
    			"A list of items to be added as the Woodchipper's 'fuels'. Formatted as: <modid>:<item>:<meta> or <modid>:<item>.").getStringList();
    	
    	woodchipperProcessTime = config.getInt("Woodchipper Process Time", config.CATEGORY_GENERAL, 400, 0, Short.MAX_VALUE, 
    			"Control how many ticks it takes the Woodchipper to process an item. Default is 400; furnaces are 200.");
    	
    	woodchipperOutput = config.getInt("Woodchipper Output Quantity", config.CATEGORY_GENERAL, 6, 0, 64, 
    			"Control how many planks are yielded from processing a log. Default is 6.");
    	
    	woodchipperRecipeItem = config.getString("Key crafting item of the Woodchipper", config.CATEGORY_GENERAL, "minecraft:diamond_block", 
     			"This is the item located in the center of the crafting recipe for the Woodchipper. Formatted as: <modid>:<item>:<meta> or <modid>:<item>.");
    	
    	woodchipperLogs = config.get(config.CATEGORY_GENERAL, "Items that the Woodchipper shall consider as logs.", defaultWoodchipperLogs, 
    			"These should 1:1 match what you put in the list of planks. So the first item on this list corresponds to the first item on the planks list, etc. etc." + "\n" + 
    			"Formatted as: <modid>:<item>:<meta> or <modid>:<item>.").getStringList();
    	
    	woodchipperPlanks = config.get(config.CATEGORY_GENERAL, "Items that the Woodchipper shall consider as planks.", defaultWoodchipperPlanks, 
    			"These should 1:1 match what you put in the list of logs. So the first item on this list corresponds to the first item on the logs list, etc. etc." + "\n" + 
    			"Formatted as: <modid>:<item>:<meta> or <modid>:<item>.").getStringList();
    	
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
