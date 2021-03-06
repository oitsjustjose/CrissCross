package com.oitsjustjose.criss_cross.container;

import com.oitsjustjose.criss_cross.tileentity.TileScribe;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerScribe extends Container
{
	TileScribe scribe;
	EntityPlayer player;

	private int lastUseTime;
	private int fuelTime;
	private int lastFuelTime;

	public ContainerScribe(EntityPlayer player, TileScribe scribe)
	{
		this.player = player;
		this.scribe = scribe;

		lastUseTime = 0;
		fuelTime = 0;
		lastFuelTime = 0;

		this.addSlotToContainer(new Slot(scribe, 0, 120, 47)); // input
		this.addSlotToContainer(new Slot(scribe, 1, 40, 47)); // book
		this.addSlotToContainer(new MachineOutputSlot(player, scribe, 2, 80, 25));
		this.addInventorySlots();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int timeType1, int timeType2)
	{
		switch (timeType1)
		{
		case 0:
			this.scribe.processTime = timeType2;
			break;
		case 1:
			this.scribe.bookTime = timeType2;
			break;
		case 2:
			this.scribe.currentBookBuffer = timeType2;
			break;
		}
	}

	@Override
	public void onCraftGuiOpened(ICrafting crafting)
	{
		super.onCraftGuiOpened(crafting);
		crafting.sendProgressBarUpdate(this, 0, this.scribe.processTime);
		crafting.sendProgressBarUpdate(this, 1, this.scribe.bookTime);
		crafting.sendProgressBarUpdate(this, 2, this.scribe.currentBookBuffer);
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for (int i = 0; i < this.crafters.size(); ++i)
		{
			ICrafting crafting = this.crafters.get(i);

			if (this.lastUseTime != this.scribe.processTime)
				crafting.sendProgressBarUpdate(this, 0, this.scribe.processTime);
			if (this.fuelTime != this.scribe.bookTime)
				crafting.sendProgressBarUpdate(this, 1, this.scribe.bookTime);
			if (this.lastFuelTime != this.scribe.currentBookBuffer)
				crafting.sendProgressBarUpdate(this, 2, this.scribe.currentBookBuffer);
		}
		this.lastUseTime = this.scribe.processTime;
		this.fuelTime = this.scribe.bookTime;
		this.lastFuelTime = this.scribe.currentBookBuffer;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID)
	{
		ItemStack itemstack = null;
		Slot slot = this.inventorySlots.get(slotID);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (slotID == 2)
			{
				if (!this.mergeItemStack(itemstack1, 3, 39, true))
					return null;

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (slotID != 1 && slotID != 0)
			{
				if (TileScribe.isValidForScribe(itemstack1))
				{
					if (!this.mergeItemStack(itemstack1, 0, 1, false))
						return null;
				}
				else if (TileScribe.isBook(itemstack1))
				{
					if (!this.mergeItemStack(itemstack1, 1, 2, false))
						return null;
				}
				else if (slotID >= 3 && slotID < 30)
				{
					if (!this.mergeItemStack(itemstack1, 30, 39, false))
						return null;
				}
				else if (slotID >= 30 && slotID < 39 && !this.mergeItemStack(itemstack1, 3, 30, false))
					return null;
			}
			else if (!this.mergeItemStack(itemstack1, 3, 39, false))
				return null;

			if (itemstack1.stackSize == 0)
				slot.putStack((ItemStack) null);
			else
				slot.onSlotChanged();

			if (itemstack1.stackSize == itemstack.stackSize)
				return null;

			slot.onPickupFromSlot(player, itemstack1);
		}

		return itemstack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return this.scribe.isUseableByPlayer(this.player);
	}

	public final void addInventorySlots()
	{
		this.addInventorySlots(8, 84, 142);
	}

	public final void addInventorySlots(int xOffset, int yOffset, int yHotbar)
	{
		InventoryPlayer inventory = this.player.inventory;

		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 9; ++j)
				this.addSlotToContainer(new Slot(inventory, 9 + j + i * 9, xOffset + j * 18, yOffset + i * 18));

		for (int i = 0; i < 9; ++i)
			this.addSlotToContainer(new Slot(inventory, i, xOffset + i * 18, yHotbar));
	}
}
