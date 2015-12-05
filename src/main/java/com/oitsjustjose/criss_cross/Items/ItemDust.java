package com.oitsjustjose.criss_cross.Items;

import java.util.ArrayList;
import java.util.List;

import com.oitsjustjose.criss_cross.CrissCross;
import com.oitsjustjose.criss_cross.Util.ClientProxy;
import com.oitsjustjose.criss_cross.Util.Reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDust extends Item
{
	private static ArrayList<String> dustNames = new ArrayList<String>();
	private static ArrayList<Integer> colors = new ArrayList<Integer>();

	public ItemDust()
	{
		this.setHasSubtypes(true);
		this.setCreativeTab(CrissCross.CCTab);
		GameRegistry.registerItem(this, "dust");
		Reference.add(this);
	}

	public static ArrayList<String> getDusts()
	{
		return dustNames;
	}

	public static void addDustType(String name, int color)
	{
		dustNames.add(name);
		colors.add(color);
	}

	public static String getName(int meta)
	{
		return dustNames.get(meta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass)
	{
		if (pass == 1)
		{
			if (stack.getItemDamage() >= colors.size())
				return 0;
			return colors.get(stack.getItemDamage());
		}
		else
			return 16777215;
	}

	@Override
	public String getUnlocalizedNameInefficiently(ItemStack itemstack)
	{
		int meta = itemstack.getItemDamage();
		return (StatCollector.translateToLocal("item.CrissCross.dustPrefix") + " " + ItemDust.getName(meta) + " " + StatCollector.translateToLocal(
				"item.CrissCross.dustPostfix"));
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack)
	{
		return this.getUnlocalizedNameInefficiently(itemstack);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemStack)
	{
		return "item." + Reference.modid + ".dust";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		for (int i = 0; i < dustNames.size(); i++)
		{
			ItemStack stack = new ItemStack(item, 1, i);
			list.add(stack);
		}
	}
}