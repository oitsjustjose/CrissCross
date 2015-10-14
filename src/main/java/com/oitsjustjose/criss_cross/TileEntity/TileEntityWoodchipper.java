package com.oitsjustjose.criss_cross.TileEntity;

import java.util.ArrayList;

import com.oitsjustjose.criss_cross.Blocks.BlockCropomator;
import com.oitsjustjose.criss_cross.Util.ConfigHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityWoodchipper extends TileEntity implements ISidedInventory
{
    private static int proTicks = ConfigHandler.woodchipperProcessTime;
    private static int qty = ConfigHandler.woodchipperOutput;
    private static final int[] slotsTop    = new int[] {0};
    private static final int[] slotsBottom = new int[] {2, 1};
    private static final int[] slotsSides  = new int[] {1};
    private static ArrayList<ItemStack> inputItems = new ArrayList<ItemStack>();
    private static ArrayList<ItemStack> outputItems = new ArrayList<ItemStack>();
    private static ArrayList<ItemStack> fuelItems   = new ArrayList<ItemStack>();

    private ItemStack[] ItemStacks = new ItemStack[3];
    
    public int fuelTime;
    public int processTime;
    public int fuelUsetime;
    
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
        tag.setShort("FuelTime", (short)this.fuelTime);
        tag.setShort("WorkTime", (short)this.processTime);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.ItemStacks.length; ++i)
        {
            if (this.ItemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
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
                BlockCropomator.updateBlockState(this.fuelTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
        }

        if (flag1)
        {
            this.markDirty();
        }
    }

    private boolean canProcess()
    {
    	if (this.ItemStacks[0] == null)
        {
            return false;
        }
        else
        {
        	int posInArList = 0;
        	for(int i = 0; i < inputItems.size(); i++)
        	{
        		if(inputItems.get(i).getItem() == ItemStacks[0].getItem() && inputItems.get(i).getItemDamage() == ItemStacks[0].getItemDamage())
        		{
        			posInArList = i;
        		}
        	}
        	
            ItemStack itemstack = new ItemStack(outputItems.get(posInArList).getItem(), qty, outputItems.get(posInArList).getItemDamage());
            
            if(this.ItemStacks[2] == null)
            	return true;
            if(!this.ItemStacks[2].isItemEqual(itemstack))
            	return false;
            int result = ItemStacks[2].stackSize + itemstack.stackSize;
            
            return result <= getInventoryStackLimit() && result <= this.ItemStacks[2].getMaxStackSize();
        }
    }
    
    public static boolean removeRecipe(ItemStack itemstack)
    {
    	for(int i = 0; i < inputItems.size(); i++)
    	{
    		if(inputItems.get(i) == itemstack)
    		{
    			inputItems.remove(i);
    			outputItems.remove(i);
    			return true;
    		}
    	}
    	return false;
    }
    
    public static void addRecipe(ItemStack input, ItemStack output)
    {
    	inputItems.add(input);
    	outputItems.add(output);
    }
    
    public static void addItemRecipe(Item item, ItemStack output)
    {
    	inputItems.add(new ItemStack(item));
    	outputItems.add(output);
    }
    
    public static void addBlockRecipe(Block block, ItemStack output)
    {
    	inputItems.add(new ItemStack(block));
    	outputItems.add(output);
    }
    
    public static void addItemStackToFuels(ItemStack itemstack)
    {
    	fuelItems.add(itemstack);
    }
    
    public static boolean removeItemStackFromFuels(ItemStack itemstack)
    {
    	for(int i = 0; i < fuelItems.size(); i++)
    	{
    		if(fuelItems.get(i) == itemstack)
    		{
    			fuelItems.remove(i);
    			return true;
    		}
    	}
    	return false;
    }
    
    public static boolean isValidForWoodchipper(ItemStack itemstack)
    {
    	for(int i = 0; i < inputItems.size(); i++)
    	{
    		ItemStack temp = inputItems.get(i);
    		if(temp.getItem() == itemstack.getItem() && temp.getItemDamage() == itemstack.getItemDamage())
    			return true;
    	}
    	
    	return false;
    }

    public void processItem()
    {
    	if (this.canProcess())
        {
        	int posInArList = 0;
        	for(int i = 0; i < inputItems.size(); i++)
        	{
        		if(inputItems.get(i).getItem() == ItemStacks[0].getItem() && inputItems.get(i).getItemDamage() == ItemStacks[0].getItemDamage())
        		{
        			posInArList = i;
        		}
        	}
        	
            ItemStack output = new ItemStack(outputItems.get(posInArList).getItem(), qty, outputItems.get(posInArList).getItemDamage());

            if (this.ItemStacks[2] == null)
            {
                this.ItemStacks[2] = output.copy();
            }
            else if (this.ItemStacks[2].getItem() == output.getItem())
            {
                this.ItemStacks[2].stackSize += output.stackSize;
            }

            --this.ItemStacks[0].stackSize;

            if (this.ItemStacks[0].stackSize <= 0)
            {
                this.ItemStacks[0] = null;
            }
        }
    }

    public static int getItemBurnTime(ItemStack itemstack)
    {
    	return isItemFuel(itemstack) ? proTicks : 0;
    }

    public static boolean isItemFuel(ItemStack itemstack)
    {
    	for(int i = 0; i < fuelItems.size(); i++)
    	{
    		ItemStack temp = fuelItems.get(i);
    		if(temp.getItem() == itemstack.getItem() && temp.getItemDamage() == itemstack.getItemDamage())
    			return true;
    	}
        return false;
    }

    public boolean isUseableByPlayer(EntityPlayer player)
    {
    	
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

    public void openInventory(){}

    public void closeInventory(){}

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
	public void markDirty(){}
}