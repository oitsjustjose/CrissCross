package com.oitsjustjose.criss_cross.Container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import com.oitsjustjose.criss_cross.TileEntity.TileEntityAutosmith;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAutosmith extends Container
{
	TileEntityAutosmith autosmith;
	EntityPlayer player;
	
	private int lastUseTime;
	private int lastChopTime;
	private int lastFuelTime;
	
	public ContainerAutosmith(EntityPlayer player, TileEntityAutosmith autosmith)
	{
		this.player = player;
		this.autosmith = autosmith;
		
		lastUseTime = 0;
		lastChopTime = 0;
		lastFuelTime = 0;
		
		this.addSlotToContainer(new Slot(autosmith, 0, 56, 17)); // tool
		this.addSlotToContainer(new Slot(autosmith, 1, 56, 53)); // repairItem
		this.addSlotToContainer(new MachineOutputSlot(player, autosmith, 2, 116, 35));
		this.addSlotToContainer(new Slot(autosmith, 3, 140, 35));
		this.addInventorySlots();
	}
	
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int timeType1, int timeType2)
	{
		switch(timeType1)
		{
		case 0:
		this.autosmith.processTime = timeType2;
			break;
		case 1:
		this.autosmith.fuelTime = timeType2;
			break;
		case 2:
		this.autosmith.fuelUsetime = timeType2;
			break;
		}
	}
	
	@Override
	public void addCraftingToCrafters(ICrafting crafting)
	{
		super.addCraftingToCrafters(crafting);
		crafting.sendProgressBarUpdate(this, 0, this.autosmith.processTime);
		crafting.sendProgressBarUpdate(this, 1, this.autosmith.fuelTime);
		crafting.sendProgressBarUpdate(this, 2, this.autosmith.fuelUsetime);
	}
	
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for(int i = 0; i < this.crafters.size(); ++i)
		{
			ICrafting crafting = (ICrafting) this.crafters.get(i);
			
			if(this.lastUseTime != this.autosmith.processTime)
				crafting.sendProgressBarUpdate(this, 0, this.autosmith.processTime);
			if(this.lastChopTime != this.autosmith.fuelTime)
				crafting.sendProgressBarUpdate(this, 1, this.autosmith.fuelTime);
			if(this.lastFuelTime != this.autosmith.fuelUsetime)
				crafting.sendProgressBarUpdate(this, 2, this.autosmith.fuelUsetime);
		}
		this.lastUseTime = this.autosmith.processTime;
		this.lastChopTime = this.autosmith.fuelTime;
		this.lastFuelTime = this.autosmith.fuelUsetime;
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
			if (slotID < 4)
			{
				if (!this.mergeItemStack(itemstack1, 4, 40, true))
					return null;
			}
			else if (TileEntityAutosmith.isItemFuel(itemstack1))
			{
				if (!this.mergeItemStack(itemstack1, 1, 2, false))
					return null;
			}
			else if (slotID >= 3 && slotID < 31)
			{
				if (!this.mergeItemStack(itemstack1, 31, 40, false))
					return null;
			}
			else if (slotID >= 30 && slotID < 40)
			{
				if (!this.mergeItemStack(itemstack1, 4, 31, false))
					return null;
			}
			
			if (itemstack1.stackSize == 0)
			{
				slot.putStack(null);
			}
			else
			{
				slot.onSlotChanged();
			}
			
			if (itemstack1.stackSize == itemstack.stackSize)
				return null;
			
			slot.onPickupFromSlot(player, itemstack);
		}
		
		return itemstack;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return this.autosmith.isUseableByPlayer(this.player);
	}
	
	public final void addInventorySlots()
	{
		this.addInventorySlots(8, 84, 142);
	}
	
	public final void addInventorySlots(int xOffset, int yOffset, int yHotbar)
	{
		InventoryPlayer inventory = this.player.inventory;
		
		for(int i = 0; i < 3; ++i)
			for(int j = 0; j < 9; ++j)
				this.addSlotToContainer(new Slot(inventory, 9 + j + i * 9, xOffset + j * 18, yOffset + i * 18));
				
		for(int i = 0; i < 9; ++i)
			this.addSlotToContainer(new Slot(inventory, i, xOffset + i * 18, yHotbar));
	}
}
