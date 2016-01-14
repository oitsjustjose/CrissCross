package com.oitsjustjose.criss_cross.event;

import com.oitsjustjose.criss_cross.items.ItemPouch;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PouchCleanEvent
{
	@SubscribeEvent
	public void registerEvent(PlayerInteractEvent event)
	{
		if (event.action != Action.RIGHT_CLICK_BLOCK || event.world.getBlockState(event.pos) == null)
			return;
		if (event.entityPlayer.getHeldItem() == null || !(event.entityPlayer.getHeldItem().getItem() instanceof ItemPouch))
			return;

		EntityPlayer player = event.entityPlayer;
		ItemPouch pouch = (ItemPouch) player.getHeldItem().getItem();
		World world = event.world;

		if (world.getBlockState(event.pos).getBlock() != Blocks.cauldron || event.world.getBlockState(event.pos) == Blocks.cauldron.getStateFromMeta(0))
			return;
		else if (pouch.hasColor(player.getHeldItem()))
		{
			pouch.removeColor(player.getHeldItem());
			world.setBlockState(event.pos, Blocks.cauldron.getStateFromMeta(Blocks.cauldron.getMetaFromState(world.getBlockState(event.pos)) - 1));
		}

	}
}