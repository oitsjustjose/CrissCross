package com.oitsjustjose.criss_cross.container;

import com.oitsjustjose.criss_cross.lib.LibItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerPouch extends Container
{
	private EntityPlayer player = null;
	private int currentFilter = -1;
	private ItemStack itemStack;

	public ContainerPouch(EntityPlayer player, int invId)
	{
		this.player = player;
		this.currentFilter = invId;

		int i = 36;
		for (int j = 0; j < 6; j++)
		{
			for (int k = 0; k < 9; k++)
			{
				addSlotToContainer(new PouchSlot(player.inventory, k + j * 9, 8 + k * 18, 18 + j * 18, this.currentFilter));
			}
		}
		for (int j = 0; j < 3; j++)
		{
			for (int k = 0; k < 9; k++)
			{
				addSlotToContainer(new Slot(player.inventory, k + j * 9 + 9, 8 + k * 18, 104 + j * 18 + i));
			}
		}
		for (int j = 0; j < 9; j++)
		{
			if (j == this.currentFilter)
			{
				addSlotToContainer(new PouchLockedSlot(player.inventory, j, 8 + j * 18, 162 + i));
			}
			else
			{
				addSlotToContainer(new Slot(player.inventory, j, 8 + j * 18, 162 + i));
			}
		}
	}

	protected void retrySlotClick(int par1, int par2, boolean par3, EntityPlayer par4EntityPlayer)
	{
	}



	public ItemStack slotClick(int par1, int par2, int par3, EntityPlayer par4EntityPlayer)
	{
		if ((par3 == 2) && (par2 == this.currentFilter))
		{
			return null;
		}
		ItemStack filter = this.player.inventory.getStackInSlot(this.currentFilter);
		if ((filter == null) || (filter.getItem() != LibItems.pouch))
		{
			return null;
		}
		return super.slotClick(par1, par2, par3, par4EntityPlayer);
	}

	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(par2);
		if ((slot != null) && (slot.getHasStack()))
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (par2 < 54)
			{
				if (!mergeItemStack(itemstack1, 54, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else if (!mergeItemStack(itemstack1, 0, 54, false))
			{
				return null;
			}
			if (itemstack1.stackSize == 0)
			{
				slot.putStack(null);
				return null;
			}
			slot.onSlotChanged();
		}
		return itemstack;
	}

	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		ItemStack filter = this.player.inventory.getStackInSlot(this.currentFilter);
		return (filter != null) && (filter.getItem() == LibItems.pouch);
	}
}
