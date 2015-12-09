package com.oitsjustjose.criss_cross.items;

import com.oitsjustjose.criss_cross.CrissCross;
import com.oitsjustjose.criss_cross.gui.GUIHandler;
import com.oitsjustjose.criss_cross.lib.Lib;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemPouch extends Item
{
	public ItemPouch()
	{
		this.setUnlocalizedName(Lib.modid + ".pouch");
		this.setCreativeTab(CrissCross.CCTab);
		GameRegistry.registerItem(this, this.getUnlocalizedName());
		Lib.add(this);
	}
	
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
    {
    	if(!world.isRemote)
    		player.openGui(CrissCross.instance, GUIHandler.Pouch, world, player.inventory.currentItem, 0, 0);
    	return itemstack;
    }
}
