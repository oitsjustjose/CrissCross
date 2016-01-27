package com.oitsjustjose.criss_cross.lib;

import com.oitsjustjose.criss_cross.items.ItemDust;
import com.oitsjustjose.criss_cross.items.ItemInfiniApple;
import com.oitsjustjose.criss_cross.items.ItemMantleSmasher;
import com.oitsjustjose.criss_cross.items.ItemModBuckets;
import com.oitsjustjose.criss_cross.items.ItemPouch;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class LibItems
{
	public static Item dusts;
	public static Item buckets;
	public static Item infiniApple;
	public static Item pouch;
	public static Item mantleSmasherMkI;
	public static Item mantleSmasherMkII;

	public static ToolMaterial mkI;
	public static ToolMaterial mkII;

	public static void init()
	{
		mkI = EnumHelper.addToolMaterial("mkI", 7, 6244, 8.0F, -2.0F, 10);
		mkI.setRepairItem(new ItemStack(Blocks.emerald_block));
		mkII = EnumHelper.addToolMaterial("mkII", 10, -1, 12.0F, -2.0F, 32);

		dusts = new ItemDust();
		buckets = new ItemModBuckets();
		infiniApple = new ItemInfiniApple();
		pouch = new ItemPouch();

		mantleSmasherMkI = new ItemMantleSmasher(mkI);
		mantleSmasherMkII = new ItemMantleSmasher(mkII);
		
		FluidContainerRegistry.registerFluidContainer(new FluidStack(FluidRegistry.WATER, 4000), new ItemStack(buckets, 1, 0), new ItemStack(buckets, 1, 0));
	}
}