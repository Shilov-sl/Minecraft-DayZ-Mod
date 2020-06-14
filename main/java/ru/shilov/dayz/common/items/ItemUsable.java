package ru.shilov.dayz.common.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemUsable extends CustomItem{
	private int currentAmountUse, maxAmountUse;
	private int useDuration;
	private EnumAction typeAction;
	protected boolean oneOff;
	
	public ItemUsable(String unlocalizedName, String name, int stackSize, List description, float weight, EnumAction typeAction, int useDuration, int maxAmountUse, boolean oneOff) {
		super(unlocalizedName, name, stackSize, description, weight);
		this.typeAction = typeAction;
		this.useDuration = useDuration;
		this.maxAmountUse = maxAmountUse;
		this.oneOff = oneOff;
	}
	
	public void writeTagCompound(ItemStack stack) {
		stack.stackTagCompound = new NBTTagCompound();
		stack.getTagCompound().setInteger("currentAmountUse", this.maxAmountUse);
		stack.getTagCompound().setInteger("maxAmountUse", this.maxAmountUse);
	}
	
    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5) {
    	super.onUpdate(stack, world, entity, par4, par5);
		if(stack.stackTagCompound == null) {
			this.writeTagCompound(stack);
		} 

    }
    
    @Override
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
        return stack;
    }
    
    @Override
    public int getMaxItemUseDuration(ItemStack p_77626_1_) {
        return this.useDuration;
    }
    
    @Override
    public EnumAction getItemUseAction(ItemStack p_77661_1_) {
        return this.typeAction;
    }
    
	@Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(stack.getTagCompound().getInteger("currentAmountUse") > 0) {
			player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		}
        return stack;
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
    	super.addInformation(stack, player, list, bool);
    	if(!(stack.stackTagCompound == null))
    	list.add("Type Action: " + this.typeAction);
    	list.add("Use Duration: " + this.useDuration);
		if(stack.stackTagCompound != null) {
			list.add("Max Amount Use: " + stack.getTagCompound().getInteger("maxAmountUse"));
			list.add("Current Amount Use: " + stack.getTagCompound().getInteger("currentAmountUse"));
		}
    	list.add("One Off: " + this.oneOff);
    }
	
}
