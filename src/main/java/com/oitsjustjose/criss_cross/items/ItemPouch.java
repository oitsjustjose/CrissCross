package com.oitsjustjose.criss_cross.items;

import java.util.List;

import com.oitsjustjose.criss_cross.CrissCross;
import com.oitsjustjose.criss_cross.gui.GUIHandler;
import com.oitsjustjose.criss_cross.lib.Lib;
import com.oitsjustjose.criss_cross.util.ColorUtils;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemPouch extends Item
{
	public ItemPouch()
	{
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
		this.setUnlocalizedName(Lib.modid + ".pouch");
		this.setCreativeTab(CrissCross.CCTab);
		GameRegistry.registerItem(this, this.getUnlocalizedName());
		Lib.add(this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass)
	{
		return ColorUtils.getColorMultiplierFromMeta(stack.getMetadata());
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
	{
		if (!world.isRemote)
			player.openGui(CrissCross.instance, GUIHandler.Pouch, world, player.inventory.currentItem, 0, 0);
		return itemstack;
	}

	@Override
	public String getUnlocalizedNameInefficiently(ItemStack itemstack)
	{
		String unlocName = StatCollector.translateToLocal("item.CrissCross.pouch.name");
		String colorName = EnumDyeColor.byDyeDamage(itemstack.getItemDamage()).getUnlocalizedName();
		colorName = colorName.substring(0, 1).toUpperCase() + colorName.substring(1, colorName.length());

		return ColorUtils.findFormatForMeta(itemstack.getItemDamage()) + colorName + " " + unlocName;
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack)
	{
		return this.getUnlocalizedNameInefficiently(itemstack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		for (int i = 0; i < 16; i++)
			list.add(new ItemStack(item, 1, i));
	}
}
