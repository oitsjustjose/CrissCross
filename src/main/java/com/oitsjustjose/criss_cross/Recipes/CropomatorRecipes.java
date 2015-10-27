package com.oitsjustjose.criss_cross.Recipes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import net.minecraft.item.ItemStack;

public class CropomatorRecipes
{
	private static final CropomatorRecipes recipeBase = new CropomatorRecipes();
	public static final ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
	public static final ArrayList<ItemStack> outputs = new ArrayList<ItemStack>();
	
	private Map recipes = Maps.newHashMap();
	
	public static CropomatorRecipes getInstance()
	{
		return recipeBase;
	}
	
	public void addRecipe(ItemStack input, ItemStack stack)
	{
		this.recipes.put(input, stack);
		inputs.add(input);
		outputs.add(stack);
	}
	
	public ItemStack getResult(ItemStack stack)
	{
		Iterator iterator = this.recipes.entrySet().iterator();
		Entry entry;
		
		do
		{
			if(!iterator.hasNext())
				return null;
				
			entry = (Entry) iterator.next();
		}
		while(!this.compareItemStacks(stack, (ItemStack) entry.getKey()));
		
		return (ItemStack) entry.getValue();
	}
	
	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
	{
		return stack2.getItem() == stack1.getItem()
				&& (stack2.getItemDamage() == 32767 || stack2.getItemDamage() == stack1.getItemDamage());
	}
	
	public Map getRecipeList()
	{
		return this.recipes;
	}
}