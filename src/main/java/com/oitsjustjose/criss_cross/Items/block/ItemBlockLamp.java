package com.oitsjustjose.criss_cross.items.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class ItemBlockLamp extends ItemBlock
{
	public ItemBlockLamp(Block block)
	{
		super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
	}

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
    	int meta = MathHelper.clamp_int(itemStack.getItemDamage(), 0, 15);
        return super.getUnlocalizedName() + meta;
    }
    
    @Override
    public int getMetadata(int par1)
    {
        return par1;
    }
}