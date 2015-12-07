package com.oitsjustjose.criss_cross.util;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class Lib
{
	public static final String modid = "CrissCross";
	public static final String name = "Criss Cross";
	public static final String version = "1.3";
	public static final String guifactory = "com.oitsjustjose.criss_cross.gui.GUIFactory";
	
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