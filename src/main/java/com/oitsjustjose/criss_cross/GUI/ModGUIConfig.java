package com.oitsjustjose.criss_cross.gui;

import com.oitsjustjose.criss_cross.util.ConfigHandler;
import com.oitsjustjose.criss_cross.util.Reference;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

public class ModGUIConfig extends GuiConfig
{
	public ModGUIConfig(GuiScreen guiScreen)
	{
		super(guiScreen, new ConfigElement(ConfigHandler.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), Reference.modid,
				false, true, GuiConfig.getAbridgedConfigPath(ConfigHandler.config.toString()));
	}
}