package com.oitsjustjose.criss_cross;

import com.oitsjustjose.criss_cross.gui.GUIHandler;
import com.oitsjustjose.criss_cross.lib.Config;
import com.oitsjustjose.criss_cross.lib.CreativeTab;
import com.oitsjustjose.criss_cross.lib.Lib;
import com.oitsjustjose.criss_cross.lib.LibBlocks;
import com.oitsjustjose.criss_cross.lib.LibItems;
import com.oitsjustjose.criss_cross.recipes.CraftingRecipes;
import com.oitsjustjose.criss_cross.recipes.MachineRecipes;
import com.oitsjustjose.criss_cross.util.ClientProxy;
import com.oitsjustjose.criss_cross.util.CommonProxy;
import com.oitsjustjose.criss_cross.util.LogHelper;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = Lib.MODID, name = Lib.NAME, version = Lib.VERSION, guiFactory = Lib.GUI_FACTORY, acceptedMinecraftVersions = "1.8.9", dependencies = "required-after:Forge@[11.15.1.1722,);")
public class CrissCross
{
	public static CreativeTabs CCTab = new CreativeTab();

	@Instance(Lib.MODID)
	public static CrissCross instance;

	@SidedProxy(clientSide = Lib.CLIENT_PROXY, serverSide = Lib.COMMON_PROXY, modId = Lib.MODID)
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Config.init(event.getSuggestedConfigurationFile());
		MinecraftForge.EVENT_BUS.register(new Config());
		CommonProxy.preInit();

		LibBlocks.init();
		LibItems.init();
		CraftingRecipes.init();
		
		if(ForgeModContainer.replaceVanillaBucketModel)
			ModelLoader.setBucketModelDefinition(LibItems.buckets);
		
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GUIHandler());
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		MachineRecipes.init();

		if (event.getSide().isClient())
			ClientProxy.init();

		LogHelper.info("Successfully loaded!");
	}
}
