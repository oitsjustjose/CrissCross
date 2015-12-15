package com.oitsjustjose.criss_cross.recipes;

import com.oitsjustjose.criss_cross.items.ItemPouch;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class PouchColorRecipes implements IRecipe
{
	@Override
	public boolean matches(InventoryCrafting invCraft, World world)
	{
		boolean dyeItem = false;
		boolean pouchItem = false;

		for (int i = 0; i < invCraft.getSizeInventory(); i++)
		{
			ItemStack stack = invCraft.getStackInSlot(i);
			if (stack != null)
				if (stack.getItem() instanceof ItemPouch && !pouchItem)
					pouchItem = true;
				else if (isOreDictDye(stack) && !dyeItem)
					dyeItem = true;
				else
					return false;
		}
		return pouchItem && dyeItem;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting invCraft)
	{
		ItemStack pouch = null;
		ItemStack dye = null;
		ItemStack newPouch = null;
		for (int i = 0; i < invCraft.getSizeInventory(); i++)
		{
			if (invCraft.getStackInSlot(i) != null)
			{
				if (invCraft.getStackInSlot(i).getItem() instanceof ItemPouch)
					pouch = invCraft.getStackInSlot(i);
				else if (isOreDictDye(invCraft.getStackInSlot(i)))
					dye = invCraft.getStackInSlot(i);
			}
		}

		if (pouch == null || dye == null)
			return null;

		newPouch = pouch.copy();
		newPouch.setItemDamage(getDyeMetaFromString(getOreDictNameForDye(dye)));
		return newPouch;
	}

	@Override
	public int getRecipeSize()
	{
		return 10;
	}

	@Override
	public ItemStack getRecipeOutput()
	{
		return null;
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting invCraft)
	{
		ItemStack[] ret = new ItemStack[9];
		ItemStack stackToClear = null;

		for (int i = 0; i < invCraft.getSizeInventory(); i++)
			if (invCraft.getStackInSlot(i) != null)
				if (!isOreDictDye(invCraft.getStackInSlot(i)) && !(invCraft.getStackInSlot(i).getItem() instanceof ItemPouch))
					ret[i] = invCraft.getStackInSlot(i);

		return ret;
	}

	boolean isOreDictDye(ItemStack itemstack)
	{
		int[] oreID = OreDictionary.getOreIDs(itemstack);
		for (int x : oreID)
			if (OreDictionary.getOreName(x).toLowerCase().contains("dye"))
				return true;

		return false;
	}

	String getOreDictNameForDye(ItemStack itemstack)
	{
		int[] oreID = OreDictionary.getOreIDs(itemstack);
		for (int x : oreID)
			if (OreDictionary.getOreName(x).toLowerCase().contains("dye"))
				return OreDictionary.getOreName(x);

		return "";
	}

	int getDyeMetaFromString(String string)
	{
		String s = string.toLowerCase();

		if (s.contains("dyeblack"))
			return 0;
		else if (s.contains("dyered"))
			return 1;
		else if (s.contains("dyegreen"))
			return 2;
		else if (s.contains("dyebrown"))
			return 3;
		else if (s.contains("dyeblue"))
			return 4;
		else if (s.contains("dyepurple"))
			return 5;
		else if (s.contains("dyecyan"))
			return 6;
		else if (s.contains("dyelightgray"))
			return 7;
		else if (s.contains("dyegray"))
			return 8;
		else if (s.contains("dyepink"))
			return 9;
		else if (s.contains("dyelime"))
			return 10;
		else if (s.contains("dyeyellow"))
			return 11;
		else if (s.contains("dyelightblue"))
			return 12;
		else if (s.contains("dyemagenta"))
			return 13;
		else if (s.contains("dyeorange"))
			return 14;
		else if (s.contains("dyewhite"))
			return 15;
		else
			return 0;
	}
}