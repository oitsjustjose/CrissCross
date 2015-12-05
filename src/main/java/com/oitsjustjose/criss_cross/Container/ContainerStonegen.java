package com.oitsjustjose.criss_cross.Container;

import com.oitsjustjose.criss_cross.TileEntity.TileEntityStonegen;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerStonegen extends Container
{
	TileEntityStonegen Stonegen;
	EntityPlayer player;

	private int lastUseTime;
	private int lastChopTime;
	private int lastFuelTime;

	public ContainerStonegen(EntityPlayer player, TileEntityStonegen Stonegen)
	{
		this.player = player;
		this.Stonegen = Stonegen;

		lastUseTime = 0;
		lastChopTime = 0;
		lastFuelTime = 0;

		this.addSlotToContainer(new Slot(Stonegen, 0, 56, 17)); // input
		this.addSlotToContainer(new Slot(Stonegen, 1, 56, 53)); // catalyst
		this.addSlotToContainer(new MachineOutputSlot(player, Stonegen, 2, 116, 35));
		this.addInventorySlots();
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int timeType1, int timeType2)
	{
		switch (timeType1)
		{
		case 0:
			this.Stonegen.processTime = timeType2;
			break;
		case 1:
			this.Stonegen.fuelTime = timeType2;
			break;
		case 2:
			this.Stonegen.fuelUsetime = timeType2;
			break;
		}
	}

	@Override
	public void onCraftGuiOpened(ICrafting crafting)
	{
		super.onCraftGuiOpened(crafting);
		crafting.sendProgressBarUpdate(this, 0, this.Stonegen.processTime);
		crafting.sendProgressBarUpdate(this, 1, this.Stonegen.fuelTime);
		crafting.sendProgressBarUpdate(this, 2, this.Stonegen.fuelUsetime);
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for (int i = 0; i < this.crafters.size(); ++i)
		{
			ICrafting crafting = (ICrafting) this.crafters.get(i);

			if (this.lastUseTime != this.Stonegen.processTime)
				crafting.sendProgressBarUpdate(this, 0, this.Stonegen.processTime);
			if (this.lastChopTime != this.Stonegen.fuelTime)
				crafting.sendProgressBarUpdate(this, 1, this.Stonegen.fuelTime);
			if (this.lastFuelTime != this.Stonegen.fuelUsetime)
				crafting.sendProgressBarUpdate(this, 2, this.Stonegen.fuelUsetime);
		}
		this.lastUseTime = this.Stonegen.processTime;
		this.lastChopTime = this.Stonegen.fuelTime;
		this.lastFuelTime = this.Stonegen.fuelUsetime;
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
					if (TileEntityStonegen.isValid(itemstack1))
					{
						if (!this.mergeItemStack(itemstack1, 0, 1, false))
						{
							return null;
						}
					}
					else
						if (TileEntityStonegen.isItemFuel(itemstack1))
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
		return this.Stonegen.isUseableByPlayer(this.player);
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
