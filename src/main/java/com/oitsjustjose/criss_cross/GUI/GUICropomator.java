package com.oitsjustjose.criss_cross.GUI;

import org.lwjgl.opengl.GL11;

import com.oitsjustjose.criss_cross.Container.ContainerCropomator;
import com.oitsjustjose.criss_cross.TileEntity.TileEntityCropomator;
import com.oitsjustjose.criss_cross.Util.Reference;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GUICropomator extends GuiContainer
{
	public static final ResourceLocation texture = new ResourceLocation("crisscross", "textures/gui/cropomator.png");
	
	private TileEntityCropomator cropomator;
	
	public GUICropomator(EntityPlayer player, TileEntityCropomator cropomator)
	{
		super(new ContainerCropomator(player, cropomator));
		this.cropomator = cropomator;
	}
	
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.cropomator"), 55, 7, 4210752);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 7, this.ySize - 92,
				4210752);
	}
	
	protected void drawGuiContainerBackgroundLayer(float partialTickTime, int mouseX, int mouseY)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		
		if(this.cropomator.isUsingFuel())
		{
			int i1 = this.cropomator.getBurnTimeRemainingScaled(13);
			this.drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 1);
			i1 = this.cropomator.getProgressScaled(24);
			this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
		}
	}
}