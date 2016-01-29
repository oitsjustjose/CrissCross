package com.oitsjustjose.criss_cross.blocks;

import com.oitsjustjose.criss_cross.CrissCross;
import com.oitsjustjose.criss_cross.gui.GUIHandler;
import com.oitsjustjose.criss_cross.tileentity.TileStonegen;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class BlockStonegen extends BlockMachineBase
{
	private static String unlocName = "StoneGenerator";

	public BlockStonegen()
	{
		super(unlocName, Material.iron);
		GameRegistry.registerTileEntity(TileStonegen.class, unlocName);
		GameRegistry.registerBlock(this, unlocName.toLowerCase());
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this), "CPC", "#$#", "CPC", '$', "blockIron", 'C', "stone", 'P', Blocks.piston, '#', Items.stone_pickaxe));
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileStonegen();
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{

		TileEntity tileentity = world.getTileEntity(pos);

		if (tileentity instanceof TileStonegen)
		{
			InventoryHelper.dropInventoryItems(world, pos, (TileStonegen) tileentity);
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
			TileStonegen tile = (TileStonegen) world.getTileEntity(pos);
			if (tile != null)
				player.openGui(CrissCross.instance, GUIHandler.Stonegen, world, pos.getX(), pos.getY(), pos.getZ());
			return true;
		}
	}
}