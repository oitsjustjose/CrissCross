package com.oitsjustjose.criss_cross.blocks;

import com.oitsjustjose.criss_cross.CrissCross;
import com.oitsjustjose.criss_cross.gui.GUIHandler;
import com.oitsjustjose.criss_cross.lib.Lib;
import com.oitsjustjose.criss_cross.tileentity.TileWoodchipper;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockWoodchipper extends BlockMachineBase
{
	private static String unlocName = "WoodChipper";

	public BlockWoodchipper()
	{
		super(unlocName, Material.iron);
		GameRegistry.registerTileEntity(TileWoodchipper.class, unlocName);
		GameRegistry.registerBlock(this, unlocName.toLowerCase());
		Lib.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileWoodchipper();
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{

		TileEntity tileentity = world.getTileEntity(pos);

		if (tileentity instanceof TileWoodchipper)
		{
			InventoryHelper.dropInventoryItems(world, pos, (TileWoodchipper) tileentity);
			world.updateComparatorOutputLevel(pos, this);
		}

		super.breakBlock(world, pos, state);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (world.isRemote)
			return true;
		else
		{
			TileWoodchipper tile = (TileWoodchipper) world.getTileEntity(pos);
			if (tile != null)
				player.openGui(CrissCross.instance, GUIHandler.Woodchipper, world, pos.getX(), pos.getY(), pos.getZ());
			return true;
		}
	}
}