package com.oitsjustjose.criss_cross.event;

import com.oitsjustjose.criss_cross.items.ItemMantleSmasher;

import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ToolEfficiencyEvent
{
	@SubscribeEvent
	public void registerEvent(BreakSpeed event)
	{
		if (event.entityPlayer == null || event.entityPlayer.getHeldItem() == null)
			return;

		Material m = event.entityPlayer.worldObj.getBlockState(event.pos).getBlock().getMaterial();

		if (event.entityPlayer.getHeldItem().getItem() instanceof ItemMantleSmasher)
			if (m == Material.ground || m == Material.grass || m == Material.sand)
				event.newSpeed = event.originalSpeed * 5 + (5 * EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, event.entityPlayer.getHeldItem()));
	}
}
