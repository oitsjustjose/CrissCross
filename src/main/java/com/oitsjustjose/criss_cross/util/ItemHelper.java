package com.oitsjustjose.criss_cross.util;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

/**
 * 
 * @author TeamCoFH All credit goes to TeamCoFH, this was required for proper woodchipper recipes :)
 * 
 */

public class ItemHelper
{
	public static ItemStack cloneStack(Item item, int stackSize)
	{

		if (item == null)
		{
			return null;
		}
		ItemStack stack = new ItemStack(item, stackSize);

		return stack;
	}

	public static ItemStack cloneStack(Block item, int stackSize)
	{

		if (item == null)
		{
			return null;
		}
		ItemStack stack = new ItemStack(item, stackSize);

		return stack;
	}

	public static ItemStack cloneStack(ItemStack stack, int stackSize)
	{

		if (stack == null)
		{
			return null;
		}
		ItemStack retStack = stack.copy();
		retStack.stackSize = stackSize;

		return retStack;
	}

	public static ItemStack cloneStack(ItemStack stack)
	{

		if (stack == null)
		{
			return null;
		}
		ItemStack retStack = stack.copy();

		return retStack;
	}

	public static int getItemDamage(ItemStack stack)
	{

		return Items.diamond.getDamage(stack);
	}

	public static ItemStack findMatchingRecipe(InventoryCrafting inv, World world)
	{

		ItemStack[] dmgItems = new ItemStack[2];
		for (int i = 0; i < inv.getSizeInventory(); i++)
		{
			if (inv.getStackInSlot(i) != null)
			{
				if (dmgItems[0] == null)
				{
					dmgItems[0] = inv.getStackInSlot(i);
				}
				else
				{
					dmgItems[1] = inv.getStackInSlot(i);
					break;
				}
			}
		}
		if (dmgItems[0] == null || dmgItems[0].getItem() == null)
		{
			return null;
		}
		else if (dmgItems[1] != null && dmgItems[0].getItem() == dmgItems[1].getItem() && dmgItems[0].stackSize == 1 && dmgItems[1].stackSize == 1 && dmgItems[0].getItem().isRepairable())
		{
			Item theItem = dmgItems[0].getItem();
			int var13 = theItem.getMaxDamage() - dmgItems[0].getItemDamage();
			int var8 = theItem.getMaxDamage() - dmgItems[1].getItemDamage();
			int var9 = var13 + var8 + theItem.getMaxDamage() * 5 / 100;
			int var10 = Math.max(0, theItem.getMaxDamage() - var9);

			return new ItemStack(dmgItems[0].getItem(), 1, var10);
		}
		else
		{
			IRecipe recipe;
			for (int i = 0; i < CraftingManager.getInstance().getRecipeList().size(); i++)
			{
				recipe = (IRecipe) CraftingManager.getInstance().getRecipeList().get(i);

				if (recipe.matches(inv, world))
				{
					return recipe.getCraftingResult(inv);
				}
			}
			return null;
		}
	}
}