package com.oitsjustjose.criss_cross.items;

import java.util.List;

import com.oitsjustjose.criss_cross.CrissCross;
import com.oitsjustjose.criss_cross.lib.Lib;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemModBuckets extends Item
{
	String[] types = new String[] { "everful_bucket", "void_bucket" };

	public ItemModBuckets()
	{
		this.setHasSubtypes(true);
		this.maxStackSize = 1;
		this.setCreativeTab(CrissCross.CCTab);
		this.setContainerItem(this);
		this.setUnlocalizedName(Lib.MODID + ".special_bucket");
		GameRegistry.registerItem(this, "special_bucket");
		Lib.add(this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack)
	{
		if (stack.getItemDamage() == 0)
			return true;
		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
	{
		MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);

		if (movingobjectposition == null)
			return itemstack;
		else if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
		{
			BlockPos blockpos = movingobjectposition.getBlockPos();
			IBlockState state = world.getBlockState(blockpos);

			if (!world.isBlockModifiable(player, blockpos))
				return itemstack;

			if (!player.canPlayerEdit(blockpos, movingobjectposition.sideHit, itemstack))
				return itemstack;

			if (itemstack.getItemDamage() == 1)
			{
				Material material = state.getBlock().getMaterial();
				int meta = state.getBlock().getMetaFromState(state);

				if (material.isLiquid() && meta == 0)
					if(world.setBlockToAir(blockpos))
						player.swingItem();
			}

			else
			{
				if (world.getBlockState(blockpos).getBlock().getMaterial() == Material.lava || world.getBlockState(blockpos).getBlock().getMaterial() == Material.water)
					return itemstack;

				int x = blockpos.getX();
				int y = blockpos.getY();
				int z = blockpos.getZ();

				if (movingobjectposition.sideHit == EnumFacing.DOWN)
					--y;

				if (movingobjectposition.sideHit == EnumFacing.UP)
					++y;

				if (movingobjectposition.sideHit == EnumFacing.WEST)
					--x;

				if (movingobjectposition.sideHit == EnumFacing.EAST)
					++x;

				if (movingobjectposition.sideHit == EnumFacing.NORTH)
					--z;

				if (movingobjectposition.sideHit == EnumFacing.SOUTH)
					++z;

				blockpos = new BlockPos(x, y, z);
				if (!player.canPlayerEdit(blockpos, movingobjectposition.sideHit, itemstack))
					return itemstack;

				if (itemstack.getItemDamage() == 0)
					if(tryPlaceWater(world, blockpos))
						player.swingItem();
			}
		}

		return itemstack;
	}

	public boolean tryPlaceWater(World worldIn, BlockPos pos)
	{
		Material material = worldIn.getBlockState(pos).getBlock().getMaterial();
		boolean flag = !material.isSolid();

		if (!worldIn.isAirBlock(pos) && !flag)
		{
			return false;
		}
		else
		{
			if (worldIn.provider.doesWaterVaporize())
			{
				int i = pos.getX();
				int j = pos.getY();
				int k = pos.getZ();
				worldIn.playSoundEffect((double) ((float) i + 0.5F), (double) ((float) j + 0.5F), (double) ((float) k + 0.5F), "random.fizz", 0.5F, 2.6F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8F);

				for (int l = 0; l < 8; ++l)
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, (double) i + Math.random(), (double) j + Math.random(), (double) k + Math.random(), 0.0D, 0.0D, 0.0D, new int[0]);
			}
			else
			{
				if (!worldIn.isRemote && flag && !material.isLiquid())
					worldIn.destroyBlock(pos, true);

				worldIn.setBlockState(pos, Blocks.flowing_water.getDefaultState(), 3);
			}
			return true;
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack)
	{
		return "item." + Lib.MODID + "." + types[itemStack.getItemDamage()];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		for (int i = 0; i < types.length; i++)
			list.add(new ItemStack(item, 1, i));
	}
}