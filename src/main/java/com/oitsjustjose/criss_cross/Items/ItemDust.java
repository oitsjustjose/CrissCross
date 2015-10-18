package com.oitsjustjose.criss_cross.Items;

import java.util.ArrayList;
import java.util.List;

import com.oitsjustjose.criss_cross.Util.ConfigHandler;
import com.oitsjustjose.criss_cross.Util.Reference;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.OreDictionary;

public class ItemDust extends Item
{
	private static ArrayList<String> dustNames = new ArrayList<String>();
	private static ArrayList<Integer> colors = new ArrayList<Integer>();
	public static IIcon overlay;
	public static IIcon base;
	
	public ItemDust()
	{
		this.setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.oreDictionaryInit();
		GameRegistry.registerItem(this, this.getUnlocalizedName());
	}
	
	public static void addDustType(String name, int color)
	{
		dustNames.add(name);
		colors.add(color);
	}
	
	@Override
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}
	
	@Override
	public IIcon getIconFromDamageForRenderPass(int meta, int pass)
	{
		return pass == 0 ? base : overlay;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemStack)
	{
		return "item." + Reference.modid + "." + dustNames.get(itemStack.getItemDamage());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register)
	{
		overlay = register.registerIcon(Reference.modid + ":dust_overlay");
		base = register.registerIcon(Reference.modid + ":dust_base");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass)
	{
		if(pass == 1)
			return colors.get(stack.getItemDamage());
		else
			return 16777215;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		for(int i = 0; i < dustNames.size(); i++)
			list.add(new ItemStack(item, 1, i));
	}
	
	void oreDictionaryInit()
	{
		for(int i = 0; i < ConfigHandler.electroextractorOreDictInputs.length; i++)
		{
			String[] entry = ConfigHandler.electroextractorOreDictInputs[i].split(":");
			if(OreDictionary.doesOreNameExist("ore" + entry[0]))
				ItemDust.addDustType(entry[0], Integer.parseInt(entry[1]));
		}
		
		for(int i = 0; i < dustNames.size(); i++)
		{
			ItemStack ingot = null;
			if(OreDictionary.doesOreNameExist("ingot" + dustNames.get(i)))
				ingot = OreDictionary.getOres("ingot" + dustNames.get(i)).get(0);
			if(ingot != null)
				GameRegistry.addSmelting(new ItemStack(this, 1, i), ingot, 0.0F);
		}
	}
}