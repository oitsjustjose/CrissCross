package com.oitsjustjose.criss_cross.util;

import com.oitsjustjose.criss_cross.blocks.CCBlocks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CCTab extends CreativeTabs
{
	public CCTab()
	{
		super(CreativeTabs.getNextID(), "tab" + Lib.modid);
	}

	@Override
	public Item getTabIconItem()
	{
		return Item.getItemFromBlock(CCBlocks.electroextractor);
	}

}
