package com.oitsjustjose.criss_cross.GUI;

import com.oitsjustjose.criss_cross.Util.ConfigHandler;
import com.oitsjustjose.criss_cross.Util.Reference;

import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

public class ModGUIConfig extends GuiConfig
{
	public ModGUIConfig(GuiScreen guiScreen)
	{
		super(guiScreen,
				new ConfigElement(ConfigHandler.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
				Reference.modid, false, true, GuiConfig.getAbridgedConfigPath(ConfigHandler.config.toString()));
	}
}