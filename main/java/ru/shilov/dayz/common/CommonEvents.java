package ru.shilov.dayz.common;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import ru.shilov.dayz.DayZChatMessage;
import ru.shilov.dayz.DayZMain;
import ru.shilov.dayz.common.player.DayZExtendedPlayer;

public class CommonEvents {
	
	@SubscribeEvent
    public void onEntityConstructing(EntityEvent.EntityConstructing event) {
        if(event.entity instanceof EntityPlayer && DayZExtendedPlayer.get((EntityPlayer)event.entity) == null) {
        	DayZExtendedPlayer.register((EntityPlayer)event.entity);	
        }
	}
    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
    	if(event.world.getTotalWorldTime() % 1200 == 0) {
    		
    	}
    }
    
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (!event.player.capabilities.isCreativeMode) {
			thrist(event.player);
            DayZExtendedPlayer exp = DayZExtendedPlayer.get(event.player);
			DayZMain.LOGGER.info(exp.getThirst());
		}
    }
	
	@SubscribeEvent
	public void onLoginPlayer(PlayerEvent.PlayerLoggedInEvent event) {
		int online = FMLCommonHandler.instance().getMinecraftServerInstance().getServer().getCurrentPlayerCount() - 1;
		if (FMLCommonHandler.instance().getSide().isClient()) {
			DayZChatMessage.sendMsgChatToPlayer(event.player, "Добро пожаловать на сервер!");
			DayZChatMessage.sendMsgChatToPlayer(event.player, "Сейчас на сервере " + online + " выживших.");
		}
	}
	
	@SubscribeEvent
	public void onLogOutPlayer(PlayerEvent.PlayerLoggedOutEvent event) {
		
	}
	
	@SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if(event.entity instanceof EntityPlayer  && !event.entity.worldObj.isRemote) {
			EntityPlayer player = (EntityPlayer) event.entity;
			//PacketHandler.network.sendTo((new PacketSyncPlayer(player)).buildPacket(), (EntityPlayerMP)player);
    		//PacketHandler.network.sendTo(new PacketSyncPlayer(player), (EntityPlayerMP)player);
		}
    }
	
	@SubscribeEvent
	public void onPlayerUpdateExtendedData(LivingUpdateEvent event) {
		if(event.entity instanceof EntityPlayer  && !event.entity.worldObj.isRemote) {
			EntityPlayer player = (EntityPlayer) event.entity;
    		//PacketHandler.network.sendTo(new PacketSyncPlayer(player), (EntityPlayerMP)player);
    		//PacketHandler.network.sendTo((new PacketSyncPlayer(player)).buildPacket(), (EntityPlayerMP)player);
		}
    
	}

	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent event) {
		if(event.entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;
            DayZExtendedPlayer exp = DayZExtendedPlayer.get(player);
            exp.resetThirst(0);
		}
	}

	@SubscribeEvent
	public void onRespawn(PlayerRespawnEvent event) {
		if(event.player instanceof EntityPlayer  && !event.player.worldObj.isRemote) {
			EntityPlayer player = (EntityPlayer) event.player;
            DayZExtendedPlayer exp = DayZExtendedPlayer.get(player);
            exp.resetThirst(0);
		}
	}
	
	private void thrist(EntityPlayer player) {
        DayZExtendedPlayer exp = DayZExtendedPlayer.get(player);
		if (!player.isDead) {
			if (exp.getThirst() == 5000) {
				DayZChatMessage.sendMsgChatToPlayer(player, EnumChatFormatting.YELLOW + "I want a little drink.");
			}

			if (exp.getThirst() == 15000) {
				DayZChatMessage.sendMsgChatToPlayer(player, EnumChatFormatting.YELLOW + "I want to drink.");
			}

			if (exp.getThirst() == 25000) {
				DayZChatMessage.sendMsgChatToPlayer(player, EnumChatFormatting.YELLOW + "I really want to drink ...");
			}

			if (exp.getThirst() == 32500) {
				DayZChatMessage.sendMsgChatToPlayer(player, EnumChatFormatting.DARK_RED + "I very much want to drink ...");
			}

			if (exp.getThirst() >= 35000) {
				player.attackEntityFrom(DamageSource.cactus, 20);
				return;
			} else if (player.isSprinting()) {
				exp.addThirst(2);
			} else {
				exp.addThirst(1);
			}
		}
	}

}