package com.oitsjustjose.criss_cross.container;

import com.oitsjustjose.criss_cross.tileentity.TileCropomator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerCropomator extends Container
{
	TileCropomator cropomator;
	EntityPlayer player;

	private int lastUseTime;
	private int lastProcessTime;
	private int lastCatalystInUseTime;

	public ContainerCropomator(EntityPlayer player, TileCropomator cropomator)
	{
		this.player = player;
		this.cropomator = cropomator;

		lastUseTime = 0;
		lastProcessTime = 0;
		lastCatalystInUseTime = 0;

		this.addSlotToContainer(new Slot(cropomator, 0, 56, 17)); // input
		this.addSlotToContainer(new Slot(cropomator, 1, 56, 53)); // catalyst
		this.addSlotToContainer(new MachineOutputSlot(player, cropomator, 2, 116, 35));
		this.addInventorySlots();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int timeType1, int timeType2)
	{
		if (timeType1 == 0)
			this.cropomator.processTime = timeType2;

		if (timeType1 == 1)
			this.cropomator.catalystTime = timeType2;

		if (timeType1 == 2)
			this.cropomator.catalystInUseTime = timeType2;
	}

	@Override
	public void onCraftGuiOpened(ICrafting crafting)
	{
		super.onCraftGuiOpened(crafting);
		crafting.sendProgressBarUpdate(this, 0, this.cropomator.processTime);
		crafting.sendProgressBarUpdate(this, 1, this.cropomator.catalystTime);
		crafting.sendProgressBarUpdate(this, 2, this.cropomator.catalystInUseTime);
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for (int i = 0; i < this.crafters.size(); ++i)
		{
			ICrafting crafting = this.crafters.get(i);

			if (this.lastUseTime != this.cropomator.processTime)
				crafting.sendProgressBarUpdate(this, 0, this.cropomator.processTime);
			if (this.lastProcessTime != this.cropomator.catalystTime)
				crafting.sendProgressBarUpdate(this, 1, this.cropomator.catalystTime);
			if (this.lastCatalystInUseTime != this.cropomator.catalystInUseTime)
				crafting.sendProgressBarUpdate(this, 2, this.cropomator.catalystInUseTime);
		}
		this.lastUseTime = this.cropomator.processTime;
		this.lastProcessTime = this.cropomator.catalystTime;
		this.lastCatalystInUseTime = this.cropomator.catalystInUseTime;
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
				if (TileCropomator.isValid(itemstack1))
				{
					if (!this.mergeItemStack(itemstack1, 0, 1, false))
						return null;
				}
				else if (TileCropomator.isItemCatalyst(itemstack1))
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
		return this.cropomator.isUseableByPlayer(this.player);
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
