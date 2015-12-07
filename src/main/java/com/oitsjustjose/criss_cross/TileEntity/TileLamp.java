package com.oitsjustjose.criss_cross.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileLamp extends TileEntity
{
	private static int lampColor = 15;
	private static boolean isPowered = false;
	
	public void setColor(int color)
	{
		this.lampColor = color;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		this.lampColor = tag.getInteger("color");
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		tag.setInteger("color", this.lampColor);
	}
	
}
