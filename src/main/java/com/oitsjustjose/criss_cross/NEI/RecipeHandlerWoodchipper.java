package com.oitsjustjose.criss_cross.NEI;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.oitsjustjose.criss_cross.TileEntity.TileEntityWoodchipper;
import com.oitsjustjose.criss_cross.TileEntity.TileEntityWoodchipper.WoodchipperRecipe;
import com.oitsjustjose.criss_cross.Util.ConfigHandler;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class RecipeHandlerWoodchipper extends RecipeHandlerBase
{

	public class CachedWoodchipperRecipe extends CachedBaseRecipe
	{

		public PositionedStack input;
		public PositionedStack output;
		public int time = ConfigHandler.woodchipperProcessTime;

		public CachedWoodchipperRecipe(WoodchipperRecipe chipper)
		{
			this.input = new PositionedStack(chipper.input, 44, 18);
			this.output = new PositionedStack(chipper.result, 98, 18);
		}

		@Override
		public PositionedStack getIngredient()
		{
			return this.input;
		}

		@Override
		public PositionedStack getResult()
		{
			return this.output;
		}

	}

	@Override
	public String getRecipeName()
	{
		return StatCollector.translateToLocal("crisscross.nei.woodchipper");
	}

	@Override
	public String getRecipeID()
	{
		return "crisscross.woodchipper";
	}

	@Override
	public void loadTransferRects()
	{
		this.transferRects
				.add(new RecipeTransferRect(new Rectangle(68, 20, 22, 15), this.getRecipeID(), new Object[0]));
	}

	@Override
	public String getGuiTexture()
	{
		return "crisscross:textures/gui/woodchipper.png";
	}

	@Override
	public void drawBackground(int recipe)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GuiDraw.changeTexture(this.getGuiTexture());
		GuiDraw.drawTexturedModalRect(0, 0, 0, 0, 160, 65);
	}

	@Override
	public void drawExtras(int recipe)
	{
		int time = ((CachedWoodchipperRecipe) this.arecipes.get(recipe)).time;
		int seconds = time / 20;
		GuiDraw.drawStringC(time + " ticks (" + seconds + " secs)", 81, 40, 0x808080, false);
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results)
	{
		if (outputId.equals(this.getRecipeID()))
		{
			ArrayList<ItemStack> inputs = TileEntityWoodchipper.getInputs();
			ArrayList<ItemStack> outputs = TileEntityWoodchipper.getOutputs();

			for (int i = 0; i < inputs.size(); i++)
			{
				WoodchipperRecipe recipe = new WoodchipperRecipe(inputs.get(i), outputs.get(i));
				this.arecipes.add(new CachedWoodchipperRecipe(recipe));
			}
		} else
		{
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result)
	{
		WoodchipperRecipe recipe = new WoodchipperRecipe(TileEntityWoodchipper.getItemFromResult(result), result);

		if (NEIServerUtils.areStacksSameTypeCrafting(recipe.result, result))
		{
			this.arecipes.add(new CachedWoodchipperRecipe(recipe));
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingred)
	{
		WoodchipperRecipe recipe = new WoodchipperRecipe(ingred, TileEntityWoodchipper.getResult(ingred));

		if (NEIServerUtils.areStacksSameTypeCrafting(recipe.input, ingred))
		{
			this.arecipes.add(new CachedWoodchipperRecipe(recipe));
		}
	}

}