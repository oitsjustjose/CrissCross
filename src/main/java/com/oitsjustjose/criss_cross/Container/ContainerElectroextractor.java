package com.oitsjustjose.criss_cross.container;

import com.oitsjustjose.criss_cross.tileentity.TileElectroextractor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerElectroextractor extends Container
{
	TileElectroextractor electroextractor;
	EntityPlayer player;

	private int lastUseTime;
	private int lastCrushTime;
	private int lastFuelTime;

	public ContainerElectroextractor(EntityPlayer player, TileElectroextractor electroextractor)
	{
		this.player = player;
		this.electroextractor = electroextractor;

		lastUseTime = 0;
		lastCrushTime = 0;
		lastFuelTime = 0;

		this.addSlotToContainer(new Slot(electroextractor, 0, 56, 17)); // input
		this.addSlotToContainer(new Slot(electroextractor, 1, 56, 53)); // fuelitem
		this.addSlotToContainer(new MachineOutputSlot(player, electroextractor, 2, 116, 35));
		this.addInventorySlots();
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int timeType1, int timeType2)
	{
		if (timeType1 == 0)
		{
			this.electroextractor.crushTime = timeType2;
		}

		if (timeType1 == 1)
		{
			this.electroextractor.fuelTime = timeType2;
		}

		if (timeType1 == 2)
		{
			this.electroextractor.fuelInUseTime = timeType2;
		}
	}

	@Override
	public void onCraftGuiOpened(ICrafting crafting)
	{
		super.onCraftGuiOpened(crafting);
		crafting.sendProgressBarUpdate(this, 0, this.electroextractor.crushTime);
		crafting.sendProgressBarUpdate(this, 1, this.electroextractor.fuelTime);
		crafting.sendProgressBarUpdate(this, 2, this.electroextractor.fuelInUseTime);
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for (int i = 0; i < this.crafters.size(); ++i)
		{
			ICrafting crafting = (ICrafting) this.crafters.get(i);

			if (this.lastUseTime != this.electroextractor.crushTime)
				crafting.sendProgressBarUpdate(this, 0, this.electroextractor.crushTime);
			if (this.lastCrushTime != this.electroextractor.fuelTime)
				crafting.sendProgressBarUpdate(this, 1, this.electroextractor.fuelTime);
			if (this.lastFuelTime != this.electroextractor.fuelInUseTime)
				crafting.sendProgressBarUpdate(this, 2, this.electroextractor.fuelInUseTime);
		}
		this.lastUseTime = this.electroextractor.crushTime;
		this.lastCrushTime = this.electroextractor.fuelTime;
		this.lastFuelTime = this.electroextractor.fuelInUseTime;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(slotID);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (slotID == 2)
			{
				if (!this.mergeItemStack(itemstack1, 3, 39, true))
				{
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}
			else
				if (slotID != 1 && slotID != 0)
				{
					if (TileElectroextractor.isValid(itemstack1))
					{
						if (!this.mergeItemStack(itemstack1, 0, 1, false))
						{
							return null;
						}
					}
					else
						if (TileElectroextractor.isItemEnergetic(itemstack1))
						{
							if (!this.mergeItemStack(itemstack1, 1, 2, false))
							{
								return null;
							}
						}
						else
							if (slotID >= 3 && slotID < 30)
							{
								if (!this.mergeItemStack(itemstack1, 30, 39, false))
								{
									return null;
								}
							}
							else
								if (slotID >= 30 && slotID < 39 && !this.mergeItemStack(itemstack1, 3, 30, false))
								{
									return null;
								}
				}
				else
					if (!this.mergeItemStack(itemstack1, 3, 39, false))
					{
						return null;
					}

			if (itemstack1.stackSize == 0)
			{
				slot.putStack((ItemStack) null);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize)
			{
				return null;
			}

			slot.onPickupFromSlot(player, itemstack1);
		}

		return itemstack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return this.electroextractor.isUseableByPlayer(this.player);
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
