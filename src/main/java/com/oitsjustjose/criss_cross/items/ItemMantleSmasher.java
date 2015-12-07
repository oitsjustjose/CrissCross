package com.oitsjustjose.criss_cross.items;

import com.oitsjustjose.criss_cross.CrissCross;
import com.oitsjustjose.criss_cross.util.Lib;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMantleSmasher extends ItemPickaxe
{
	public ItemMantleSmasher(ToolMaterial material)
	{
		super(material);
		this.setUnlocalizedName(Lib.modid + ".mantleSmasher_" + material.name());
		this.setCreativeTab(CrissCross.CCTab);
		GameRegistry.registerItem(this, this.getUnlocalizedName());
		Lib.add(this);
	}

	@Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }
	
	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player)
	{
		World world = player.getEntityWorld();

		if (!world.isRemote && !player.isSneaking())
		{
			if (world.getBlockState(pos).getBlock().getMaterial() != Material.rock)
				return super.onBlockStartBreak(itemstack, pos, player);

			MovingObjectPosition movObjPos = raytraceFromEntity(world, player, false, 4.5d);
			EnumFacing side = movObjPos.sideHit;

			if(side == null)
				return super.onBlockStartBreak(itemstack, pos, player);
			
			if (itemstack.getItem() instanceof ItemMantleSmasher)
			{
				if (side == EnumFacing.NORTH || side == EnumFacing.SOUTH)
				{
					breakBlock(world, player, pos.up().west());
					breakBlock(world, player, pos.up());
					breakBlock(world, player, pos.up().east());
					breakBlock(world, player, pos.west());
					breakBlock(world, player, pos.east());
					breakBlock(world, player, pos.down().west());
					breakBlock(world, player, pos.down());
					breakBlock(world, player, pos.down().east());
					return super.onBlockStartBreak(itemstack, pos, player);

				}
				else if (side == EnumFacing.EAST || side == EnumFacing.WEST)
				{
					breakBlock(world, player, pos.up().north());
					breakBlock(world, player, pos.up());
					breakBlock(world, player, pos.up().south());
					breakBlock(world, player, pos.north());
					breakBlock(world, player, pos.south());
					breakBlock(world, player, pos.down().north());
					breakBlock(world, player, pos.down());
					breakBlock(world, player, pos.down().south());
					return super.onBlockStartBreak(itemstack, pos, player);

				}
				else if (side == EnumFacing.UP || side == EnumFacing.DOWN)
				{
					breakBlock(world, player, pos.north().east());
					breakBlock(world, player, pos.north());
					breakBlock(world, player, pos.north().west());
					breakBlock(world, player, pos.east());
					breakBlock(world, player, pos.west());
					breakBlock(world, player, pos.south().east());
					breakBlock(world, player, pos.south());
					breakBlock(world, player, pos.south().west());
					return super.onBlockStartBreak(itemstack, pos, player);
				}
			}
		}
		return super.onBlockStartBreak(itemstack, pos, player);
	}

	void breakBlock(World world, EntityPlayer player, BlockPos pos)
	{
		Block block = world.getBlockState(pos).getBlock();
		IBlockState state = world.getBlockState(pos);

		if (block.getMaterial() == Material.rock)
		{
			block.onBlockHarvested(world, pos, state, player);
			if (block.removedByPlayer(world, pos, player, true))
			{
				block.onBlockDestroyedByPlayer(world, pos, state);
				block.harvestBlock(world, player, pos, state, world.getTileEntity(pos));
			}
			world.playAuxSFXAtEntity(player, Block.getIdFromBlock(block), pos, 1);
			world.setBlockToAir(pos);
		}
	}

	public static MovingObjectPosition raytraceFromEntity(World world, Entity player, boolean par3, double range)
	{
		float f = 1.0F;
		float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
		float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
		double d0 = player.prevPosX + (player.posX - player.prevPosX) * (double) f;
		double d1 = player.prevPosY + (player.posY - player.prevPosY) * (double) f;
		if (!world.isRemote && player instanceof EntityPlayer)
			d1 += 1.62D;
		double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * (double) f;
		Vec3 vec3 = new Vec3(d0, d1, d2);
		float f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
		float f4 = MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
		float f5 = -MathHelper.cos(-f1 * 0.017453292F);
		float f6 = MathHelper.sin(-f1 * 0.017453292F);
		float f7 = f4 * f5;
		float f8 = f3 * f5;
		double d3 = range;
		if (player instanceof EntityPlayerMP)
		{
			d3 = ((EntityPlayerMP) player).theItemInWorldManager.getBlockReachDistance();
		}
		Vec3 vec31 = vec3.addVector((double) f7 * d3, (double) f6 * d3, (double) f8 * d3);
		return world.rayTraceBlocks(vec3, vec31, par3, !par3, par3);
	}
}
