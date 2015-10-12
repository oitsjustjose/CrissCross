package com.oitsjustjose.criss_cross.GUI;

import com.oitsjustjose.criss_cross.Container.ContainerCropomator;
import com.oitsjustjose.criss_cross.Container.ContainerElectroextractor;
import com.oitsjustjose.criss_cross.TileEntity.TileEntityCropomator;
import com.oitsjustjose.criss_cross.TileEntity.TileEntityElectroextractor;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GUIHandler implements IGuiHandler
{
	public static final int Cropomator = 1;
	public static final int Electroextractor = 2;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		
		if(tile instanceof TileEntityCropomator)
		{
			TileEntityCropomator tileCropomator = (TileEntityCropomator)tile;
			
			return new ContainerCropomator(player, tileCropomator);
		}
		if(tile instanceof TileEntityElectroextractor)
		{
			TileEntityElectroextractor tileExt = (TileEntityElectroextractor)tile;
			
			return new ContainerElectroextractor(player, tileExt);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		
		if(tile instanceof TileEntityCropomator)
		{
			TileEntityCropomator tileCropomator = (TileEntityCropomator)tile;
			
			return new GUICropomator(player, tileCropomator);
		}
		
		if(tile instanceof TileEntityElectroextractor)
		{
			TileEntityElectroextractor tileExt = (TileEntityElectroextractor)tile;
			
			return new GUIElectroextractor(player, tileExt);
		}
		return null;
	}
}