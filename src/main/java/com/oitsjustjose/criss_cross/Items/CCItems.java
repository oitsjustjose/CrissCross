package com.oitsjustjose.criss_cross.Items;

import com.oitsjustjose.criss_cross.Util.Reference;

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