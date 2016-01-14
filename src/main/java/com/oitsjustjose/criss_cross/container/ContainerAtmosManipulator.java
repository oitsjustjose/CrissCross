package com.oitsjustjose.criss_cross.container;

import com.oitsjustjose.criss_cross.tileentity.TileAtmosManipulator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;

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

	@Override
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
