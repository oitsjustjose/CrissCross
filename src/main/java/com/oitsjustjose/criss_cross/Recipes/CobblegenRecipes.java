package com.oitsjustjose.criss_cross.recipes;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;
import com.oitsjustjose.criss_cross.items.CCItems;
import com.oitsjustjose.criss_cross.tileentity.TileCobblegen;
import com.oitsjustjose.criss_cross.tileentity.TileStonegen;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CobblegenRecipes
{
	private static final CobblegenRecipes recipeBase = new CobblegenRecipes();
	private Map recipes = Maps.newHashMap();

	public CobblegenRecipes()
	{
		this.addRecipe(new ItemStack(Items.lava_bucket), new ItemStack(Blocks.cobblestone));
		TileCobblegen.addFuel(new ItemStack(Items.water_bucket));
		TileCobblegen.addFuel(new ItemStack(CCItems.buckets));
	}

	public static CobblegenRecipes getInstance()
	{
		return recipeBase;
	}

	public void addRecipe(ItemStack input, ItemStack stack)
	{
		this.recipes.put(input, stack);
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
		return stack2.getItem() == stack1.getItem() && (stack2.getItemDamage() == 32767 || stack2.getItemDamage() == stack1.getItemDamage());
	}

	public Map getRecipeList()
	{
		return this.recipes;
	}
}