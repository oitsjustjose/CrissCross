package com.oitsjustjose.criss_cross.recipes;

import com.oitsjustjose.criss_cross.items.ItemPouch;

import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
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
		ItemStack itemstack = null;
		int[] aint = new int[3];
		int i = 0;
		int j = 0;
		ItemPouch pouch = null;

		for (int k = 0; k < invCraft.getSizeInventory(); ++k)
		{
			ItemStack itemstack1 = invCraft.getStackInSlot(k);

			if (itemstack1 != null)
			{
				if (itemstack1.getItem() instanceof ItemPouch)
				{
					pouch = (ItemPouch) itemstack1.getItem();

					if (itemstack != null)
						return null;

					itemstack = itemstack1.copy();
					itemstack.stackSize = 1;

					if (pouch.hasColor(itemstack1))
					{
						int l = pouch.getColor(itemstack);
						float f = (float) (l >> 16 & 255) / 255.0F;
						float f1 = (float) (l >> 8 & 255) / 255.0F;
						float f2 = (float) (l & 255) / 255.0F;
						i = (int) ((float) i + Math.max(f, Math.max(f1, f2)) * 255.0F);
						aint[0] = (int) ((float) aint[0] + f * 255.0F);
						aint[1] = (int) ((float) aint[1] + f1 * 255.0F);
						aint[2] = (int) ((float) aint[2] + f2 * 255.0F);
						++j;
					}
				}
				else
				{
					if (!isOreDictDye(itemstack1))
						return null;

					float[] afloat = EntitySheep.func_175513_a(EnumDyeColor.byDyeDamage(getDyeMetaFromString(getOreDictNameForDye(itemstack1))));
					int l1 = (int) (afloat[0] * 255.0F);
					int i2 = (int) (afloat[1] * 255.0F);
					int j2 = (int) (afloat[2] * 255.0F);
					i += Math.max(l1, Math.max(i2, j2));
					aint[0] += l1;
					aint[1] += i2;
					aint[2] += j2;
					++j;
				}
			}
		}

		if (pouch == null)
			return null;

		else
		{
			int i1 = aint[0] / j;
			int j1 = aint[1] / j;
			int k1 = aint[2] / j;
			float f3 = (float) i / (float) j;
			float f4 = (float) Math.max(i1, Math.max(j1, k1));
			i1 = (int) ((float) i1 * f3 / f4);
			j1 = (int) ((float) j1 * f3 / f4);
			k1 = (int) ((float) k1 * f3 / f4);
			int lvt_12_3_ = (i1 << 8) + j1;
			lvt_12_3_ = (lvt_12_3_ << 8) + k1;
			pouch.setColor(itemstack, lvt_12_3_);
			return itemstack;
		}
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