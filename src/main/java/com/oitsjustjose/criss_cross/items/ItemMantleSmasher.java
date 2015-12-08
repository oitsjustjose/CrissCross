package com.oitsjustjose.criss_cross.items;

import com.oitsjustjose.criss_cross.CrissCross;
import com.oitsjustjose.criss_cross.event.ToolEfficiencyEvent;
import com.oitsjustjose.criss_cross.lib.Lib;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemMantleSmasher extends ItemPickaxe
{
	public ItemMantleSmasher(ToolMaterial material)
	{
		super(material);
		this.setUnlocalizedName(Lib.modid + ".mantleSmasher_" + material.name());
		this.setCreativeTab(CrissCross.CCTab);
		MinecraftForge.EVENT_BUS.register(new ToolEfficiencyEvent());
		GameRegistry.registerItem(this, this.getUnlocalizedName());
		Lib.add(this);
	}

	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player)
	{
		World world = player.getEntityWorld();

		if (!world.isRemote && !player.isSneaking())
		{
			Material m = world.getBlockState(pos).getBlock().getMaterial();
			
			if (m != Material.ground)
				if(m != Material.grass)
					if(m != Material.sand)
						if(m != Material.rock)
							return super.onBlockStartBreak(itemstack, pos, player);

			EnumFacing side = this.getMovingObjectPositionFromPlayer(world, player, true).sideHit;

			if (side == null)
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
				}
			}
		}
		return super.onBlockStartBreak(itemstack, pos, player);
	}

	void breakBlock(World world, EntityPlayer player, BlockPos pos)
	{
		Block block = world.getBlockState(pos).getBlock();
		IBlockState state = world.getBlockState(pos);
		
		Material m = block.getMaterial();

		if (m == Material.ground || m == Material.grass || m == Material.sand || m == Material.rock)
		{
			if (block.getBlockHardness(world, pos) != -1.0F)
			{
				block.onBlockHarvested(world, pos, state, player);
				if (block.removedByPlayer(world, pos, player, true))
				{
					block.onBlockDestroyedByPlayer(world, pos, state);
					block.harvestBlock(world, player, pos, state, world.getTileEntity(pos));
					player.getHeldItem().attemptDamageItem(1, player.getRNG());
					world.playAuxSFX(2001, pos, Block.getIdFromBlock(block) + (block.getMetaFromState(state) << 12));
				}
			}
		}

	}
}