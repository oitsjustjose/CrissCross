package com.oitsjustjose.criss_cross.GUI;

import com.oitsjustjose.criss_cross.Container.ContainerCropomator;
import com.oitsjustjose.criss_cross.Container.ContainerElectroextractor;
import com.oitsjustjose.criss_cross.Container.ContainerStonegen;
import com.oitsjustjose.criss_cross.Container.ContainerWoodchipper;
import com.oitsjustjose.criss_cross.TileEntity.TileEntityCobblegen;
import com.oitsjustjose.criss_cross.TileEntity.TileEntityCropomator;
import com.oitsjustjose.criss_cross.TileEntity.TileEntityElectroextractor;
import com.oitsjustjose.criss_cross.TileEntity.TileEntityStonegen;
import com.oitsjustjose.criss_cross.TileEntity.TileEntityWoodchipper;

import cpw.mods.fml.common.network.IGuiHandler;
import com.oitsjustjose.criss_cross.Container.ContainerCobblegen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GUIHandler implements IGuiHandler
{
	public static final int Cropomator = 1;
	public static final int Electroextractor = 2;
	public static final int Woodchipper = 3;
	public static final int Cobblegen = 4;
	public static final int Stonegen = 5;
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		
		if(tile instanceof TileEntityCropomator)
		{
			TileEntityCropomator TE = (TileEntityCropomator) tile;
			
			return new ContainerCropomator(player, TE);
		}
		if(tile instanceof TileEntityElectroextractor)
		{
			TileEntityElectroextractor TE = (TileEntityElectroextractor) tile;
			
			return new ContainerElectroextractor(player, TE);
		}
		if(tile instanceof TileEntityWoodchipper)
		{
			TileEntityWoodchipper TE = (TileEntityWoodchipper) tile;
			
			return new ContainerWoodchipper(player, TE);
		}
		if(tile instanceof TileEntityCobblegen)
		{
			TileEntityCobblegen TE = (TileEntityCobblegen) tile;
			
			return new ContainerCobblegen(player, TE);
		}
		if(tile instanceof TileEntityStonegen)
		{
			TileEntityStonegen TE = (TileEntityStonegen) tile;
			
			return new ContainerStonegen(player, TE);
		}

		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		
		if(tile instanceof TileEntityCropomator)
		{
			TileEntityCropomator TE = (TileEntityCropomator) tile;
			
			return new GUICropomator(player, TE);
		}
		
		if(tile instanceof TileEntityElectroextractor)
		{
			TileEntityElectroextractor TE = (TileEntityElectroextractor) tile;
			
			return new GUIElectroextractor(player, TE);
		}
		if(tile instanceof TileEntityWoodchipper)
		{
			TileEntityWoodchipper TE = (TileEntityWoodchipper) tile;
			
			return new GUIWoodchipper(player, TE);
		}
		if(tile instanceof TileEntityCobblegen)
		{
			TileEntityCobblegen TE = (TileEntityCobblegen) tile;
			
			return new GUICobblegen(player, TE);
		}
		if(tile instanceof TileEntityStonegen)
		{
			TileEntityStonegen TE = (TileEntityStonegen) tile;
			
			return new GUIStonegen(player, TE);
		}
		
		return null;
	}
}