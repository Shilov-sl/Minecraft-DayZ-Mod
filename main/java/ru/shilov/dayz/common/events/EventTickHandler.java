package ru.shilov.dayz.common.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import ru.shilov.dayz.DayZChatMessage;
import ru.shilov.dayz.DayZConfig;
import ru.shilov.dayz.DayZMain;
import ru.shilov.dayz.common.player.DayZExtendedPlayer;
import ru.shilov.dayz.common.potion.DayZPotion;

public class EventTickHandler {
	
	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event) {
		
	}
	
    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
    	if(event.world.getTotalWorldTime() % 20 == 0) {

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
			blood(player);
			if(player.ticksExisted % 20 == 0) {
				hunger(player);
				thrist(player);
				energy(player);
				DayZMain.LOGGER.info(player.getCommandSenderName() + " blood: " + exp.getBlood());
				DayZMain.LOGGER.info(player.getCommandSenderName() + " hunger: " + exp.getFood());
				DayZMain.LOGGER.info(player.getCommandSenderName() + " thirst: " + exp.getThirst());
				DayZMain.LOGGER.info(player.getCommandSenderName() + " energy: " + exp.getEnergy());
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
      		if(exp.getBlood() > 0 && exp.getBlood() <= exp.MAX_BLOOD && player.ticksExisted % DayZConfig.bloodSubtractTime == 0) {
      			exp.subtractBlood(DayZConfig.bloodSubtractAmount);
      		} else if(exp.getBlood() == 0) {
      			player.attackEntityFrom(DamageSource.cactus, 5);
      		} else if(exp.getBlood() < 0) {
      			exp.resetBlood(0);
      		}
      	} else {
      		if(exp.getBlood() < exp.MAX_BLOOD && player.ticksExisted % DayZConfig.bloodAddTime == 0) {
      			exp.addBlood(DayZConfig.bloodAddAmount);
      		}
      	}
	}
	
	private void hunger(EntityPlayer player) {
        DayZExtendedPlayer exp = DayZExtendedPlayer.get(player);
		if (exp.getFood() >= exp.MAX_FOOD) {
			player.attackEntityFrom(DamageSource.cactus, 1);
			return;
		} else if (player.isSprinting() && exp.getFood() < exp.MAX_FOOD) {
			exp.addFood(2);
		} else if(exp.getFood() < 0) {
      		exp.resetFood(0);
      	}else if(exp.getFood() < exp.MAX_FOOD) {
			exp.addFood(1);
		}
		if(exp.getFood() < 0) {
      		exp.resetFood(0);
      	}
	}
	
	private void thrist(EntityPlayer player) {
        DayZExtendedPlayer exp = DayZExtendedPlayer.get(player);
		if (exp.getThirst() >= exp.MAX_THIRST) {
			player.attackEntityFrom(DamageSource.cactus, 1);
			return;
		} else if (player.isSprinting() && exp.getThirst() < exp.MAX_THIRST) {
			exp.addThirst(DayZConfig.thirstAddAmount * 2);
		} else if(exp.getThirst() < exp.MAX_THIRST) {
			exp.addThirst(DayZConfig.thirstAddAmount);
		}
		if(exp.getThirst() < 0) {
      		exp.resetThirst(0);
      	}
	}
	
	private void energy(EntityPlayer player) {
        DayZExtendedPlayer exp = DayZExtendedPlayer.get(player);
        if(player.isSprinting() && exp.getEnergy() > 0 && exp.getEnergy() <= exp.MAX_ENERGY) {
        	exp.subtractEnergy(1);
        } else if(exp.getEnergy() == 0) {
			DayZChatMessage.sendMsgChatToPlayer(player, "Я устал, нужно отдохнуть!");
        	player.setSprinting(false);
        }
        if(!player.isSprinting() && exp.getEnergy() < exp.MAX_ENERGY) {
        	exp.addEnergy(1);
        }
        if(exp.getEnergy() < 0) {
      		exp.resetEnergy(0);
      	}
	}
	
}
