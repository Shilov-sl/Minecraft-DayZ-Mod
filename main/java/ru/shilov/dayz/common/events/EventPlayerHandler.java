package ru.shilov.dayz.common.events;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import net.minecraft.entity.player.EntityPlayer;
import ru.shilov.dayz.DayZChatMessage;
import ru.shilov.dayz.common.player.DayZExtendedPlayer;

public class EventPlayerHandler {
	
	@SubscribeEvent
	public void onLoginPlayer(PlayerEvent.PlayerLoggedInEvent event) {
		int online = FMLCommonHandler.instance().getMinecraftServerInstance().getServer().getCurrentPlayerCount() - 1;
		if (FMLCommonHandler.instance().getSide().isClient()) {
			DayZChatMessage.sendMsgChatToPlayer(event.player, "Добро пожаловать на сервер DayZ!");
			DayZChatMessage.sendMsgChatToPlayer(event.player, "Сейчас на сервере " + online + " выживших.");
			DayZChatMessage.sendMsgChatToPlayer(event.player, "Наша группа Вконтакте [vk.com/slava_shilov]");
		}
	}
	
	@SubscribeEvent
	public void onLogOutPlayer(PlayerEvent.PlayerLoggedOutEvent event) {
		
	}
	
	@SubscribeEvent
	public void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {

	}
	
	@SubscribeEvent
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		if(event.player instanceof EntityPlayer  && !event.player.worldObj.isRemote) {
			EntityPlayer player = (EntityPlayer) event.player;
            DayZExtendedPlayer exp = DayZExtendedPlayer.get(player);
            exp.resetBlood(exp.MAX_BLOOD);
            exp.resetFood(0);
            exp.resetThirst(0);
            exp.resetEnergy(exp.MAX_ENERGY);
		}
	}
	
	
}
