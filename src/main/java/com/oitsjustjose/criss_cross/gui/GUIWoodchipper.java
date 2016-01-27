package com.oitsjustjose.criss_cross.gui;

import org.lwjgl.opengl.GL11;

import com.oitsjustjose.criss_cross.container.ContainerWoodchipper;
import com.oitsjustjose.criss_cross.lib.Lib;
import com.oitsjustjose.criss_cross.tileentity.TileWoodchipper;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.ForgeModContainer;

public class GUIWoodchipper extends GuiContainer
{
	public static final ResourceLocation texture = new ResourceLocation(Lib.MODID.toLowerCase(), "textures/gui/woodchipper.png");
	public static final ResourceLocation altTexture = new ResourceLocation(Lib.MODID.toLowerCase(), "textures/gui/woodchipper_alt.png");

	private TileWoodchipper woodchipper;

	public GUIWoodchipper(EntityPlayer player, TileWoodchipper woodchipper)
	{
		super(new ContainerWoodchipper(player, woodchipper));
		this.woodchipper = woodchipper;
	}

	public static ResourceLocation getBackground()
	{
		if (ForgeModContainer.replaceVanillaBucketModel)
			return altTexture;
		return texture;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.woodchipper"), 55, 7, 4210752);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 7, this.ySize - 92, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTickTime, int mouseX, int mouseY)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(getBackground());
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

		if (this.woodchipper.isUsingFuel())
		{
			int i1 = this.woodchipper.getBurnTimeRemainingScaled(13);
			this.drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 1);
			i1 = this.woodchipper.getProgressScaled(24);
			this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
		}
	}
}