package com.oitsjustjose.criss_cross.lib;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class Lib
{
	public static final String modid = "CrissCross";
	public static final String name = "CrissCross";
	public static final String version = "@VERSION@";
	public static final String guifactory = "com.oitsjustjose.criss_cross.gui.ConfigGUI$GUIFactory";
	public static final String clientProxy = "com.oitsjustjose.criss_cross.util.ClientProxy";
	public static final String commonProxy = "com.oitsjustjose.criss_cross.util.CommonProxy";

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