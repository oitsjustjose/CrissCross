package com.oitsjustjose.criss_cross;

import com.oitsjustjose.criss_cross.Blocks.CCBlocks;
import com.oitsjustjose.criss_cross.Items.CCItems;
import com.oitsjustjose.criss_cross.Items.ItemDust;
import com.oitsjustjose.criss_cross.Recipes.CRecipes;
import com.oitsjustjose.criss_cross.Util.CommonProxy;
import com.oitsjustjose.criss_cross.Util.ConfigHandler;
import com.oitsjustjose.criss_cross.Util.Reference;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.modid, version = Reference.version, guiFactory = Reference.guifactory)
public class CrissCross
{
    @Instance(Reference.modid)
    public static CrissCross instance;
    
    @SidedProxy(clientSide = "com.oitsjustjose.criss_cross.Util.ClientProxy", serverSide = "com.oitsjustjose.criss_cross.Util.CommonProxy")
    public static CommonProxy proxy;
	
	@EventHandler
	public static void preInit(FMLPreInitializationEvent event)
	{
		ConfigHandler.init(event.getSuggestedConfigurationFile());
        FMLCommonHandler.instance().bus().register(new ConfigHandler());
		CCItems.init();
		CCBlocks.init();
		proxy.preInit();
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event)
	{
		CRecipes.init();
		proxy.init();
	}
}