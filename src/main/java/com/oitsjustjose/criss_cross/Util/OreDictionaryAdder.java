package com.oitsjustjose.criss_cross.util;

import java.util.ArrayList;

import com.oitsjustjose.criss_cross.items.CCItems;
import com.oitsjustjose.criss_cross.items.ItemDust;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryAdder
{
	public static void init()
	{
		ArrayList<String> dustNames = ItemDust.getDusts();

		for (int i = 0; i < dustNames.size(); i++)
			OreDictionary.registerOre("dust" + dustNames.get(i), new ItemStack(CCItems.dusts, 1, i));
	}
}