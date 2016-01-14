package com.oitsjustjose.criss_cross.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class PouchSlot extends Slot
{
	static boolean checking = false;
	static boolean iterating = false;
	private static IInventory fakeInv = new InventoryBasic("fakeInventory", true, 54);
	int filterIndex = -1;
	ItemStack curStack = null;
	private IInventory filterInv;

	public PouchSlot(IInventory par1iInventory, int slot, int x, int y, int filterIndex)
	{
		super(fakeInv, slot, x, y);
		this.filterInv = par1iInventory;
		this.filterIndex = filterIndex;
	}

	@Override
	public ItemStack getStack()
	{
		ItemStack filter = this.filterInv.getStackInSlot(this.filterIndex);
		if ((filter != null) && (filter.getTagCompound() != null) && (filter.getTagCompound().hasKey("items_" + getSlotIndex())))
		{
			if (!checking)
			{
				this.curStack = ItemStack.loadItemStackFromNBT(filter.getTagCompound().getCompoundTag("items_" + getSlotIndex()));
				return this.curStack;
			}
			return ItemStack.loadItemStackFromNBT(filter.getTagCompound().getCompoundTag("items_" + getSlotIndex()));
		}
		if (!checking)
		{
			this.curStack = null;
			return null;
		}
		return null;
	}

	@Override
	public ItemStack decrStackSize(int par1)
	{
		ItemStack curItem = getStack();
		if (curItem != null)
		{
			if (curItem.stackSize <= par1)
			{
				ItemStack itemstack = curItem;
				putStack(null);
				return itemstack;
			}
			ItemStack itemstack = curItem.splitStack(par1);
			if (curItem.stackSize == 0)
				putStack(null);
			return itemstack;
		}
		return null;
	}

	@Override
	public boolean getHasStack()
	{
		return getStack() != null;
	}

	@Override
	public void putStack(ItemStack par1ItemStack)
	{
		ItemStack filter = this.filterInv.getStackInSlot(this.filterIndex);
		if (filter == null)
			return;
		NBTTagCompound tags = filter.getTagCompound();
		if (par1ItemStack != null)
		{
			if (tags == null)
				tags = new NBTTagCompound();
			if (tags.hasKey("items_" + getSlotIndex()))
				tags.removeTag("items_" + getSlotIndex());
			NBTTagCompound itemTags = new NBTTagCompound();
			par1ItemStack.writeToNBT(itemTags);
			tags.setTag("items_" + getSlotIndex(), itemTags);
			filter.setTagCompound(tags);
		}
		else if (tags != null)
		{
			tags.removeTag("items_" + getSlotIndex());
			if (tags.hasNoTags())
				filter.setTagCompound(null);
			else
				filter.setTagCompound(tags);
		}
		if (par1ItemStack != null)
			this.curStack = par1ItemStack;
		else
			this.curStack = null;
		if (!iterating)
			onSlotChanged();
	}

	@Override
	public void onSlotChanged()
	{
		checking = true;
		ItemStack oldItem = getStack();
		checking = false;
		if (!ItemStack.areItemStacksEqual(oldItem, this.curStack))
		{
			iterating = true;
			putStack(this.curStack);
			iterating = false;
		}
		this.filterInv.markDirty();
	}
}
