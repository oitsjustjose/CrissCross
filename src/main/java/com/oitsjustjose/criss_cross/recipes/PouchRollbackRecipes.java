package com.oitsjustjose.criss_cross.recipes;

import com.oitsjustjose.criss_cross.items.ItemPouch;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class PouchRollbackRecipes implements IRecipe
{
	@Override
	public boolean matches(InventoryCrafting invCraft, World world)
	{
		boolean pouchItem = false;

		for (int i = 0; i < invCraft.getSizeInventory(); i++)
		{
			ItemStack stack = invCraft.getStackInSlot(i);
			if (stack != null)
				if (stack.getItem() instanceof ItemPouch && !pouchItem)
					pouchItem = true;
				else
					return false;
		}
		return pouchItem;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting invCraft)
	{
		ItemStack pouch = null;
		ItemStack newPouch = null;
		for (int i = 0; i < invCraft.getSizeInventory(); i++)
			if (invCraft.getStackInSlot(i) != null)
				if (invCraft.getStackInSlot(i).getItem() instanceof ItemPouch)
					pouch = invCraft.getStackInSlot(i);

		if (pouch == null || pouch.getItemDamage() == 0)
			return null;

		newPouch = pouch.copy();
		newPouch.setItemDamage(0);

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

		for (int i = 0; i < invCraft.getSizeInventory(); i++)
			if (invCraft.getStackInSlot(i) != null)
				if (!(invCraft.getStackInSlot(i).getItem() instanceof ItemPouch))
					ret[i] = invCraft.getStackInSlot(i);

		return ret;
	}
}