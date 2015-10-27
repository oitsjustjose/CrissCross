package com.oitsjustjose.criss_cross.Recipes;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ElectroextractorRecipes
{
	private static final ElectroextractorRecipes recipeBase = new ElectroextractorRecipes();
	private Map recipes = Maps.newHashMap();
	
	public static ElectroextractorRecipes getInstance()
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