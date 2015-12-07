package com.oitsjustjose.criss_cross.items;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

public class CCItems
{
	public static Item dusts;
	public static Item buckets;
	public static Item infiniApple;
	public static Item mantleSmasherMkI;
	public static Item mantleSmasherMkII;
	
	public static ToolMaterial mkI;
	public static ToolMaterial mkII;

	public static void init()
	{
		mkI = EnumHelper.addToolMaterial("mkI", 7, 6244, 8.0F, 0.0F, 22);
		mkI.setRepairItem(new ItemStack(Blocks.emerald_block));
		mkII = EnumHelper.addToolMaterial("mkII", 10, -1, 12.0F, 0.0F, 32);

		dusts = new ItemDust();
		buckets = new ItemModBuckets();
		infiniApple = new ItemInfiniApple();
		mantleSmasherMkI = new ItemMantleSmasher(mkI);
		mantleSmasherMkII = new ItemMantleSmasher(mkII);
	}
}