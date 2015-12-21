package com.oitsjustjose.criss_cross.util;

import net.minecraft.util.EnumChatFormatting;

public class ColorHelper
{
	public static String[] dyes = { "Black", "Red", "Green", "Brown", "Blue", "Purple", "Cyan", "LightGray", "Gray", "Pink", "Lime", "Yellow", "LightBlue", "Magenta", "Orange", "White" };

	public static EnumChatFormatting findFormatForMeta(int meta)
	{
		switch (meta)
		{
		case 0:
			return EnumChatFormatting.BLACK;
		case 1:
			return EnumChatFormatting.DARK_RED;
		case 2:
			return EnumChatFormatting.DARK_GREEN;
		case 3:
			return EnumChatFormatting.GOLD;
		case 4:
			return EnumChatFormatting.BLUE;
		case 5:
			return EnumChatFormatting.DARK_PURPLE;
		case 6:
			return EnumChatFormatting.DARK_AQUA;
		case 7:
			return EnumChatFormatting.GRAY;
		case 8:
			return EnumChatFormatting.DARK_GRAY;
		case 9:
			return EnumChatFormatting.RED;
		case 10:
			return EnumChatFormatting.GREEN;
		case 11:
			return EnumChatFormatting.YELLOW;
		case 12:
			return EnumChatFormatting.AQUA;
		case 13:
			return EnumChatFormatting.LIGHT_PURPLE;
		case 14:
			return EnumChatFormatting.GOLD;
		case 15:
			return EnumChatFormatting.WHITE;
		default:
			return EnumChatFormatting.RESET;
		}
	}

	public static int getColorMultiplierFromMeta(int meta)
	{
		switch (meta)
		{
		case 0:
			return 4342338;
		case 1:
			return 15204352;
		case 2:
			return 4175157;
		case 3:
			return 6046976;
		case 4:
			return 7935;
		case 5:
			return 6962352;
		case 6:
			return 3057097;
		case 7:
			return 13224393;
		case 8:
			return 8882055;
		case 9:
			return 16295679;
		case 10:
			return 65331;
		case 11:
			return 16775936;
		case 12:
			return 3721215;
		case 13:
			return 16711927;
		case 14:
			return 16751872;
		case 15:
			return 16777215;
		default:
			return 16777215;
		}
	}

	public static String getOreDictFromMeta(int meta)
	{
		return "dye" + dyes[meta];
	}
}