package com.oitsjustjose.criss_cross.util;

import java.util.ArrayList;
import java.util.List;

import com.oitsjustjose.criss_cross.CrissCross;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy
{
	@SideOnly(Side.CLIENT)
	public static void init()
	{
		ArrayList<Block> blocks = Reference.getModBlocks();
		ArrayList<Item> items = Reference.getModItems();
		
		for(Block b : blocks)
			register(b, "");
		for(Item i : items)
			register(i, "");
	}
	
	
	//WARNING: The following code is BULLSHIT and should be handled by FORGE. But it isn't. So sorry if you can't understand much. Just remember kids:
	//EVERYTHING HAS TO BE IN LOWER CASE. NOTHING YOU LEARNED IN SCHOOL ABOUT CAPITALIZATION MATTERS ANYMORE.
	@SideOnly(Side.CLIENT)
	private static void register(Item item, String customName)
	{
		int meta = 0;
		if(item.getHasSubtypes())
		{
			List<ItemStack> subItems = new ArrayList<ItemStack>();
			item.getSubItems(item, CrissCross.CCTab, subItems);
			for(ItemStack sub : subItems)
			{
				String name = item.getUnlocalizedName(sub).substring(16).toLowerCase();
				ModelBakery.addVariantName(item, Reference.modid.toLowerCase() + ":" + name);
				Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, new ModelResourceLocation(Reference.modid.toLowerCase() + ":" + name, "inventory"));
				meta++;
			}
		}
		else if(customName == "")
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, new ModelResourceLocation(Reference.modid.toLowerCase() + ":" + item.getUnlocalizedName().substring(16).toLowerCase(), "inventory"));
		else
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, new ModelResourceLocation(Reference.modid.toLowerCase() + ":" + customName, "inventory"));
	}
	
	@SideOnly(Side.CLIENT)
	private static void register(Block block, String customName)
	{
		int meta = 0;
		Item itemBlock = Item.getItemFromBlock(block);
		if(itemBlock.getHasSubtypes())
		{
			List<ItemStack> subItems = new ArrayList<ItemStack>();
			itemBlock.getSubItems(itemBlock, CrissCross.CCTab, subItems);
			for(ItemStack sub : subItems)
			{
				String name = itemBlock.getUnlocalizedName(sub).substring(16).toLowerCase();
				ModelBakery.addVariantName(itemBlock, Reference.modid.toLowerCase() + ":" + name);
				Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(itemBlock, meta, new ModelResourceLocation(Reference.modid.toLowerCase() + ":" + name, "inventory"));
				meta++;
			}
		}
		else if(customName == "")
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(itemBlock, meta, new ModelResourceLocation(Reference.modid.toLowerCase() + ":" + block.getUnlocalizedName().substring(16).toLowerCase(), "inventory"));
		else
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(itemBlock, meta, new ModelResourceLocation(Reference.modid.toLowerCase() + ":" + customName, "inventory"));
	}
}