package com.oitsjustjose.criss_cross.Recipes;

import com.oitsjustjose.criss_cross.Items.CCItems;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

public class ForgingHammerRecipes
{
	public static void init()
	{
		for(int i = 0; i < Item.itemRegistry.getKeys().size(); i++)
		{
			ItemStack toolStack = new ItemStack(Item.getItemById(i));
			if(toolStack != null && (toolStack.getItem() instanceof ItemTool || toolStack.getItem() instanceof ItemArmor))
			{
				for(int toolMat = 0; toolMat < Item.ToolMaterial.values().length; toolMat++)
				{
					ItemStack repairItem = Item.ToolMaterial.values()[toolMat].getRepairItemStack();
					String[] toolName = toolStack.getDisplayName().split(" ");
					if(repairItem == null)
						return;
					for(int name = 0; name < toolName.length; name++)
					{
						if(repairItem.getDisplayName().contains(toolName[name]))
						{
							ItemStack repairedTool = toolStack.copy();
							toolStack.setItemDamage(0);
							GameRegistry.addShapelessRecipe(repairedTool, new Object[] {
									toolStack, repairItem, CCItems.forgingHammer
							});
						}
					}
				}
			}
		}
	}
}
