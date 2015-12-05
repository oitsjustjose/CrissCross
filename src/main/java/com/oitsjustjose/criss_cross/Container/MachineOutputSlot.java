package com.oitsjustjose.criss_cross.Container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class MachineOutputSlot extends Slot
{
	/** The player that is using the GUI where this slot resides. */
	private EntityPlayer thePlayer;
	private int temp;

	public MachineOutputSlot(EntityPlayer player, IInventory inventory, int x, int y, int z)
	{
		super(inventory, x, y, z);
		this.thePlayer = player;
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return false;
	}

	@Override
	public ItemStack decrStackSize(int slot)
	{
		if (this.getHasStack())
		{
			this.temp += Math.min(slot, this.getStack().stackSize);
		}

		return super.decrStackSize(slot);
	}

	@Override
	public void onPickupFromSlot(EntityPlayer player, ItemStack itemstack)
	{
		this.onCrafting(itemstack);
		super.onPickupFromSlot(player, itemstack);
	}

	@Override
	protected void onCrafting(ItemStack itemstack, int slot)
	{
		this.temp += slot;
		this.onCrafting(itemstack);
	}

	@Override
	protected void onCrafting(ItemStack stack)
	{
		stack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.temp);
		this.temp = 0;
	}
}