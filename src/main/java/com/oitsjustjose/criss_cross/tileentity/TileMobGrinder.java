package com.oitsjustjose.criss_cross.tileentity;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

public class TileMobGrinder extends TileEntity implements ITickable
{
	int facing;

	public TileMobGrinder(int dir)
	{
		super();
		this.facing = dir;
	}

	@Override
	public void update()
	{
		World world = this.worldObj;

		if (!world.isRemote)
		{

		}
	}

	public AxisAlignedBB getAABBForFacing(BlockPos pos, int facing)
	{
		switch (facing)
		{
		case 2:
			return new AxisAlignedBB(pos, pos.north(3));
		case 3:
			return new AxisAlignedBB(pos, pos.south(3));
		case 4:
			return new AxisAlignedBB(pos, pos.west(3));
		case 5:
			return new AxisAlignedBB(pos, pos.east(3));
		default:
			return new AxisAlignedBB(pos, pos);

		}
	}
}
