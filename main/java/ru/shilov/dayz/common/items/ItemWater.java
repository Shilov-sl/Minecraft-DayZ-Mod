package ru.shilov.dayz.common.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ru.shilov.dayz.common.player.DayZExtendedPlayer;

public class ItemWater extends ItemUsable{
	private boolean isSoda;
	public ItemWater(String unlocalizedName, String name, int stackSize, List description, float weight, float maxAmountUse, int useDuration, EnumAction act, boolean isSoda) {
		super(unlocalizedName, name, stackSize, description, weight, maxAmountUse, useDuration, act);
		this.isSoda = isSoda;
	}
    
    @Override
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
        DayZExtendedPlayer exp = DayZExtendedPlayer.get(player);
        int water = (int) stack.getTagCompound().getFloat("currentAmountUse");
        if(exp.getThirst() > water) {
            exp.subtractThirst(water);
        	stack.getTagCompound().setFloat("currentAmountUse", 0);
        } else {
            int i = exp.getThirst();
        	exp.subtractThirst(i);
        	stack.getTagCompound().setFloat("currentAmountUse", water - i);
        }
    	if(isSoda && stack.getTagCompound().getFloat("currentAmountUse") == 0.0F) {
    		--stack.stackSize;
    	}
        return stack;
    }
    
	@Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(stack.getTagCompound().getFloat("currentAmountUse") > 0.0F) {
			player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		}
        return stack;
	}
	
}