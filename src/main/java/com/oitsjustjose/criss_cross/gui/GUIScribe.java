package com.oitsjustjose.criss_cross.gui;

import org.lwjgl.opengl.GL11;

import com.oitsjustjose.criss_cross.container.ContainerScribe;
import com.oitsjustjose.criss_cross.lib.Lib;
import com.oitsjustjose.criss_cross.tileentity.TileScribe;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GUIScribe extends GuiContainer
{
	public static final ResourceLocation texture = new ResourceLocation(Lib.MODID.toLowerCase(), "textures/gui/scribe.png");

	private TileScribe scribe;

	public GUIScribe(EntityPlayer player, TileScribe scribe)
	{
		super(new ContainerScribe(player, scribe));
		this.scribe = scribe;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.scribe"), 72, 7, 4210752);
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
	}
}