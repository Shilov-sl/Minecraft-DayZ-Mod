package ru.shilov.dayz.common.player;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class DayZExtendedPlayer implements IExtendedEntityProperties {
	public final static String EXT_PROP_NAME = "DayZExtendedPlayer";
	private final EntityPlayer player;
	public static final int thirst = 25;
	
	public DayZExtendedPlayer(EntityPlayer player) {
		this.player = player;
		this.player.getDataWatcher().addObject(thirst, 100);
	}
	
    public static final void register(EntityPlayer player) {
        player.registerExtendedProperties(EXT_PROP_NAME, new DayZExtendedPlayer(player));
    }

    public static final DayZExtendedPlayer get(EntityPlayer player) {
        return (DayZExtendedPlayer)player.getExtendedProperties(EXT_PROP_NAME);
    }
    
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = new NBTTagCompound();
		properties.setInteger("thirst", this.player.getDataWatcher().getWatchableObjectInt(thirst));
		compound.setTag( EXT_PROP_NAME, properties);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = (NBTTagCompound)compound.getTag(EXT_PROP_NAME);
		this.player.getDataWatcher().updateObject(thirst, properties.getInteger("thirst"));
		
	}

	@Override
	public void init(Entity entity, World world) {
		
	}
	
	// THIRST START
	public final void resetThirst(int amount) {
		this.player.getDataWatcher().updateObject(this.thirst, amount);
	}

	public void addThirst(int amout) {
		this.player.getDataWatcher().updateObject(this.thirst, getThirst() + amout);
	}

	public void subtractThirst(int amout) {
		if (getThirst() > 0) {
			System.out.println(getThirst());
			this.player.getDataWatcher().updateObject(this.thirst, getThirst() - amout);
		}
	}
	
	public int getThirst() {
		return this.player.getDataWatcher().getWatchableObjectInt(this.thirst);
	}
	
}
