package com.oitsjustjose.criss_cross.lib;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class FluidHandler
{
	public static ArrayList<ItemStack> waterContainers = new ArrayList<ItemStack>();

	public static void init()
	{
		FluidContainerData[] fluidC = FluidContainerRegistry.getRegisteredFluidContainerData();
		for (FluidContainerData f : fluidC)
			if (f.fluid.isFluidEqual(new FluidStack(FluidRegistry.WATER, 1000)) && f.fluid.amount >= 1000)
				waterContainers.add(f.filledContainer);
	}
}
