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

public class ItemFoodWater extends ItemUsable {
	private int food, water;
	
	public ItemFoodWater(String unlocalizedName, String name, int stackSize, List description, float weight, EnumAction typeAction, int useDuration, int maxAmountUse, boolean oneOff, int food, int water) {
		super(unlocalizedName, name, stackSize, description, weight, typeAction, useDuration, maxAmountUse, oneOff);
		this.food = food;
		this.water = water;
	}
    
    @Override
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
        DayZExtendedPlayer exp = DayZExtendedPlayer.get(player);
        if(this.food != 0) {
        	exp.subtractFood(this.food);       
        }
        if(this.water != 0) {
        	exp.subtractThirst(this.water);
        }
        int amount = stack.getTagCompound().getInteger("currentAmountUse");
        stack.getTagCompound().setInteger("currentAmountUse", amount - 1);
		if(this.oneOff && amount == 0) {
			--stack.stackSize;
		}
        return stack;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
    	super.addInformation(stack, player, list, bool);
    	list.add("Food: " + this.food);
    	list.add("Water: " + this.water);
    }

}
