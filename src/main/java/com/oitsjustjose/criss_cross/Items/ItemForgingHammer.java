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

public class ItemForgingHammer extends Item
{
	@SideOnly(Side.CLIENT)
	IIcon icon;
	
	private String unlocName;
	
	public ItemForgingHammer()
	{
		unlocName = "forgingHammer";
		this.setContainerItem(this);
		this.maxStackSize = 1;
		this.setMaxDamage(3);
		this.setCreativeTab(CrissCross.CCTab);
		GameRegistry.registerItem(this, unlocName);
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta)
	{
		return this.icon;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register)
	{
		icon = register.registerIcon(Reference.modid + ":" + this.unlocName);
	}
}