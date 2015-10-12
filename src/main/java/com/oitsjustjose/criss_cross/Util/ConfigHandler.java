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
    public static String[] electroextractorInputs;
    public static String[] electroextractorEnergySources;

    public static String[] defaultElectroextractorEnergySources = new String[]
    		{
    				"minecraft:redstone_block"
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
    			"Control how many of the input item you get as the output item. Default is 2.");
    	
    	electroextractorRecipeItem = config.getString("Key crafting item of the Electroextractor.", config.CATEGORY_GENERAL, "minecraft:diamond_block",
    			"This is the item located in the center of the crafting recipe for the Electroextractor. Formatted as: <modid>:<item>:<meta> or <modid>:<item>.");
    	
    	
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
