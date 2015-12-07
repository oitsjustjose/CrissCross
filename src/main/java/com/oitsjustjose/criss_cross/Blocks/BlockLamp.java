package com.oitsjustjose.criss_cross.blocks;

import java.util.List;
import java.util.Random;

import com.oitsjustjose.criss_cross.CrissCross;
import com.oitsjustjose.criss_cross.items.block.ItemBlockLamp;
import com.oitsjustjose.criss_cross.util.Lib;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockLamp extends Block
{
	public static final PropertyEnum<EnumDyeColor> COLOR = PropertyEnum.<EnumDyeColor> create("color", EnumDyeColor.class);
	public static final PropertyBool POWERED = PropertyBool.create("powered");
	private static String unlocalizedName = "smartLamp";

	public BlockLamp()
	{
		super(Material.rock);
		this.setHardness(2.0F);
		this.setResistance(2.0F);
		this.setStepSound(soundTypeMetal);
		this.setUnlocalizedName(Lib.modid + "." + unlocalizedName);
		this.setCreativeTab(CrissCross.CCTab);
		this.setDefaultState(this.blockState.getBaseState().withProperty(COLOR, EnumDyeColor.WHITE).withProperty(POWERED, false));
		GameRegistry.registerBlock(this, ItemBlockLamp.class, unlocalizedName.toLowerCase());
		Lib.add(this);
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		int meta = state.getBlock().getMetaFromState(state);
		if (!world.isRemote)
		{
			if (meta > 15 && !world.isBlockPowered(pos))
				world.setBlockState(pos, CCBlocks.smartLamp.getStateFromMeta(meta - 16).withProperty(POWERED, false), 2);
			else if (meta < 16 && world.isBlockPowered(pos))
				world.setBlockState(pos, CCBlocks.smartLamp.getStateFromMeta(meta + 16).withProperty(POWERED, true));
		}
	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock)
	{
		int meta = state.getBlock().getMetaFromState(state);
		if (!world.isRemote)
		{
			if (meta > 15 && !world.isBlockPowered(pos))
				world.scheduleUpdate(pos, this, 4);
			else if (meta < 16 && world.isBlockPowered(pos))
				world.setBlockState(pos, CCBlocks.smartLamp.getStateFromMeta(meta + 16).withProperty(POWERED, true));
		}
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		int meta = state.getBlock().getMetaFromState(state);
		if (!world.isRemote)
			if (!world.isBlockPowered(pos) && meta > 15)
			{
				world.setBlockState(pos, CCBlocks.smartLamp.getStateFromMeta(meta - 16).withProperty(POWERED, false));
			}
	}

//	@Override
//	public int getLightValue(IBlockAccess world, BlockPos pos)
//	{
//		Block block = world.getBlockState(pos).getBlock();
//		boolean p = world.getBlockState(pos).getProperties()
//		
//		
//		if (block != this)
//			return block.getLightValue(world, pos);
//		else if (meta > 15)
//		{
//			return 15;
//		}
//		return 0;
//	}

	public int damageDropped(IBlockState state)
	{
		if (state.getBlock().getMetaFromState(state) > 15)
			return ((EnumDyeColor) state.getValue(COLOR)).getMetadata() - 16;
		else
			return ((EnumDyeColor) state.getValue(COLOR)).getMetadata();
	}

	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
	{
		for (EnumDyeColor enumdyecolor : EnumDyeColor.values())
			list.add(new ItemStack(itemIn, 1, enumdyecolor.getMetadata()));
	}

	@Override
	public MapColor getMapColor(IBlockState state)
	{
		return ((EnumDyeColor) state.getValue(COLOR)).getMapColor();
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(COLOR, EnumDyeColor.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		if (state.getValue(POWERED))
			return ((EnumDyeColor) state.getValue(COLOR)).getMetadata() + 16;
		else
			return ((EnumDyeColor) state.getValue(COLOR)).getMetadata();

	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		int meta = state.getBlock().getMetaFromState(state);
		return state.withProperty(POWERED, Boolean.valueOf(meta > 15)).withProperty(COLOR, EnumDyeColor.byMetadata(meta));
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] { COLOR, POWERED });
	}
}