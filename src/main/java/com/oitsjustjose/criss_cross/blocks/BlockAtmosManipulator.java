package com.oitsjustjose.criss_cross.blocks;

import com.oitsjustjose.criss_cross.CrissCross;
import com.oitsjustjose.criss_cross.gui.GUIHandler;
import com.oitsjustjose.criss_cross.tileentity.TileAtmosManipulator;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockAtmosManipulator extends BlockMachineBase
{
	private static String unlocName = "AtmosManipulator";
	public BlockAtmosManipulator()
	{
		super(unlocName, Material.iron);
		GameRegistry.registerTileEntity(TileAtmosManipulator.class, unlocName);
		GameRegistry.registerBlock(this, unlocName.toLowerCase());
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileAtmosManipulator();
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if(world.isRemote)
		{
			return true;
		}
		else
		{
			TileAtmosManipulator tile = (TileAtmosManipulator) world.getTileEntity(pos);
			if(tile != null)
				player.openGui(CrissCross.instance, GUIHandler.AtmosManipulator, world, pos.getX(), pos.getY(), pos.getZ());
			return true;
		}
	}
}
