package ru.shilov.dayz.common.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ru.shilov.dayz.common.player.DayZExtendedPlayer;
import ru.shilov.dayz.common.potion.DayZPotion;

public class ItemMedicine extends ItemUsable {
	private boolean removesBleeding;
	
	public ItemMedicine(String unlocalizedName, String name, int stackSize, List description, float weight, EnumAction typeAction, int useDuration, int maxAmountUse, boolean oneOff, boolean removesBleeding) {
		super(unlocalizedName, name, stackSize, description, weight, typeAction, useDuration, maxAmountUse, oneOff);
		this.removesBleeding = removesBleeding;
	}
    
    @Override
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
        DayZExtendedPlayer exp = DayZExtendedPlayer.get(player);
        if(this.removesBleeding) {   
        	player.removePotionEffect(DayZPotion.bleeding.id);
        }  
        int amount = stack.getTagCompound().getInteger("currentAmountUse");
        stack.getTagCompound().setInteger("currentAmountUse", amount - 1);
		if(this.oneOff && amount == 0) {
			--stack.stackSize;
		}
        return stack;
    }
    
	@Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(stack.getTagCompound().getInteger("currentAmountUse") > 0 && player.isPotionActive(DayZPotion.bleeding.id)) {
			player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		}
        return stack;
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
    	super.addInformation(stack, player, list, bool);
    	list.add("Removes Bleeding: " + this.removesBleeding);
    }

}
