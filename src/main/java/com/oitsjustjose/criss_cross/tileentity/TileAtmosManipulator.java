package com.oitsjustjose.criss_cross.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.world.storage.WorldInfo;

public class TileAtmosManipulator extends TileEntity implements ITickable
{
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return this.worldObj.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void update()
	{
		if (worldObj.isRemote)
			System.out.println(worldObj.isRaining());
	}

	public void setRain()
	{
		EntityPlayer player = worldObj.getClosestPlayer(this.pos.getX(), this.pos.getY(), this.pos.getZ(), 3);
		worldObj.getWorldInfo().setRaining(true);

		if (worldObj.isRemote)
		{
			worldObj.setRainStrength(1.0F);
			worldObj.setThunderStrength(1.0f);
		}

		worldObj.getWorldInfo().setThunderTime(0);
		worldObj.getWorldInfo().setThundering(true);
	}

	public void setSun()
	{
		worldObj.getWorldInfo().setRaining(false);

		if (worldObj.isRemote)
		{
			worldObj.setRainStrength(0.0F);
			worldObj.setThunderStrength(1.0f);
		}

		worldObj.getWorldInfo().setThunderTime(0);
		worldObj.getWorldInfo().setThundering(false);
	}
}
