package com.oitsjustjose.criss_cross.gui;

import org.lwjgl.opengl.GL11;

import com.oitsjustjose.criss_cross.container.ContainerStonegen;
import com.oitsjustjose.criss_cross.lib.Lib;
import com.oitsjustjose.criss_cross.tileentity.TileStonegen;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.ForgeModContainer;

public class GUIStonegen extends GuiContainer
{
	public static final ResourceLocation texture = new ResourceLocation(Lib.MODID.toLowerCase(), "textures/gui/stonegen.png");
	public static final ResourceLocation altTexture = new ResourceLocation(Lib.MODID.toLowerCase(), "textures/gui/stonegen_alt.png");

	private TileStonegen stonegen;

	public GUIStonegen(EntityPlayer player, TileStonegen stonegen)
	{
		super(new ContainerStonegen(player, stonegen));
		this.stonegen = stonegen;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.stonegen"), 30, 7, 4210752);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 7, this.ySize - 92, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTickTime, int mouseX, int mouseY)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		if (ForgeModContainer.replaceVanillaBucketModel)
			this.mc.renderEngine.bindTexture(altTexture);
		else
			this.mc.renderEngine.bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

		if (this.stonegen.isUsingFuel())
		{
			int i1 = this.stonegen.getProgressScaled(24);
			this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
		}
	}
}