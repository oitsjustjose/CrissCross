package com.oitsjustjose.criss_cross.Recipes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import net.minecraft.item.ItemStack;

public class AutosmithRecipes
{
	private Map<ItemStack, Integer> stack1Size = Maps.newHashMap();
	private Map<ItemStack, Integer> stack2Size = Maps.newHashMap();
	
	public class RepairRecipe
	{
		private final ItemStack toolItem;
		private final ItemStack repairItem;
		private final ItemStack repairedTool;
		
		public RepairRecipe(final ItemStack repairItem, final ItemStack toolItem)
		{
			this.repairItem = repairItem;
			this.toolItem = toolItem;
			this.repairedTool = toolItem.copy();
			this.repairedTool.setItemDamage(0);
		}
		
		public ItemStack getCraftingResult()
		{
			return this.repairedTool.copy();
		}
		
		public ItemStack[] getIngredients()
		{
			return new ItemStack[]
			{
					this.repairItem, this.toolItem
			};
		}
		
		public boolean uses(final ItemStack ingredient)
		{
			if(ingredient == null)
			{
				return false;
			}
			
			if((this.repairItem != null) && this.repairItem.isItemEqual(ingredient))
			{
				return true;
			}
			else if((this.toolItem != null) && this.toolItem.isItemEqual(ingredient))
			{
				return true;
			}
			
			return false;
		}
		
		@SuppressWarnings("unused")
		public boolean matches(ItemStack toolItem, ItemStack repairItem)
		{
			if(toolItem.isItemEqual(repairItem))
			{
				return false;
			}
			
			if(this.uses(toolItem) && this.uses(repairItem))
			{
				return true;
			}
			
			if(this.uses(toolItem) && (repairItem == null))
			{
				return true;
			}
			
			if(this.uses(repairItem) && (toolItem == null))
			{
				return true;
			}
			return false;
		}
	}
	
	private static AutosmithRecipes instance = new AutosmithRecipes();
	private final ArrayList<RepairRecipe> recipes = new ArrayList<RepairRecipe>();
	
	public static AutosmithRecipes getInstance()
	{
		return AutosmithRecipes.instance;
	}
	
	public void addRecipe(ItemStack itemStack, ItemStack otherItemStack)
	{
		this.recipes.add(new RepairRecipe(itemStack, otherItemStack));
		
		this.stack1Size.put(itemStack, itemStack.stackSize);
		this.stack2Size.put(otherItemStack, otherItemStack.stackSize);
		
		this.stack1Size.put(otherItemStack, otherItemStack.stackSize);
		this.stack2Size.put(itemStack, itemStack.stackSize);
	}
	
	public ItemStack getResult(ItemStack toolItem, ItemStack repairItem)
	{
		for(int j = 0; j < this.recipes.size(); ++j)
		{
			RepairRecipe irecipe = this.recipes.get(j);
			
			if(irecipe.matches(toolItem, repairItem))
			{
				if((ItemStack.areItemStacksEqual(toolItem, irecipe.toolItem) && ItemStack.areItemStacksEqual(repairItem, irecipe.repairItem)))
				{
					return irecipe.getCraftingResult();
				}
			}
		}
		
		return null;
	}
	
	public boolean hasUsage(ItemStack itemStack)
	{
		for(int j = 0; j < this.recipes.size(); ++j)
		{
			RepairRecipe irecipe = this.recipes.get(j);
			
			if(irecipe.uses(itemStack))
			{
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<RepairRecipe> getRecipesFor(ItemStack output)
	{
		ArrayList<RepairRecipe> list = new ArrayList<RepairRecipe>();
		
		for(RepairRecipe recipe : this.recipes)
		{
			if(recipe.repairedTool.isItemEqual(output)
					&& recipe.repairedTool.getTagCompound() == output.getTagCompound())
			{
				list.add(recipe);
			}
		}
		return list;
	}
	
	public ArrayList<RepairRecipe> getRecipesUsing(ItemStack ingredient)
	{
		ArrayList<RepairRecipe> list = new ArrayList<RepairRecipe>();
		
		for(RepairRecipe recipe : this.recipes)
		{
			if(recipe.uses(ingredient))
			{
				list.add(recipe);
				continue;
			}
		}
		return list;
	}
}