package com.oitsjustjose.criss_cross.Items;

import com.oitsjustjose.criss_cross.Recipes.ForgingHammerRecipes;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

public class CCItems
{
	public static Item dusts;
	public static Item magicBucket;
	public static Item forgingHammer;
		
	public static void init()
	{
		dusts = new ItemDust();
		magicBucket = new ItemMagicBuckets();
		forgingHammer = new ItemForgingHammer();
		ForgingHammerRecipes.init();
	}
}