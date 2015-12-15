package com.oitsjustjose.criss_cross.tileentity;

import java.util.ArrayList;

import com.oitsjustjose.criss_cross.blocks.BlockScribe;
import com.oitsjustjose.criss_cross.container.ContainerScribe;
import com.oitsjustjose.criss_cross.lib.Lib;
import com.oitsjustjose.criss_cross.recipes.ScribeRecipes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
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

public class TileScribe extends TileEntityLockable implements ITickable, ISidedInventory
{
	// TODO: change this back to 700.
	private static int proTicks = 700;
	private static final int[] slotsTop = new int[] { 0 };
	private static final int[] slotsBottom = new int[] { 2, 1 };
	private static final int[] slotsSides = new int[] { 1 };
	private static String customName;
	private ItemStack[] ItemStacks = new ItemStack[3];

	public int fuelTime;
	public int writingTime;
	public int fuelUsetime;

	@Override
	public void update()
	{
		boolean flag = this.fuelTime > 0;
		boolean flag1 = false;

		if (this.fuelTime > 0)
		{
			--this.fuelTime;
		}

		if (!this.worldObj.isRemote)
		{
			if (this.fuelTime != 0 || this.ItemStacks[1] != null && this.ItemStacks[0] != null)
			{
				if (this.fuelTime == 0 && this.canProcess())
				{
					this.fuelUsetime = this.fuelTime = getBookUseTime(this.ItemStacks[1]);

					if (this.fuelTime > 0)
					{
						flag1 = true;

						if (this.ItemStacks[1] != null)
						{
							--this.ItemStacks[1].stackSize;

							if (this.ItemStacks[1].stackSize == 0)
							{
								this.ItemStacks[1] = ItemStacks[1].getItem().getContainerItem(ItemStacks[1]);
							}
						}
					}
				}

				if (this.isUsingFuel() && this.canProcess())
				{
					++this.writingTime;

					if (this.writingTime == proTicks)
					{
						this.worldObj.playSoundEffect(this.pos.getX(), this.pos.getY(), this.pos.getZ(), "portal.portal", 0.5F, 1.0F);
						this.writingTime = 0;
						this.processItem();
						flag1 = true;
					}
				}
				else
				{
					this.writingTime = 0;
				}
			}

			if (flag != this.fuelTime > 0)
			{
				flag1 = true;
				BlockScribe.updateBlockState(this.fuelTime > 0, this.worldObj, this.pos);
			}
		}

		if (flag1)
		{
			this.markDirty();
		}
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
	public ItemStack removeStackFromSlot(int slot)
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
		return Lib.modid + ":container.scribe";
	}

	@Override
	public boolean hasCustomName()
	{
		return this.customName != null && this.customName.length() > 0;
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
		this.writingTime = tag.getShort("WorkTime");
		if (this.ItemStacks[1] != null)
			this.fuelUsetime = getBookUseTime(this.ItemStacks[1]);
		else
			this.fuelUsetime = 0;
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		tag.setShort("FuelTime", (short) this.fuelTime);
		tag.setShort("WorkTime", (short) this.writingTime);
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
		return this.writingTime * par1 / proTicks;
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

	private boolean canProcess()
	{
		ItemStack input = this.ItemStacks[0];
		if (input != null)
		{
			ItemStack output = ScribeRecipes.getInstance().getResult(input);
			if (output == null)
				return false;
			ItemStack outputSlot = this.ItemStacks[2];
			if (outputSlot == null)
				return true;
			if (!outputSlot.isItemEqual(output))
				return false;
			int result = outputSlot.stackSize + output.stackSize;
			return result <= output.getMaxStackSize();
		}
		return false;
	}

	public static boolean isValidForScribe(ItemStack itemstack)
	{
		if (ScribeRecipes.getInstance().getResult(itemstack) != null)
			return true;
		return false;
	}

	public void processItem()
	{
		if (this.canProcess())
		{
			ItemStack input = ItemStacks[0];
			ItemStack output = ScribeRecipes.getInstance().getResult(input);
			ItemStack outputSlot = ItemStacks[2];
			if (outputSlot == null)
			{
				ItemStacks[2] = output.copy();
			}
			else if (outputSlot.isItemEqual(output))
			{
				outputSlot.stackSize += output.stackSize;
			}
			ItemStacks[0] = null;
		}
	}

	public static int getBookUseTime(ItemStack itemstack)
	{
		return isBook(itemstack) ? proTicks : 0;
	}

	public static boolean isBook(ItemStack itemstack)
	{
		ItemStack temp = new ItemStack(Items.writable_book);
		if (temp.getItem() == itemstack.getItem() && temp.getItemDamage() == itemstack.getItemDamage())
			return true;
		return false;
	}

	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return this.worldObj.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
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
			return this.writingTime;
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
			this.writingTime = value;
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
	public String getName()
	{
		return this.hasCustomName() ? this.customName : "container.scribe";
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
	{
		return new ContainerScribe(playerInventory.player, this);
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack)
	{
		return slot == 2 ? false : (slot == 1 ? isBook(itemstack) : true);
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
}