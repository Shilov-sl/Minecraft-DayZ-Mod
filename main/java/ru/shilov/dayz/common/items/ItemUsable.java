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
	private float currentAmountUse, maxAmountUse;
	private int useDuration;
	private EnumAction act;
	
	public ItemUsable(String unlocalizedName, String name, int stackSize, List description, float weight, float maxAmountUse, int useDuration, EnumAction act) {
		super(unlocalizedName, name, stackSize, description, weight);
		this.maxAmountUse = maxAmountUse;
		this.useDuration = useDuration;
		this.act = act;
	}
	
	public void writeTagCompound(ItemStack stack) {
		stack.stackTagCompound = new NBTTagCompound();
		stack.getTagCompound().setFloat("currentAmountUse", this.maxAmountUse);
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
        return this.act;
    }
    
	@Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        return stack;
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
    	super.addInformation(stack, player, list, bool);
    	if(!(stack.stackTagCompound == null))
    	list.add("" + stack.getTagCompound().getFloat("currentAmountUse"));
    }
	
}
