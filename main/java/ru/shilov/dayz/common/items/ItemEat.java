package ru.shilov.dayz.common.items;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemEat extends ItemUsable{
	public ItemEat(String unlocalizedName, String name, int stackSize, List description, float weight, float maxAmountUse, int useDuration, EnumAction act) {
		super(unlocalizedName, name, stackSize, description, weight, maxAmountUse, useDuration, act);
	}

    @Override
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
    	stack.getTagCompound().setFloat("currentAmountUse", stack.getTagCompound().getFloat("currentAmountUse") - 10.0F);
    	if(stack.getTagCompound().getFloat("currentAmountUse") == 0.0F) {
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