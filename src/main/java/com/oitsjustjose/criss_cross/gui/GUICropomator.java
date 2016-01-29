package com.oitsjustjose.criss_cross.gui;

import org.lwjgl.opengl.GL11;

import com.oitsjustjose.criss_cross.container.ContainerCropomator;
import com.oitsjustjose.criss_cross.lib.Lib;
import com.oitsjustjose.criss_cross.tileentity.TileCropomator;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GUICropomator extends GuiContainer
{
	public static final ResourceLocation texture = new ResourceLocation(Lib.MODID.toLowerCase(), "textures/gui/cropomator.png");

	private TileCropomator cropomator;

	public GUICropomator(EntityPlayer player, TileCropomator cropomator)
	{
		super(new ContainerCropomator(player, cropomator));
		this.cropomator = cropomator;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.cropomator"), 55, 7, 4210752);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 7, this.ySize - 92, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTickTime, int mouseX, int mouseY)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

		if (this.cropomator.isUsingFuel())
		{
			//draws burn meter
			int i1 = this.cropomator.getBurnTimeRemainingScaled(46);
			//args: x, y, textureX, textureY, width, height
			this.drawTexturedModalRect(k + 8, l + 51  - i1, 176, 17, 16, i1 - 1);
			//draws progress bar
			i1 = this.cropomator.getProgressScaled(24);
			//args: x, y, textureX, textureY, width, height
			this.drawTexturedModalRect(k + 79, l + 34, 176, 0, i1 + 1, 16);
		}
	}
}