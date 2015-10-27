package com.oitsjustjose.criss_cross.Util;

import java.util.ArrayList;

import com.oitsjustjose.criss_cross.Items.CCItems;
import com.oitsjustjose.criss_cross.Items.ItemDust;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryAdder
{
	public static void init()
	{
		ArrayList<String> dustNames = ItemDust.getDusts();
		
		for(int i = 0; i < dustNames.size(); i++)
			OreDictionary.registerOre("dust" + dustNames.get(i), new ItemStack(CCItems.dusts, 1, i));
	}
}