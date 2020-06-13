package ru.shilov.dayz.common.items;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import ru.shilov.dayz.DayZMain;

public class CustomItem extends Item{
	private String unlocalizedName;
	private List description;
	public float weight;
	
	
	public CustomItem(String unlocalizedName, String name, int stackSize, List description, float weight) {
		this.unlocalizedName = unlocalizedName;
		this.setUnlocalizedName(unlocalizedName);
		this.setCreativeTab(DayZMain.customTab);
		LanguageRegistry.addName(this, name);
		this.maxStackSize = Math.max(1, Math.min(64, stackSize));
		this.description = description;
		this.weight = weight;
		GameRegistry.registerItem(this, unlocalizedName);

	}

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
    	list.addAll(this.description);
    }
    
	@SideOnly(Side.CLIENT)
    @Override
	public void registerIcons(IIconRegister register) {
		this.itemIcon = register.registerIcon(DayZMain.MODID + ":" + this.unlocalizedName);
	}
    
}