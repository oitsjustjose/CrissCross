package com.oitsjustjose.criss_cross.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class PouchLockedSlot extends Slot
{
	public PouchLockedSlot(IInventory par1iInventory, int par2, int par3, int par4)
	{
		super(par1iInventory, par2, par3, par4);
	}

	public boolean isItemValid(ItemStack par1ItemStack)
	{
		return false;
	}

	public boolean canTakeStack(EntityPlayer par1EntityPlayer)
	{
		return false;
	}
}
