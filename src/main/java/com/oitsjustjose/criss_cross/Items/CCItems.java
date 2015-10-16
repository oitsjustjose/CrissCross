package com.oitsjustjose.criss_cross.Items;

import net.minecraft.item.Item;

public class CCItems
{
	public static Item dusts;
	public static void init()
	{
		dusts = new ItemDust();
		ItemDust.addDustType("Iron", 10053171);
		ItemDust.addDustType("Gold", 13467442);
	}
}