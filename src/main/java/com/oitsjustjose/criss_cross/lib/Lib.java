package com.oitsjustjose.criss_cross.lib;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class Lib
{
	public static final String MODID = "CrissCross";
	public static final String NAME = "CrissCross";
	public static final String VERSION = "@VERSION@";
	public static final String GUI_FACTORY = "com.oitsjustjose.criss_cross.gui.ConfigGUI$GUIFactory";
	public static final String CLIENT_PROXY = "com.oitsjustjose.criss_cross.util.ClientProxy";
	public static final String COMMON_PROXY = "com.oitsjustjose.criss_cross.util.CommonProxy";
	
	public static final String CROPOMATOR_UID = MODID + ":Cropomator";
	public static final String ELECTROEXTRACTOR_UID = MODID + ":Electroextractor";
	public static final String WOODCHIPPER_UID = MODID + ":Woodchipper";
	public static final String SCRIBE_UID = MODID + ":Scribe";
	
	private static final ArrayList<Item> allItems = new ArrayList<Item>();
	private static final ArrayList<Block> allBlocks = new ArrayList<Block>();

	public static ArrayList<Item> getModItems()
	{
		return allItems;
	}

	public static ArrayList<Block> getModBlocks()
	{
		return allBlocks;
	}

	public static void add(Item item)
	{
		allItems.add(item);
	}

	public static void add(Block block)
	{
		allBlocks.add(block);
	}
}