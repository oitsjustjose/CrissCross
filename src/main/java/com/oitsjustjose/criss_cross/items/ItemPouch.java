package com.oitsjustjose.criss_cross.items;

import com.oitsjustjose.criss_cross.CrissCross;
import com.oitsjustjose.criss_cross.event.PouchCleanEvent;
import com.oitsjustjose.criss_cross.gui.GUIHandler;
import com.oitsjustjose.criss_cross.lib.Lib;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemPouch extends Item
{
	public ItemPouch()
	{
		this.setMaxStackSize(1);
		this.setUnlocalizedName(Lib.MODID + ".pouch");
		this.setCreativeTab(CrissCross.CCTab);
		GameRegistry.registerItem(this, "pouch");
		MinecraftForge.EVENT_BUS.register(new PouchCleanEvent());
		Lib.add(this);
	}

	// Dye specific code below: allows for custom pouch colors, not just your standard 16 colors
	public boolean hasColor(ItemStack stack)
	{
		return (!stack.hasTagCompound() ? false : (!stack.getTagCompound().hasKey("display", 10) ? false : stack.getTagCompound().getCompoundTag("display").hasKey("color", 3)));
	}

	public void setColor(ItemStack stack, int color)
	{
		NBTTagCompound nbttagcompound = stack.getTagCompound();

		if (nbttagcompound == null)
		{
			nbttagcompound = new NBTTagCompound();
			stack.setTagCompound(nbttagcompound);
		}

		NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

		if (!nbttagcompound.hasKey("display", 10))
			nbttagcompound.setTag("display", nbttagcompound1);

		nbttagcompound1.setInteger("color", color);
	}

	public void removeColor(ItemStack stack)
	{
		NBTTagCompound nbttagcompound = stack.getTagCompound();

		if (nbttagcompound != null)
		{
			NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
			if (nbttagcompound1.hasKey("color"))
				nbttagcompound1.removeTag("color");
		}
	}

	public int getColor(ItemStack stack)
	{
		NBTTagCompound nbttagcompound = stack.getTagCompound();

		if (nbttagcompound != null)
		{
			NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
			if (nbttagcompound1 != null && nbttagcompound1.hasKey("color", 3))
				return nbttagcompound1.getInteger("color");
		}
		return 10511680;
	}

	// From here down is just back to normal stuff

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass)
	{
		if (pass == 0)
		{
			int i = this.getColor(stack);

			if (i < 0)
				i = 16777215;

			return i;
		}
		else
			return 16777215;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
	{
		if (!world.isRemote)
		{
			world.playSoundEffect(player.posX, player.posY, player.posZ, Block.soundTypeCloth.getPlaceSound(), 1.0F, 1.0F);
			player.openGui(CrissCross.instance, GUIHandler.Pouch, world, player.inventory.currentItem, 0, 0);
		}
		return itemstack;
	}
}
