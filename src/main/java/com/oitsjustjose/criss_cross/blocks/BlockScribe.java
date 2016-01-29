package com.oitsjustjose.criss_cross.blocks;

import java.util.Random;

import com.oitsjustjose.criss_cross.CrissCross;
import com.oitsjustjose.criss_cross.gui.GUIHandler;
import com.oitsjustjose.criss_cross.tileentity.TileScribe;

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
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class BlockScribe extends BlockMachineBase
{
	private static String unlocName = "Scribe";

	public BlockScribe()
	{
		super(unlocName, Material.iron);
		GameRegistry.registerTileEntity(TileScribe.class, unlocName);
		GameRegistry.registerBlock(this, unlocName.toLowerCase());
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this), "BWB", "FES", "BWB", 'B', Items.book, 'W', "plankWood", 'F', Items.feather, 'S', "dyeBlack", 'E', Blocks.enchanting_table));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		super.randomDisplayTick(worldIn, pos, state, rand);

		for (int i = -2; i <= 2; ++i)
			for (int j = -2; j <= 2; ++j)
			{
				if (i > -2 && i < 2 && j == -1)
					j = 2;

				if (rand.nextInt(16) == 0)
					for (int k = 0; k <= 1; ++k)
					{
						if (!worldIn.isAirBlock(pos.add(i / 2, 0, j / 2)))
							break;
						worldIn.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, pos.getX() + 0.5D, pos.getY() + 2.0D, pos.getZ() + 0.5D, i + rand.nextFloat() - 0.5D, k - rand.nextFloat() - 1.0F, j + rand.nextFloat() - 0.5D, new int[0]);
					}
			}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileScribe();
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{

		TileEntity tileentity = world.getTileEntity(pos);

		if (tileentity instanceof TileScribe)
		{
			InventoryHelper.dropInventoryItems(world, pos, (TileScribe) tileentity);
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
			TileScribe tile = (TileScribe) world.getTileEntity(pos);
			if (tile != null)
				player.openGui(CrissCross.instance, GUIHandler.Scribe, world, pos.getX(), pos.getY(), pos.getZ());
			return true;
		}
	}
}