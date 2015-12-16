package com.oitsjustjose.criss_cross.recipes;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

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
				this.recipes.put(new ItemStack(i.getItem(), qty, i.getMetadata()), stack);
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
		return stack2.getItem() == stack1.getItem() && (stack2.getItemDamage() == 32767 || stack2.getItemDamage() == stack1.getItemDamage()) && stack1.stackSize == stack2.stackSize;
	}

	public Map getRecipeList()
	{
		return this.recipes;
	}
}