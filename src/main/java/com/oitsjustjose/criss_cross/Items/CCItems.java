package com.oitsjustjose.criss_cross.Items;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

public class CCItems
{
	public static Item dusts;
	public static Item infiniBucket;
		
	public static void init()
	{
		dusts = new ItemDust();
		infiniBucket = new ItemInfibucket();
	}
}