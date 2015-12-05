package com.oitsjustjose.criss_cross.Blocks;

import com.oitsjustjose.criss_cross.CrissCross;
import com.oitsjustjose.criss_cross.Util.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMachineBase extends BlockContainer
{
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	private static String unlocalizedName;
	private static boolean isActive = false;

	public BlockMachineBase(String unlocName)
	{
		super(Material.rock);
		this.setHardness(2.0F);
		this.setResistance(2.0F);
		this.setStepSound(soundTypeMetal);
		this.setUnlocalizedName(Reference.modid + "." + unlocName);
		this.setCreativeTab(CrissCross.CCTab);
		this.unlocalizedName = unlocName;
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		Reference.add(this);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.CUTOUT;
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		setDefaultFacing(world, pos, state);
	}
	
	@Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }
	
	@Override
    public int getRenderType()
    {
        return 3;
    }
	
    @SideOnly(Side.CLIENT)
    public IBlockState getStateForEntityRender(IBlockState state)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.SOUTH);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }

    @Override
    protected BlockState createBlockState()
    {
        return new BlockState(this, new IProperty[] {FACING});
    }

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack itemstack)
	{
        world.setBlockState(pos, state.withProperty(FACING, entity.getHorizontalFacing().getOpposite()), 2);

		int l = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

		if (l == 0)
		{
			world.setBlockState(pos, state.getBlock().getStateFromMeta(2), 2);
		}

		if (l == 1)
		{
			world.setBlockState(pos, state.getBlock().getStateFromMeta(5), 2);
		}

		if (l == 2)
		{
			world.setBlockState(pos, state.getBlock().getStateFromMeta(3), 2);
		}

		if (l == 3)
		{
			world.setBlockState(pos, state.getBlock().getStateFromMeta(4), 2);
		}
	}
	
	private void setDefaultFacing(World world, BlockPos pos, IBlockState state)
	{
		if (!world.isRemote)
		{
			Block block = world.getBlockState(pos.north()).getBlock();
			Block block1 = world.getBlockState(pos.south()).getBlock();
			Block block2 = world.getBlockState(pos.west()).getBlock();
			Block block3 = world.getBlockState(pos.east()).getBlock();
			EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);
			if (enumfacing == EnumFacing.NORTH && block.isFullBlock() && !block1.isFullBlock())
			{
				enumfacing = EnumFacing.SOUTH;
			}
			else
				if (enumfacing == EnumFacing.SOUTH && block1.isFullBlock() && !block.isFullBlock())
				{
					enumfacing = EnumFacing.NORTH;
				}
				else
					if (enumfacing == EnumFacing.WEST && block2.isFullBlock() && !block3.isFullBlock())
					{
						enumfacing = EnumFacing.EAST;
					}
					else
						if (enumfacing == EnumFacing.EAST && block3.isFullBlock() && !block2.isFullBlock())
						{
							enumfacing = EnumFacing.WEST;
						}

			world.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
		}
	}

	public static void updateBlockState(boolean active, World world, BlockPos pos)
	{
		isActive = active;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return null;
	}
}