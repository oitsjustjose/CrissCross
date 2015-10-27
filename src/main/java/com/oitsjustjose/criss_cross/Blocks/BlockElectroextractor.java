package com.oitsjustjose.criss_cross.Blocks;

import java.util.Random;

import com.oitsjustjose.criss_cross.CrissCross;
import com.oitsjustjose.criss_cross.GUI.GUIHandler;
import com.oitsjustjose.criss_cross.TileEntity.TileEntityElectroextractor;
import com.oitsjustjose.criss_cross.TileEntity.TileEntityElectroextractor;
import com.oitsjustjose.criss_cross.Util.Reference;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockElectroextractor extends BlockMachineBase
{
	private static String unlocName = "ElectroExtractor";
	
	public BlockElectroextractor()
	{
		super(unlocName);
		GameRegistry.registerTileEntity(TileEntityElectroextractor.class, unlocName);
		GameRegistry.registerBlock(this, unlocName);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register)
	{
		blockTextures[0] = register.registerIcon(Reference.modid + ":" + this.unlocName + "/" + unlocName + "_Bottom");
		blockTextures[1] = register.registerIcon(Reference.modid + ":" + this.unlocName + "/" + unlocName + "_Top");
		blockTextures[2] = register.registerIcon(Reference.modid + ":" + this.unlocName + "/" + unlocName + "_Front");
		blockTextures[3] = register.registerIcon(Reference.modid + ":" + this.unlocName + "/" + unlocName + "_Sides");
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityElectroextractor();
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
	{
		Random random = new Random();
		TileEntityElectroextractor tile = (TileEntityElectroextractor) world.getTileEntity(x, y, z);
		
		if(tile != null)
		{
			for(int i = 0; i < tile.getSizeInventory(); ++i)
			{
				ItemStack itemstack = tile.getStackInSlot(i);
				
				if(itemstack != null)
				{
					float f = random.nextFloat() * 0.8F + 0.1F;
					float f1 = random.nextFloat() * 0.8F + 0.1F;
					float f2 = random.nextFloat() * 0.8F + 0.1F;
					
					while(itemstack.stackSize > 0)
					{
						int j = random.nextInt(21) + 10;
						
						if(j > itemstack.stackSize)
						{
							j = itemstack.stackSize;
						}
						
						itemstack.stackSize -= j;
						EntityItem entityitem = new EntityItem(world, (double) ((float) x + f),
								(double) ((float) y + f1), (double) ((float) z + f2),
								new ItemStack(itemstack.getItem(), j, itemstack.getItemDamage()));
								
						if(itemstack.hasTagCompound())
						{
							entityitem.getEntityItem()
									.setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
						}
						
						float f3 = 0.05F;
						entityitem.motionX = (double) ((float) random.nextGaussian() * f3);
						entityitem.motionY = (double) ((float) random.nextGaussian() * f3 + 0.2F);
						entityitem.motionZ = (double) ((float) random.nextGaussian() * f3);
						world.spawnEntityInWorld(entityitem);
					}
				}
			}
			
			world.func_147453_f(x, y, z, block);
			
		}
		
		super.breakBlock(world, x, y, z, block, meta);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX,
			float hitY, float hitZ)
	{
		if(world.isRemote)
		{
			return true;
		}
		else
		{
			TileEntityElectroextractor tileext = (TileEntityElectroextractor) world.getTileEntity(x, y, z);
			if(tileext != null)
				player.openGui(CrissCross.instance, GUIHandler.Electroextractor, world, x, y, z);
			return true;
		}
	}
}