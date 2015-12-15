package com.oitsjustjose.criss_cross.gui;

import com.oitsjustjose.criss_cross.container.ContainerCobblegen;
import com.oitsjustjose.criss_cross.container.ContainerCropomator;
import com.oitsjustjose.criss_cross.container.ContainerElectroextractor;
import com.oitsjustjose.criss_cross.container.ContainerPouch;
import com.oitsjustjose.criss_cross.container.ContainerScribe;
import com.oitsjustjose.criss_cross.container.ContainerStonegen;
import com.oitsjustjose.criss_cross.container.ContainerWoodchipper;
import com.oitsjustjose.criss_cross.items.ItemPouch;
import com.oitsjustjose.criss_cross.tileentity.TileCobblegen;
import com.oitsjustjose.criss_cross.tileentity.TileCropomator;
import com.oitsjustjose.criss_cross.tileentity.TileElectroextractor;
import com.oitsjustjose.criss_cross.tileentity.TileScribe;
import com.oitsjustjose.criss_cross.tileentity.TileStonegen;
import com.oitsjustjose.criss_cross.tileentity.TileWoodchipper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler
{
	public static final int Cropomator = 1;
	public static final int Electroextractor = 2;
	public static final int Woodchipper = 3;
	public static final int Cobblegen = 4;
	public static final int Stonegen = 5;
	public static final int Pouch = 6;
	public static final int Scribe = 7;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));

		if (tile instanceof TileCropomator)
		{
			TileCropomator TE = (TileCropomator) tile;

			return new ContainerCropomator(player, TE);
		}
		if (tile instanceof TileElectroextractor)
		{
			TileElectroextractor TE = (TileElectroextractor) tile;

			return new ContainerElectroextractor(player, TE);
		}
		if (tile instanceof TileWoodchipper)
		{
			TileWoodchipper TE = (TileWoodchipper) tile;

			return new ContainerWoodchipper(player, TE);
		}
		if (tile instanceof TileCobblegen)
		{
			TileCobblegen TE = (TileCobblegen) tile;

			return new ContainerCobblegen(player, TE);
		}
		if (tile instanceof TileStonegen)
		{
			TileStonegen TE = (TileStonegen) tile;

			return new ContainerStonegen(player, TE);
		}
		if (tile instanceof TileScribe)
		{
			TileScribe TE = (TileScribe) tile;

			return new ContainerScribe(player, TE);
		}

		if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemPouch)
		{
			return new ContainerPouch(player, player.inventory.currentItem);
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));

		if (tile instanceof TileCropomator)
		{
			TileCropomator TE = (TileCropomator) tile;

			return new GUICropomator(player, TE);
		}

		if (tile instanceof TileElectroextractor)
		{
			TileElectroextractor TE = (TileElectroextractor) tile;

			return new GUIElectroextractor(player, TE);
		}
		if (tile instanceof TileWoodchipper)
		{
			TileWoodchipper TE = (TileWoodchipper) tile;

			return new GUIWoodchipper(player, TE);
		}
		if (tile instanceof TileCobblegen)
		{
			TileCobblegen TE = (TileCobblegen) tile;

			return new GUICobblegen(player, TE);
		}
		if (tile instanceof TileStonegen)
		{
			TileStonegen TE = (TileStonegen) tile;

			return new GUIStonegen(player, TE);
		}
		if (tile instanceof TileScribe)
		{
			TileScribe TE = (TileScribe) tile;

			return new GUIScribe(player, TE);
		}
		if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemPouch)
		{
			return new GUIPouch(player, player.inventory.currentItem);
		}

		return null;
	}
}