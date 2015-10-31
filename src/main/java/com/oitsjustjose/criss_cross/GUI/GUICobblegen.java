package com.oitsjustjose.criss_cross.GUI;

import org.lwjgl.opengl.GL11;

import com.oitsjustjose.criss_cross.TileEntity.TileEntityCobblegen;

import com.oitsjustjose.criss_cross.Container.ContainerCobblegen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GUICobblegen extends GuiContainer
{
	public static final ResourceLocation texture = new ResourceLocation("crisscross", "textures/gui/cobblegen.png");
	
	private TileEntityCobblegen cobblegen;
	
	public GUICobblegen(EntityPlayer player, TileEntityCobblegen cobblegen)
	{
		super(new ContainerCobblegen(player, cobblegen));
		this.cobblegen = cobblegen;
	}
	
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.cobblegen"), 30, 7, 4210752);
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
		
		if(this.cobblegen.isUsingFuel())
		{
			int i1 = this.cobblegen.getBurnTimeRemainingScaled(13);
			this.drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 1);
			i1 = this.cobblegen.getProgressScaled(24);
			this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
		}
	}
}