package com.oitsjustjose.criss_cross.event;

import com.oitsjustjose.criss_cross.items.CCItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.FoodStats;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class InfiniAppleEvent
{
	@SubscribeEvent
	public void registerEvent(PlayerEvent event)
	{
		if (event.entityPlayer == null)
			return;
		EntityPlayer player = event.entityPlayer;

		if (player.inventory.hasItem(CCItems.infiniApple))
		{
			if (player.getFoodStats() != null)
			{
				player.getFoodStats().setFoodLevel(20);
				player.getFoodStats().setFoodSaturationLevel(5.0F);
			}
		}
	}
}