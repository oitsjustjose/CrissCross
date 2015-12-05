package com.oitsjustjose.criss_cross.Recipes;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;
import com.oitsjustjose.criss_cross.Items.CCItems;
import com.oitsjustjose.criss_cross.TileEntity.TileEntityStonegen;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class StonegenRecipes
{
	private static final StonegenRecipes recipeBase = new StonegenRecipes();
	private Map recipes = Maps.newHashMap();

	public StonegenRecipes()
	{
		this.addRecipe(new ItemStack(Items.lava_bucket), new ItemStack(Blocks.stone));
		TileEntityStonegen.addFuel(new ItemStack(Items.water_bucket));
		TileEntityStonegen.addFuel(new ItemStack(CCItems.buckets));
	}

	public static StonegenRecipes getInstance()
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