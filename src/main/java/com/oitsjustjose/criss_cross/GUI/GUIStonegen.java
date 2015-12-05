package com.oitsjustjose.criss_cross.GUI;

import org.lwjgl.opengl.GL11;

import com.oitsjustjose.criss_cross.TileEntity.TileEntityStonegen;

import com.oitsjustjose.criss_cross.Container.ContainerStonegen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GUIStonegen extends GuiContainer
{
	public static final ResourceLocation texture = new ResourceLocation("crisscross", "textures/gui/stonegen.png");

	private TileEntityStonegen stonegen;

	public GUIStonegen(EntityPlayer player, TileEntityStonegen stonegen)
	{
		super(new ContainerStonegen(player, stonegen));
		this.stonegen = stonegen;
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.stonegen"), 30, 7, 4210752);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 7, this.ySize - 92, 4210752);
	}

	protected void drawGuiContainerBackgroundLayer(float partialTickTime, int mouseX, int mouseY)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

		if (this.stonegen.isUsingFuel())
		{
			int i1 = this.stonegen.getBurnTimeRemainingScaled(13);
			this.drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 1);
			i1 = this.stonegen.getProgressScaled(24);
			this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
		}
	}
}