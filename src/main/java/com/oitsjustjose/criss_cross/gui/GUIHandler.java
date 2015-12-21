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
	public static final int Cropomator = 0;
	public static final int Electroextractor = 1;
	public static final int Woodchipper = 2;
	public static final int Cobblegen = 3;
	public static final int Stonegen = 4;
	public static final int Pouch = 5;
	public static final int Scribe = 6;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));

		if (tile != null)
		{
			System.out.println(ID);
			switch (ID)
			{
			case 0: // Cropomator
				return new ContainerCropomator(player, ((TileCropomator) tile));
			case 1: // Electroextractor
				return new ContainerElectroextractor(player, ((TileElectroextractor) tile));
			case 2: // Wood Chipper
				return new ContainerWoodchipper(player, ((TileWoodchipper) tile));
			case 3: // Cobblestone generator
				return new ContainerCobblegen(player, ((TileCobblegen) tile));
			case 4: // Stone generator
				return new ContainerStonegen(player, ((TileStonegen) tile));
			case 6: // Scribe
				return new ContainerScribe(player, ((TileScribe) tile));
			}
		}

		if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemPouch)
			return new ContainerPouch(player, player.inventory.currentItem);

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));

		if (tile != null)
		{
			switch (ID)
			{
			case 0: // Cropomator
				return new GUICropomator(player, ((TileCropomator) tile));
			case 1: // Electroextractor
				return new GUIElectroextractor(player, ((TileElectroextractor) tile));
			case 2: // Wood Chipper
				return new GUIWoodchipper(player, ((TileWoodchipper) tile));
			case 3: // Cobblestone generator
				return new GUICobblegen(player, ((TileCobblegen) tile));
			case 4: // Stone generator
				return new GUIStonegen(player, ((TileStonegen) tile));
			case 6: // Scribe
				return new GUIScribe(player, ((TileScribe) tile));
			}
		}

		if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemPouch)
			return new GUIPouch(player, player.inventory.currentItem);

		return null;
	}
}