package com.oitsjustjose.criss_cross.Items;

import java.util.List;

import com.oitsjustjose.criss_cross.CrissCross;
import com.oitsjustjose.criss_cross.Util.Reference;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemEverfulBucket extends Item
{
	String[] types = new String[]
	{
			"everfulBucket", "voidBucket"
	};
	@SideOnly(Side.CLIENT)
	IIcon[] icons = new IIcon[types.length];
	
	public ItemEverfulBucket()
	{
		this.setHasSubtypes(true);
		this.maxStackSize = 1;
		this.setCreativeTab(CrissCross.CCTab);
		GameRegistry.registerItem(this, "magicBucket");
		this.setContainerItem(this);
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta)
	{
		return icons[meta];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register)
	{
		for(int i = 0; i < icons.length; i++)
			icons[i] = register.registerIcon(Reference.modid + ":" + types[i]);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
	{
		MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);
		
		if(movingobjectposition == null)
		{
			return itemstack;
		}
		else
		{
			if(movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
			{
				int x = movingobjectposition.blockX;
				int y = movingobjectposition.blockY;
				int z = movingobjectposition.blockZ;
				
				if(!world.canMineBlock(player, x, y, z))
					return itemstack;
					
				if(!player.canPlayerEdit(x, y, z, movingobjectposition.sideHit, itemstack))
					return itemstack;
					
				if(itemstack.getItemDamage() == 1)
				{
					Material material = world.getBlock(x, y, z).getMaterial();
					int meta = world.getBlockMetadata(x, y, z);
					
					if(material == Material.water && meta == 0)
						world.setBlockToAir(x, y, z);
						
					if(material == Material.lava && meta == 0)
						world.setBlockToAir(x, y, z);
				}
				else
				{
					if(world.getBlock(x, y, z).getMaterial() == Material.lava
							|| world.getBlock(x, y, z).getMaterial() == Material.water)
						return itemstack;
						
					if(movingobjectposition.sideHit == 0)
						--y;
						
					if(movingobjectposition.sideHit == 1)
						++y;
						
					if(movingobjectposition.sideHit == 2)
						--z;
						
					if(movingobjectposition.sideHit == 3)
						++z;
						
					if(movingobjectposition.sideHit == 4)
						--x;
						
					if(movingobjectposition.sideHit == 5)
						++x;
						
					if(!player.canPlayerEdit(x, y, z, movingobjectposition.sideHit, itemstack))
						return itemstack;
						
					if(itemstack.getItemDamage() == 0)
						world.setBlock(x, y, z, Blocks.flowing_water);
				}
			}
		}
		
		return itemstack;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemStack)
	{
		return "item." + Reference.modid + "." + types[itemStack.getItemDamage()];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		for(int i = 0; i < types.length; i++)
			list.add(new ItemStack(item, 1, i));
	}
}