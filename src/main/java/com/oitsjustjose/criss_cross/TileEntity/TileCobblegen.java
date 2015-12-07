package com.oitsjustjose.criss_cross.tileentity;

import java.util.ArrayList;

import com.oitsjustjose.criss_cross.blocks.BlockCobblegen;
import com.oitsjustjose.criss_cross.container.ContainerCobblegen;
import com.oitsjustjose.criss_cross.recipes.CobblegenRecipes;
import com.oitsjustjose.criss_cross.util.ConfigHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public class TileCobblegen extends TileGeneric
{
	private static int proTicks = ConfigHandler.blockGeneratorProcessTimes;
	private static final int[] slotsTop = new int[] { 0 };
	private static final int[] slotsBottom = new int[] { 2, 1 };
	private static final int[] slotsSides = new int[] { 1 };
	private static ArrayList<ItemStack> fuelItems = new ArrayList<ItemStack>();
	private static String customName;
	private ItemStack[] ItemStacks = new ItemStack[3];
	
	public int fuelTime;
	public int processTime;
	public int fuelUsetime;
	
	public TileCobblegen()
	{
		super("Cobblegen", proTicks);
	}

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
					this.fuelUsetime = this.fuelTime = getItemBurnTime(this.ItemStacks[1]);

					if (this.fuelTime > 0)
					{
						flag1 = true;

						if (this.ItemStacks[1] != null)
						{
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
				BlockCobblegen.updateBlockState(this.fuelTime > 0, this.worldObj, this.pos);
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
			ItemStack output = CobblegenRecipes.getInstance().getResult(input);
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

	public static boolean isValid(ItemStack itemstack)
	{
		if (CobblegenRecipes.getInstance().getResult(itemstack) != null)
			return true;
		return false;
	}

	public void processItem()
	{
		if (this.canProcess())
		{

			ItemStack input = ItemStacks[0];
			ItemStack output = CobblegenRecipes.getInstance().getResult(input);
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

			if (input.stackSize <= 0)
			{
				ItemStacks[0] = null;
			}
		}
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
	{
		return new ContainerCobblegen(playerInventory.player, this);
	}

}
