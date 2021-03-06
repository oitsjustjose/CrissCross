package com.oitsjustjose.criss_cross.items;

import com.oitsjustjose.criss_cross.CrissCross;
import com.oitsjustjose.criss_cross.event.InfiniAppleEvent;
import com.oitsjustjose.criss_cross.lib.Lib;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemInfiniApple extends Item
{
	public ItemInfiniApple()
	{
		this.setMaxStackSize(1);
		this.setCreativeTab(CrissCross.CCTab);
		this.setUnlocalizedName(Lib.MODID + ".infini_apple");
		GameRegistry.registerItem(this, "infiniapple");
		MinecraftForge.EVENT_BUS.register(new InfiniAppleEvent());
		Lib.add(this);
	}
}
