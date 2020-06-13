package ru.shilov.dayz;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public final class DayZChatMessage {
	
	public static void sendMsgChatToPlayer(EntityPlayer player, String message) {
		if (FMLCommonHandler.instance().getSide().isClient()) {
			player.addChatMessage(new ChatComponentText(message));
		}

	}
}