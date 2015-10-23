package com.oitsjustjose.criss_cross.Blocks;

import com.oitsjustjose.criss_cross.CrissCross;
import com.oitsjustjose.criss_cross.Util.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMachineBase extends BlockContainer
{
	IIcon[] blockTextures = new IIcon[4];
	private static String unlocalizedName;
	private static boolean isActive = false;

	public BlockMachineBase(String unlocName)
	{
		super(Material.rock); // 0 - Top
		this.setHardness(2.0F); // 1 - Bottom
		this.setResistance(2.0F); // 3 - Front
		this.setStepSound(soundTypeMetal); // 4 - Sides
		this.setBlockName(Reference.modid + "." + unlocName);
		this.setCreativeTab(CrissCross.CCTab);

		this.unlocalizedName = unlocName;
	}

	// Used for the ItemStack vs. In-world Rendering
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if (side == 0)
			return blockTextures[0];
		if (side == 1)
			return blockTextures[1];
		if (side == 3)
			return blockTextures[2];

		return blockTextures[3];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side)
	{
		if (side == 0)
			return blockTextures[0];
		if (side == 1)
			return blockTextures[1];
		if (side == access.getBlockMetadata(x, y, z))
			return blockTextures[2];

		return blockTextures[3];
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z)
	{
		super.onBlockAdded(world, x, y, z);
		setMetadataForFront(world, x, y, z);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemstack)
	{
		int l = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

		if (l == 0)
		{
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}

		if (l == 1)
		{
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}

		if (l == 2)
		{
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}

		if (l == 3)
		{
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
	}

	private void setMetadataForFront(World world, int x, int y, int z)
	{
		if (!world.isRemote)
		{
			Block block = world.getBlock(x, y, z - 1);
			Block block1 = world.getBlock(x, y, z + 1);
			Block block2 = world.getBlock(x - 1, y, z);
			Block block3 = world.getBlock(x + 1, y, z);
			byte b0 = 3;

			if (block.func_149730_j() && !block1.func_149730_j())
			{
				b0 = 3;
			}

			if (block1.func_149730_j() && !block.func_149730_j())
			{
				b0 = 2;
			}

			if (block2.func_149730_j() && !block3.func_149730_j())
			{
				b0 = 5;
			}

			if (block3.func_149730_j() && !block2.func_149730_j())
			{
				b0 = 4;
			}

			world.setBlockMetadataWithNotify(x, y, z, b0, 2);
		}
	}

	public static void updateBlockState(boolean active, World world, int x, int y, int z)
	{
		isActive = active;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return null;
	}
}