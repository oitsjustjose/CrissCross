package com.oitsjustjose.criss_cross.items;

import com.oitsjustjose.criss_cross.util.Lib;

import net.minecraft.item.Item;

public class CCItems
{
	public static Item dusts;
	public static Item buckets;

	public static void init()
	{
		dusts = new ItemDust();
		buckets = new ItemModBuckets();
	}
}