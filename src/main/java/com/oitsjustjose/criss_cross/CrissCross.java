package com.oitsjustjose.criss_cross;

import com.oitsjustjose.criss_cross.lib.ConfigHandler;
import com.oitsjustjose.criss_cross.lib.CreativeTab;
import com.oitsjustjose.criss_cross.lib.Lib;
import com.oitsjustjose.criss_cross.lib.LibBlocks;
import com.oitsjustjose.criss_cross.lib.LibItems;
import com.oitsjustjose.criss_cross.recipes.CCMachineRecipes;
import com.oitsjustjose.criss_cross.recipes.VanillaRecipes;
import com.oitsjustjose.criss_cross.util.ClientProxy;
import com.oitsjustjose.criss_cross.util.CommonProxy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Lib.modid, name = Lib.name, version = Lib.version, guiFactory = Lib.guifactory)
public class CrissCross
{
	public static CreativeTabs CCTab = new CreativeTab();

	@Instance(Lib.modid)
	public static CrissCross instance;

	@SidedProxy(clientSide = Lib.clientProxy, serverSide = Lib.commonProxy)
	public static CommonProxy proxy;

	@EventHandler
	public static void preInit(FMLPreInitializationEvent event)
	{
		ConfigHandler.init(event.getSuggestedConfigurationFile());
		MinecraftForge.EVENT_BUS.register(new ConfigHandler());
		proxy.preInit();

		LibBlocks.init();
		LibItems.init();
		VanillaRecipes.init();
	}
	
	@EventHandler
	public static void postInit(FMLPostInitializationEvent event)
	{
		CCMachineRecipes.init();
		
		if (event.getSide().isClient())
			ClientProxy.init();
	}
}