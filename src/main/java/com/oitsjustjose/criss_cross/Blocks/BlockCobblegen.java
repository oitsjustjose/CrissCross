package com.oitsjustjose.criss_cross.blocks;

import java.util.Random;

import com.oitsjustjose.criss_cross.CrissCross;
import com.oitsjustjose.criss_cross.gui.GUIHandler;
import com.oitsjustjose.criss_cross.tileentity.TileCobblegen;
import com.oitsjustjose.criss_cross.util.ClientProxy;
import com.oitsjustjose.criss_cross.util.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCobblegen extends BlockMachineBase
{
	private static String unlocName = "CobblestoneGenerator";

	public BlockCobblegen()
	{
		super(unlocName);
		GameRegistry.registerTileEntity(TileCobblegen.class, unlocName);
		GameRegistry.registerBlock(this, unlocName.toLowerCase());
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileCobblegen();
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{

		TileEntity tileentity = world.getTileEntity(pos);

		if (tileentity instanceof TileCobblegen)
		{
			InventoryHelper.dropInventoryItems(world, pos, (TileCobblegen) tileentity);
			world.updateComparatorOutputLevel(pos, this);
		}

		super.breakBlock(world, pos, state);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY,
			float hitZ)
	{
		if (world.isRemote)
		{
			return true;
		}
		else
		{
			TileCobblegen tile = (TileCobblegen) world.getTileEntity(pos);
			if (tile != null)
				player.openGui(CrissCross.instance, GUIHandler.Cobblegen, world, pos.getX(), pos.getY(), pos.getZ());
			return true;
		}
	}
}