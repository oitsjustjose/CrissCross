package com.oitsjustjose.criss_cross.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.oitsjustjose.criss_cross.lib.Lib;
import com.oitsjustjose.criss_cross.tileentity.TileAtmosManipulator;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIAtmosManipulator extends GuiScreen
{
	static final ResourceLocation texture = new ResourceLocation(Lib.MODID.toLowerCase(), "textures/gui/atmosmanipulator.png");
	final EntityPlayer player;
	final int RAINID = 0;
	final int SUNID = 1;
	int updateCount;
	int GUIHeight = 166;
	int GUIWidth = 176;
	TileAtmosManipulator tileAM;

	GuiButton buttonRain;
	GuiButton buttonSun;

	public GUIAtmosManipulator(EntityPlayer player, TileAtmosManipulator tile)
	{
		this.player = player;
		this.tileAM = tile;
	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();
		++this.updateCount;
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException
	{
		if (keyCode == mc.gameSettings.keyBindInventory.getKeyCode())
			this.mc.displayGuiScreen(null);
		if (keyCode == 1)
		{
			this.mc.displayGuiScreen((GuiScreen) null);

			if (this.mc.currentScreen == null)
				this.mc.setIngameFocus();
		}
	}

	@Override
	public void initGui()
	{
		this.buttonList.clear();

		int x = (this.width - this.GUIHeight) / 2;
		int y = (this.height - this.GUIWidth) / 2;
		this.buttonList.add(this.buttonRain = new GuiButton(RAINID, x + 35, y + 20, 98, 20, StatCollector.translateToLocal("button.rain")));
		this.buttonList.add(this.buttonSun = new GuiButton(SUNID, x + 35, y + 75, 98, 20, StatCollector.translateToLocal("button.sun")));

		this.updateButtons();
	}

	@Override
	public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}

	public void updateButtons()
	{
		this.buttonRain.visible = true;
		this.buttonSun.visible = true;
	}

	@Override
	public void actionPerformed(GuiButton button) throws IOException
	{
		if (button.enabled)
		{
			if (button.id == this.RAINID)
				tileAM.setRain();
			else if (button.id == this.SUNID)
				tileAM.setSun();
			this.updateButtons();
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(texture);
		int x = (this.width - this.GUIHeight) / 2;
		int y = (this.height - this.GUIWidth) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.GUIHeight, this.GUIWidth);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.atmosmanipulator"), x + 40, y + 8, 4210752);

		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}