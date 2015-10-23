package com.oitsjustjose.criss_cross;

import com.oitsjustjose.criss_cross.Blocks.CCBlocks;
import com.oitsjustjose.criss_cross.Items.CCItems;
import com.oitsjustjose.criss_cross.Recipes.CCRecipes;
import com.oitsjustjose.criss_cross.Util.CommonProxy;
import com.oitsjustjose.criss_cross.Util.ConfigHandler;
import com.oitsjustjose.criss_cross.Util.OreDictionaryAdder;
import com.oitsjustjose.criss_cross.Util.Reference;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import com.oitsjustjose.criss_cross.Util.CCTab;
import net.minecraft.creativetab.CreativeTabs;

@Mod(modid = Reference.modid, version = Reference.version, guiFactory = Reference.guifactory)
public class CrissCross
{
	public static CreativeTabs CCTab;

	@Instance(Reference.modid)
	public static CrissCross instance;

	@SidedProxy(clientSide = "com.oitsjustjose.criss_cross.Util.ClientProxy", serverSide = "com.oitsjustjose.criss_cross.Util.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public static void preInit(FMLPreInitializationEvent event)
	{
		ConfigHandler.init(event.getSuggestedConfigurationFile());
		FMLCommonHandler.instance().bus().register(new ConfigHandler());
		CCTab = new CCTab();
		proxy.preInit();
	}

	@EventHandler
	public static void init(FMLInitializationEvent event)
	{
		CCBlocks.init();
		CCItems.init();
		CCRecipes.init();
		OreDictionaryAdder.init();
		proxy.init();
	}
}