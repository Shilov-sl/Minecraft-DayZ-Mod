package ru.shilov.dayz.common.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import ru.shilov.dayz.DayZChatMessage;
import ru.shilov.dayz.DayZMain;
import ru.shilov.dayz.common.player.DayZExtendedPlayer;
import ru.shilov.dayz.common.potion.DayZPotion;

public class EventTickHandler {
	
	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event) {
		
	}
	
    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
    	if(event.world.getTotalWorldTime() % 1200 == 0) {

    	}
    }
    
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.START) {
			tickStart(event.player);
		} else if (event.phase == TickEvent.Phase.END && event.side.isServer()) {
			tickEnd(event.player);
		}
	}
	
	private void tickStart(EntityPlayer player) {

	}
	
	private void tickEnd(EntityPlayer player) {
        DayZExtendedPlayer exp = DayZExtendedPlayer.get(player);
		if (!player.capabilities.isCreativeMode && !player.isDead) {
			if(player.ticksExisted % 20 == 0) {
				blood(player);
				hunger(player);
				thrist(player);
				DayZMain.LOGGER.info(player.getCommandSenderName() + " blood: " + exp.getBlood());
				DayZMain.LOGGER.info(player.getCommandSenderName() + " hunger: " + exp.getFood());
				DayZMain.LOGGER.info(player.getCommandSenderName() + " thirst: " + exp.getThirst());
			}
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
	
	private void blood(EntityPlayer player) {
		DayZExtendedPlayer exp = DayZExtendedPlayer.get(player);
      	if(player.isPotionActive(DayZPotion.bleeding.id)) {
      		if(exp.getBlood() > 0 && exp.getBlood() <= exp.MAX_BLOOD) {
      			exp.subtractBlood(5);
      		} else if(exp.getBlood() == 0) {
      			player.attackEntityFrom(DamageSource.cactus, 5);
      		} else if(exp.getBlood() < 0) {
      			exp.resetBlood(0);
      		}
      	} else {
      		exp.addBlood(5);
      	}
	}
	
	private void hunger(EntityPlayer player) {
        DayZExtendedPlayer exp = DayZExtendedPlayer.get(player);
		if (exp.getFood() == 100) {
			DayZChatMessage.sendMsgChatToPlayer(player, "Поесть бы.");
		} else if (exp.getFood() == 500) {
			DayZChatMessage.sendMsgChatToPlayer(player, "Хочу есть.");
		} else if (exp.getFood() == 35000) {
			DayZChatMessage.sendMsgChatToPlayer(player, "Я реально хочу есть!");
		} else if (exp.getFood() == 42500) {
			DayZChatMessage.sendMsgChatToPlayer(player, "Дай поесть блять!");
		}
		if (exp.getFood() >= exp.MAX_FOOD) {
			player.attackEntityFrom(DamageSource.cactus, 1);
			return;
		} else if (player.isSprinting() && exp.getFood() < exp.MAX_FOOD) {
			exp.addFood(2);
		} else if(exp.getFood() < exp.MAX_FOOD) {
			exp.addFood(1);
		}
	}
	
	private void thrist(EntityPlayer player) {
        DayZExtendedPlayer exp = DayZExtendedPlayer.get(player);
		if (exp.getThirst() == 1500) {
			DayZChatMessage.sendMsgChatToPlayer(player, "Попить бы.");
		} else if (exp.getThirst() == 25000) {
			DayZChatMessage.sendMsgChatToPlayer(player, "Хочу пить.");
		} else if (exp.getThirst() == 35000) {
			DayZChatMessage.sendMsgChatToPlayer(player, "Я реально хочу пить!");
		} else if (exp.getThirst() == 42500) {
			DayZChatMessage.sendMsgChatToPlayer(player, "Дай попить блять!");
		}
		if (exp.getThirst() >= exp.MAX_THIRST) {
			player.attackEntityFrom(DamageSource.cactus, 1);
			return;
		} else if (player.isSprinting() && exp.getThirst() < exp.MAX_THIRST) {
			exp.addThirst(3);
		} else if(exp.getThirst() < exp.MAX_THIRST) {
			exp.addThirst(2);
		}
	}
	
}
