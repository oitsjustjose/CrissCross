package com.oitsjustjose.criss_cross.Util;

import com.oitsjustjose.criss_cross.Blocks.CCBlocks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CCTab extends CreativeTabs
{
	public CCTab()
	{
		super(CreativeTabs.getNextID(), "tab" + Reference.modid);
	}
	
	@Override
	public Item getTabIconItem()
	{
		return Item.getItemFromBlock(CCBlocks.electroextractor);
	}
	
}
