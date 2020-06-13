package ru.shilov.dayz.common.items;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemBullet extends CustomItem {

	public ItemBullet(String unlocalizedName, String name, int stackSize, List description, float weight) {
		super(unlocalizedName, name, stackSize, description, weight);
	}
    
}
