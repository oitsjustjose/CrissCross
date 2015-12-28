package com.oitsjustjose.criss_cross.container;

import com.oitsjustjose.criss_cross.tileentity.TileAtmosManipulator;
import com.oitsjustjose.criss_cross.tileentity.TileCobblegen;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAtmosManipulator extends Container
{
	TileAtmosManipulator tileAM;
	EntityPlayer player;

	public ContainerAtmosManipulator(EntityPlayer player, TileAtmosManipulator tile)
	{
		super();
		this.tileAM = tile;
		this.player = player;
	}


	public void onCraftGuiOpened(ICrafting crafting)
	{
		super.onCraftGuiOpened(crafting);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return this.tileAM.isUseableByPlayer(this.player);
	}
}
