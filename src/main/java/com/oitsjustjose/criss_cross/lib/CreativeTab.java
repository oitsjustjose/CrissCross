package com.oitsjustjose.criss_cross.lib;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTab extends CreativeTabs
{
	public CreativeTab()
	{
		super(CreativeTabs.getNextID(), "tab" + Lib.MODID);
	}

	@Override
	public Item getTabIconItem()
	{
		return Item.getItemFromBlock(LibBlocks.electroextractor);
	}

}
