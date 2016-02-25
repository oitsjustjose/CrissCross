package com.oitsjustjose.criss_cross.event;

import com.oitsjustjose.criss_cross.lib.LibItems;

import net.minecraft.entity.player.EntityPlayer;
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

		if (player.inventory.hasItem(LibItems.infiniApple))
			if (player.getFoodStats() != null)
				player.getFoodStats().setFoodLevel(20);
	}
}