package com.oitsjustjose.criss_cross.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileAtmosManipulator extends TileEntity
{
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return this.worldObj.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
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
		EntityPlayer player = worldObj.getClosestPlayer(this.pos.getX(), this.pos.getY(), this.pos.getZ(), 3);
		if (player.inventory.hasItem(Items.lava_bucket))
		{
			worldObj.getWorldInfo().setRaining(false);

			if (worldObj.isRemote)
			{
				worldObj.setRainStrength(0.0F);
				worldObj.setThunderStrength(0.0f);
			}

			worldObj.getWorldInfo().setThunderTime(0);
			worldObj.getWorldInfo().setThundering(false);
		}
	}
}
