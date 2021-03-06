package com.oitsjustjose.criss_cross.tileentity;

import java.util.ArrayList;

import com.oitsjustjose.criss_cross.blocks.BlockCobblegen;
import com.oitsjustjose.criss_cross.container.ContainerCobblegen;
import com.oitsjustjose.criss_cross.lib.Config;
import com.oitsjustjose.criss_cross.lib.Lib;
import com.oitsjustjose.criss_cross.lib.LibItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class TileCobblegen extends TileEntityLockable implements ITickable, ISidedInventory
{
	public TileCobblegen()
	{
		this.addFuel(new ItemStack(Items.water_bucket));
		this.addFuel(new ItemStack(LibItems.buckets));
	}

	private static int proTicks = Config.blockGeneratorProcessTimes;
	private static final int[] slotsTop = new int[] { 0 };
	private static final int[] slotsBottom = new int[] { 2, 1 };
	private static final int[] slotsSides = new int[] { 1 };
	private static ArrayList<ItemStack> fuelItems = new ArrayList<ItemStack>();
	private ItemStack[] ItemStacks = new ItemStack[3];
	public int fuelTime;
	public int processTime;
	public int totalTime;
	public int currentFuelBuffer;

	@Override
	public void update()
	{
		boolean isBurning = this.isUsingFuel();
		boolean flag = false;

		if (this.fuelTime > 0)
			--this.fuelTime;

		if (!this.worldObj.isRemote)
		{
			if (this.fuelTime != 0 || this.ItemStacks[1] != null && this.ItemStacks[0] != null)
			{
				if (this.fuelTime == 0 && this.canProcess())
				{
					this.currentFuelBuffer = this.fuelTime = getFuelAmount(this.ItemStacks[1]);

					if (this.fuelTime > 0)
						flag = true;
				}

				if (this.isUsingFuel() && this.canProcess())
				{
					++this.processTime;

					if (this.processTime == proTicks)
					{
						this.processTime = 0;
						this.totalTime = this.getFuelAmount(this.ItemStacks[0]);
						this.processItem();
						flag = true;
					}
				}
				else
					this.processTime = 0;
			}
			else if (!this.isUsingFuel() && this.processTime > 0)
				this.processTime = MathHelper.clamp_int(this.processTime - 2, 0, this.totalTime);

			if (isBurning != this.fuelTime > 0)
			{
				flag = true;
				BlockCobblegen.updateBlockState(this.isUsingFuel(), this.worldObj, this.pos);
			}
		}

		if (flag)
			this.markDirty();
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
					this.ItemStacks[slot] = null;

				return itemstack;
			}
		}
		else
			return null;
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
			return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack)
	{
		this.ItemStacks[slot] = itemstack;

		if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
			itemstack.stackSize = this.getInventoryStackLimit();
	}

	@Override
	public String getGuiID()
	{
		return Lib.MODID + ":container.cobblestonegen";
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
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
				this.ItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}

		this.fuelTime = tag.getShort("FuelTime");
		this.processTime = tag.getShort("WorkTime");
		this.totalTime = tag.getShort("WorkTimeTotal");
		this.currentFuelBuffer = getFuelAmount(this.ItemStacks[1]);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		tag.setShort("FuelTime", (short) this.fuelTime);
		tag.setShort("WorkTime", (short) this.processTime);
		tag.setShort("WorkTimeTotal", (short) this.totalTime);

		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.ItemStacks.length; ++i)
			if (this.ItemStacks[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.ItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
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
		if (this.currentFuelBuffer == 0)
			this.currentFuelBuffer = proTicks;

		return this.fuelTime * par1 / this.currentFuelBuffer;
	}

	public boolean isUsingFuel()
	{
		return this.fuelTime > 0;
	}

	private boolean canProcess()
	{
		ItemStack input = this.ItemStacks[0];
		if (input != null && isValid(input))
		{
			ItemStack output = new ItemStack(Blocks.cobblestone);
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
			if (fuelItems.get(i) == itemstack)
			{
				fuelItems.remove(i);
				return true;
			}
		return false;
	}

	public static boolean isValid(ItemStack itemstack)
	{
		return FluidContainerRegistry.containsFluid(itemstack, new FluidStack(FluidRegistry.LAVA, 1000));

	}

	public void processItem()
	{
		if (this.canProcess())
		{
			ItemStack input = ItemStacks[0];
			ItemStack output = new ItemStack(Blocks.cobblestone);
			ItemStack outputSlot = ItemStacks[2];
			if (outputSlot == null)
				ItemStacks[2] = output.copy();
			else if (outputSlot.isItemEqual(output))
				outputSlot.stackSize += output.stackSize;

			if (input.stackSize <= 0)
				ItemStacks[0] = null;
		}
	}

	public static int getFuelAmount(ItemStack itemstack)
	{
		if (itemstack == null)
			return 0;
		return isItemFuel(itemstack) ? proTicks : 0;
	}

	public static boolean isItemFuel(ItemStack itemstack)
	{
		return FluidContainerRegistry.containsFluid(itemstack, new FluidStack(FluidRegistry.WATER, 1000));
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return this.worldObj.getTileEntity(this.pos) != this ? false : player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D;
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
			return this.currentFuelBuffer;
		case 2:
			return this.processTime;
		case 3:
			return this.totalTime;
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
			this.currentFuelBuffer = value;
			break;
		case 2:
			this.processTime = value;
			break;
		case 3:
			this.totalTime = value;
		}
	}

	@Override
	public int getFieldCount()
	{
		return 4;
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
		return "container.cobblegen";
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
	{
		return new ContainerCobblegen(playerInventory.player, this);
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

	IItemHandler handlerTop = new SidedInvWrapper(this, EnumFacing.UP);
	IItemHandler handlerBottom = new SidedInvWrapper(this, EnumFacing.DOWN);
	IItemHandler handlerSide = new SidedInvWrapper(this, EnumFacing.WEST);

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			if (facing == EnumFacing.DOWN)
				return (T) handlerBottom;
			else if (facing == EnumFacing.UP)
				return (T) handlerTop;
			else
				return (T) handlerSide;
		return super.getCapability(capability, facing);
	}
}
