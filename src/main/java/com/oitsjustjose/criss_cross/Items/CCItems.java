package com.oitsjustjose.criss_cross.Items;

import net.minecraft.item.Item;

public class CCItems
{
	public static Item dusts;
	public static Item magicBucket;
		
	public static void init()
	{
		dusts = new ItemDust();
		magicBucket = new ItemMagicBuckets();
	}
}