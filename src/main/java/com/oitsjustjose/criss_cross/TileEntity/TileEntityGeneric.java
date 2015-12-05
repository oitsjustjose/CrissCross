package com.oitsjustjose.criss_cross.TileEntity;

import java.util.ArrayList;

import com.oitsjustjose.criss_cross.Util.Reference;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class TileEntityGeneric extends TileEntityLockable implements ITickable, ISidedInventory
{
	private static int proTicks = 0;
	private static final int[] slotsTop = new int[] { 0 };
	private static final int[] slotsBottom = new int[] { 2, 1 };
	private static final int[] slotsSides = new int[] { 1 };
	private static String defaultName;
	private static String customName;
	private ItemStack[] ItemStacks = new ItemStack[3];
	private static ArrayList<ItemStack> fuelItems = new ArrayList<ItemStack>();

	public int fuelTime;
	public int processTime;
	public int fuelUsetime;

	public TileEntityGeneric(String tileName, int processTicks)
	{
		this.defaultName = tileName;
		this.proTicks = processTicks;
		
		/*
		 * Any Tile Entity extending this TE must implement its own update(),
		 * canProcess(), isValid(), processItem(), and createContainer()
		 */
	}
	
	public static ArrayList<ItemStack> getFuels()
	{
		return fuelItems;
	}

	@Override
	public int getSizeInventory()
	{
		return this.ItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return this.ItemStacks[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int qtyToDecr)
	{
		if (this.ItemStacks[slot] != null)
		{
			ItemStack itemstack;

			if (this.ItemStacks[slot].stackSize <= qtyToDecr)
			{
				itemstack = this.ItemStacks[slot];
				this.ItemStacks[slot] = null;
				return itemstack;
			}
			else
			{
				itemstack = this.ItemStacks[slot].splitStack(qtyToDecr);

				if (this.ItemStacks[slot].stackSize == 0)
				{
					this.ItemStacks[slot] = null;
				}

				return itemstack;
			}
		}
		else
		{
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		if (this.ItemStacks[slot] != null)
		{
			ItemStack itemstack = this.ItemStacks[slot];
			this.ItemStacks[slot] = null;
			return itemstack;
		}
		else
		{
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack)
	{
		this.ItemStacks[slot] = itemstack;

		if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
		{
			itemstack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public String getGuiID()
	{
		return Reference.modid + ":container." + this.defaultName.toLowerCase();
	}

	@Override
	public boolean hasCustomName()
	{
		return this.customName != null && this.customName.length() > 0;
	}

	public void setCustomInventoryName(String newName)
	{
		this.customName = newName;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		NBTTagList nbttaglist = tag.getTagList("Items", 10);
		this.ItemStacks = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte b0 = nbttagcompound1.getByte("Slot");

			if (b0 >= 0 && b0 < this.ItemStacks.length)
			{
				this.ItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}

		this.fuelTime = tag.getShort("FuelTime");
		this.processTime = tag.getShort("WorkTime");
		if (this.ItemStacks[1] != null)
			this.fuelUsetime = getItemBurnTime(this.ItemStacks[1]);
		else
			this.fuelUsetime = 0;
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		tag.setShort("FuelTime", (short) this.fuelTime);
		tag.setShort("WorkTime", (short) this.processTime);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.ItemStacks.length; ++i)
		{
			if (this.ItemStacks[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.ItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		tag.setTag("Items", nbttaglist);
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@SideOnly(Side.CLIENT)
	public int getProgressScaled(int par1)
	{
		return this.processTime * par1 / proTicks;
	}

	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int par1)
	{
		if (this.fuelUsetime == 0)
		{
			this.fuelUsetime = proTicks;
		}

		return this.fuelTime * par1 / this.fuelUsetime;
	}

	public boolean isUsingFuel()
	{
		return this.fuelTime > 0;
	}

	public static void addFuel(ItemStack itemstack)
	{
		fuelItems.add(itemstack);
	}

	public static boolean removeFuel(ItemStack itemstack)
	{
		for (int i = 0; i < fuelItems.size(); i++)
		{
			if (fuelItems.get(i) == itemstack)
			{
				fuelItems.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public static int getItemBurnTime(ItemStack itemstack)
	{
		return isItemFuel(itemstack) ? proTicks : 0;
	}

	public static boolean isItemFuel(ItemStack itemstack)
	{
		for (int i = 0; i < fuelItems.size(); i++)
		{
			ItemStack temp = fuelItems.get(i);
			if (temp.getItem() == itemstack.getItem() && temp.getItemDamage() == itemstack.getItemDamage())
				return true;
		}
		return false;
	}

	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return this.worldObj.getTileEntity(this.pos) != this ? false
				: player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack)
	{
		return slot == 2 ? false : (slot == 1 ? isItemFuel(itemstack) : true);
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return side == EnumFacing.DOWN ? slotsBottom : (side == EnumFacing.UP ? slotsTop : slotsSides);
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, EnumFacing direction)
	{
		return this.isItemValidForSlot(slot, itemstack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, EnumFacing direction)
	{
		return direction != EnumFacing.DOWN || slot != 1;
	}

	@Override
	public void markDirty()
	{
	}

	@Override
	public void openInventory(EntityPlayer player)
	{
	}

	@Override
	public void closeInventory(EntityPlayer player)
	{
	}

	@Override
	public int getField(int id)
	{
		switch (id)
		{
		case 0:
			return this.fuelTime;
		case 1:
			return this.fuelUsetime;
		case 2:
			return this.processTime;
		default:
			return 0;
		}
	}

	@Override
	public void setField(int id, int value)
	{
		switch (id)
		{
		case 0:
			this.fuelTime = value;
			break;
		case 1:
			this.fuelUsetime = value;
			break;
		case 2:
			this.processTime = value;
			break;
		}
	}

	@Override
	public int getFieldCount()
	{
		return 3;
	}

	@Override
	public void clear()
	{
		for (int i = 0; i < this.ItemStacks.length; ++i)
			this.ItemStacks[i] = null;
	}

	@Override
	public String getCommandSenderName()
	{
		return this.hasCustomName() ? this.customName : "container." + this.defaultName.toLowerCase();
	}
}
