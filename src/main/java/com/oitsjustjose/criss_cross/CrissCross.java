package com.oitsjustjose.criss_cross;

import com.oitsjustjose.criss_cross.blocks.CCBlocks;
import com.oitsjustjose.criss_cross.items.CCItems;
import com.oitsjustjose.criss_cross.items.ItemDust;
import com.oitsjustjose.criss_cross.recipes.CCCraftingRecipes;
import com.oitsjustjose.criss_cross.recipes.CCMachineRecipes;
import com.oitsjustjose.criss_cross.util.CCTab;
import com.oitsjustjose.criss_cross.util.ClientProxy;
import com.oitsjustjose.criss_cross.util.CommonProxy;
import com.oitsjustjose.criss_cross.util.ConfigHandler;
import com.oitsjustjose.criss_cross.util.OreDictionaryAdder;
import com.oitsjustjose.criss_cross.util.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.modid, version = Reference.version, guiFactory = Reference.guifactory)
public class CrissCross
{
	public static CreativeTabs CCTab;

	@Instance(Reference.modid)
	public static CrissCross instance;

	@SidedProxy(clientSide = "com.oitsjustjose.criss_cross.util.ClientProxy", serverSide = "com.oitsjustjose.criss_cross.util.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public static void preInit(FMLPreInitializationEvent event)
	{
		ConfigHandler.init(event.getSuggestedConfigurationFile());
		MinecraftForge.EVENT_BUS.register(new ConfigHandler());
		CCTab = new CCTab();
		proxy.preInit();
		
		CCBlocks.init();
		CCItems.init();
		CCCraftingRecipes.init();
		CCMachineRecipes.init();
		OreDictionaryAdder.init();
	}

	@EventHandler
	public static void init(FMLInitializationEvent event)
	{
		if(event.getSide().isClient())
			ClientProxy.init();
	}
}