package com.oitsjustjose.criss_cross.recipes.machine;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ScribeRecipes
{
	private static final ScribeRecipes recipeBase = new ScribeRecipes();
	private Map recipes = Maps.newHashMap();

	public static ScribeRecipes getInstance()
	{
		return recipeBase;
	}

	public void addRecipe(ItemStack input, ItemStack stack)
	{
		this.recipes.put(input, stack);
	}

	public void addRecipe(String oreDict, int qty, ItemStack stack)
	{
		List<ItemStack> itemstacks = OreDictionary.getOres(oreDict);
		if (itemstacks.size() > 0)
			for (ItemStack i : itemstacks)
			{
				ItemStack temp = i.copy();
				temp.stackSize = qty;
				this.recipes.put(temp, stack);
			}
	}

	public int getInputStackSizeForOutput(ItemStack output)
	{
		Iterator iterator = this.recipes.entrySet().iterator();
		Entry entry;
		do
		{
			if (!iterator.hasNext())
				return -1;

			entry = (Entry) iterator.next();
		}
		while (!this.compareBooks(output, (ItemStack) entry.getValue()));
		return ((ItemStack) entry.getKey()).stackSize;
	}

	public ItemStack getResult(ItemStack stack)
	{
		Iterator iterator = this.recipes.entrySet().iterator();
		Entry entry;

		do
		{
			if (!iterator.hasNext())
				return null;

			entry = (Entry) iterator.next();
		}
		while (!this.compareItemStacks(stack, (ItemStack) entry.getKey()));

		return (ItemStack) entry.getValue();
	}

	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
	{
		return stack2.getItem() == stack1.getItem() && (stack2.getItemDamage() == 32767 || stack2.getItemDamage() == stack1.getItemDamage()) && stack1.stackSize >= stack2.stackSize;
	}

	private boolean compareBooks(ItemStack stack1, ItemStack stack2)
	{
		if (stack1.getItem() instanceof ItemEnchantedBook && stack2.getItem() instanceof ItemEnchantedBook)
		{
			ItemEnchantedBook book1 = (ItemEnchantedBook) stack1.getItem();
			ItemEnchantedBook book2 = (ItemEnchantedBook) stack2.getItem();
			return book1.getEnchantments(stack1) == book2.getEnchantments(stack2);
		}
		return false;
	}

	public Map getRecipeList()
	{
		return this.recipes;
	}
}