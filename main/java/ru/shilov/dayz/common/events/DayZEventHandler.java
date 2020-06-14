package ru.shilov.dayz.common.events;

import java.util.Random;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.world.BlockEvent;
import ru.shilov.dayz.DayZChatMessage;
import ru.shilov.dayz.DayZConfig;
import ru.shilov.dayz.common.entity.EntityDeadBody;
import ru.shilov.dayz.common.player.DayZExtendedPlayer;
import ru.shilov.dayz.common.potion.DayZPotion;
import ru.shilov.dayz.common.potion.DayZPotionEffect;

public class DayZEventHandler {
	public Random rand = new Random();
	
	@SubscribeEvent
	public void onLivingUpdateEvent(LivingUpdateEvent event) {
		
	}
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
        if(event.entity instanceof EntityPlayer && DayZExtendedPlayer.get((EntityPlayer)event.entity) == null) {
        	DayZExtendedPlayer.register((EntityPlayer)event.entity);	
        }
	}
	
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		
	}
	
	@SubscribeEvent
	public void livingHurtEvent(LivingHurtEvent event) {
		if (event.entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			if (player != null && !player.isDead) {
				if (event.ammount > rand.nextInt(DayZConfig.bleedingStartChance + 10)) {
					DayZChatMessage.sendMsgChatToPlayer(player, EnumChatFormatting.DARK_RED + "You started bleeding!");
					player.addPotionEffect(new DayZPotionEffect(DayZPotion.bleeding.id, 600 + rand.nextInt(600), 5));
				}
			}
		}
	}
	
	@SubscribeEvent
	public void playerDropsEvent(PlayerDropsEvent event) {
		event.setCanceled(true);
	}
	
	@SubscribeEvent
	public void onLivingDeathEvent(LivingDeathEvent event) {
		Entity killed = event.entity;
		Entity killer = event.source.getEntity();
		if (killed instanceof EntityPlayer) {
			//LDPlayerHandler.saveProxyData((EntityPlayer) event.entity);
			EntityDeadBody body = new EntityDeadBody(event.entity.worldObj);
			body.setLocationAndAngles(killed.posX, killed.posY, killed.posZ, killed.rotationYaw, killed.rotationPitch);
			body.setCurrentItemID(((EntityPlayer) killed).inventory.currentItem);
			body.setPlayerProfile(((EntityPlayer) killed).getGameProfile());
			int i = 0;
			for (ItemStack stack : ((EntityPlayer) killed).inventory.mainInventory) {
				body.inventory.mainInventory[i] = stack;
				i++;
			}
			int i1 = 0;
			for (ItemStack stack : ((EntityPlayer) killed).inventory.armorInventory) {
				//body.inventory.armorInventory[i1] = stack;
				body.setCurrentItemOrArmor(i1, stack);
				i1++;
			}
			body.setCurrentItemOrArmor(4, ((EntityPlayer) killed).inventory.getStackInSlot(((EntityPlayer) killed).inventory.currentItem));
			//if (!event.entity.worldObj.isRemote)
			{
				event.entity.worldObj.spawnEntityInWorld(body);
			}
		}
	}
	
	@SubscribeEvent
	public void livingAttackEvent(LivingAttackEvent event) {
		
	}
	
	@SubscribeEvent
	public void breakEvent(BlockEvent.BreakEvent event) {
		
	}
	
	@SubscribeEvent
	public void onItemPickup(EntityItemPickupEvent event) {
		
	}
	
	
}
