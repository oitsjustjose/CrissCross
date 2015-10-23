package com.oitsjustjose.criss_cross.TileEntity;

import java.util.ArrayList;

import com.oitsjustjose.criss_cross.Blocks.BlockWoodchipper;
import com.oitsjustjose.criss_cross.Recipes.WoodchipperRecipes;
import com.oitsjustjose.criss_cross.Util.ConfigHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityWoodchipper extends TileEntity implements ISidedInventory
{
	private static int proTicks = ConfigHandler.woodchipperProcessTime;
	private static int qty = ConfigHandler.woodchipperOutput;
	private static final int[] slotsTop = new int[] { 0 };
	private static final int[] slotsBottom = new int[] { 2, 1 };
	private static final int[] slotsSides = new int[] { 1 };
	private static ArrayList<ItemStack> fuelItems = new ArrayList<ItemStack>();

	private ItemStack[] ItemStacks = new ItemStack[3];

	public int fuelTime;
	public int processTime;
	public int fuelUsetime;

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
	public String getInventoryName()
	{
		return "container.woodchipper";
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return true;
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
		this.fuelUsetime = getItemBurnTime(this.ItemStacks[1]);
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

	public void updateEntity()
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
					this.fuelUsetime = this.fuelTime = getItemBurnTime(this.ItemStacks[1]);

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
					++this.processTime;

					if (this.processTime == proTicks)
					{
						this.processTime = 0;
						this.processItem();
						flag1 = true;
					}
				}
				else
				{
					this.processTime = 0;
				}
			}

			if (flag != this.fuelTime > 0)
			{
				flag1 = true;
				BlockWoodchipper.updateBlockState(this.fuelTime > 0, this.worldObj, this.xCoord, this.yCoord,
						this.zCoord);
			}
		}

		if (flag1)
		{
			this.markDirty();
		}
	}

	private boolean canProcess()
	{
		ItemStack input = this.ItemStacks[0];
		if (input != null)
		{
			ItemStack output = WoodchipperRecipes.getInstance().getResult(input);
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

	public static boolean isValidForWoodchipper(ItemStack itemstack)
	{
		if (WoodchipperRecipes.getInstance().getResult(itemstack) != null)
			return true;
		return false;
	}

	public void processItem()
	{
		if (this.canProcess())
		{

			ItemStack input = ItemStacks[0];
			ItemStack output = WoodchipperRecipes.getInstance().getResult(input);
			ItemStack outputSlot = ItemStacks[2];
			if (outputSlot == null)
			{
				ItemStacks[2] = output.copy();
			}
			else
				if (outputSlot.isItemEqual(output))
				{
					outputSlot.stackSize += output.stackSize;
				}

			--input.stackSize;
			if (input.stackSize <= 0)
			{
				ItemStacks[0] = null;
			}
		}
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

		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false
				: player.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D,
						(double) this.zCoord + 0.5D) <= 64.0D;
	}

	public void openInventory()
	{
	}

	public void closeInventory()
	{
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack)
	{
		return slot == 2 ? false : (slot == 1 ? isItemFuel(itemstack) : true);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int slot)
	{
		return slot == 0 ? slotsBottom : (slot == 1 ? slotsTop : slotsSides);
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, int side)
	{
		return this.isItemValidForSlot(slot, itemstack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int side)
	{
		return side != 0 || slot != 1;
	}

	@Override
	public void markDirty()
	{
	}

	public static class WoodchipperRecipe
	{
		public final int time;
		public final ItemStack input;
		public final ItemStack result;

		public WoodchipperRecipe(ItemStack input, ItemStack result)
		{
			this.time = ConfigHandler.woodchipperProcessTime;
			this.input = input;
			this.result = result;
		}

		public boolean matches(ItemStack input)
		{
			return ItemStack.areItemStacksEqual(this.input, input);
		}

		public ItemStack getResult()
		{
			return result.copy();
		}
	}
}
