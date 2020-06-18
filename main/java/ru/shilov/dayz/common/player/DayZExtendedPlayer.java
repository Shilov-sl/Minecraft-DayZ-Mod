package ru.shilov.dayz.common.player;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import ru.shilov.dayz.DayZConfig;

public class DayZExtendedPlayer implements IExtendedEntityProperties {
	public final static String EXT_PROP_NAME = "DayZExtendedPlayer";
	private final EntityPlayer player;
	public final int MAX_BLOOD = DayZConfig.bloodMax, MAX_FOOD = 50000, MAX_THIRST = DayZConfig.thirstMax, MAX_ENERGY = 100;
	public static final int blood = 25, food = 26, thirst = 27, temperature = 28, energy = 29, weight = 30, maxWeight = 31;
	
	public DayZExtendedPlayer(EntityPlayer player) {
		this.player = player;
		this.player.getDataWatcher().addObject(blood, MAX_BLOOD);
		this.player.getDataWatcher().addObject(food, 0);
		this.player.getDataWatcher().addObject(thirst, 0);
		this.player.getDataWatcher().addObject(temperature, 0);
		this.player.getDataWatcher().addObject(energy, MAX_ENERGY);
		this.player.getDataWatcher().addObject(weight, 0);
		this.player.getDataWatcher().addObject(maxWeight, 50);
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
		properties.setInteger("blood", this.player.getDataWatcher().getWatchableObjectInt(blood));
		properties.setInteger("food", this.player.getDataWatcher().getWatchableObjectInt(food));
		properties.setInteger("thirst", this.player.getDataWatcher().getWatchableObjectInt(thirst));
		properties.setInteger("temperature", this.player.getDataWatcher().getWatchableObjectInt(temperature));
		properties.setInteger("energy", this.player.getDataWatcher().getWatchableObjectInt(energy));
		properties.setFloat("weight", this.player.getDataWatcher().getWatchableObjectInt(weight));
		properties.setInteger("maxWeight", this.player.getDataWatcher().getWatchableObjectInt(maxWeight));
		compound.setTag( EXT_PROP_NAME, properties);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = (NBTTagCompound)compound.getTag(EXT_PROP_NAME);
		this.player.getDataWatcher().updateObject(blood, properties.getInteger("blood"));
		this.player.getDataWatcher().updateObject(food, properties.getInteger("food"));
		this.player.getDataWatcher().updateObject(thirst, properties.getInteger("thirst"));
		this.player.getDataWatcher().updateObject(temperature, properties.getInteger("temperature"));
		this.player.getDataWatcher().updateObject(energy, properties.getInteger("energy"));
		this.player.getDataWatcher().updateObject(weight, properties.getFloat("weight"));
		this.player.getDataWatcher().updateObject(maxWeight, properties.getInteger("maxWeight"));
		
	}

	@Override
	public void init(Entity entity, World world) {
		
	}
	
	// BLOOD START
	public final void resetBlood(int amount) {
		this.player.getDataWatcher().updateObject(this.blood, amount);
	}

	public void addBlood(int amout) {
		this.player.getDataWatcher().updateObject(this.blood, getBlood() + amout);
	}

	public void subtractBlood(int amout) {
		if (getBlood() > 0) {
			this.player.getDataWatcher().updateObject(this.blood, getBlood() - amout);
		}
	}
	
	public int getBlood() {
		return this.player.getDataWatcher().getWatchableObjectInt(this.blood);
	}
	
	// FOOD START
	public final void resetFood(int amount) {
		this.player.getDataWatcher().updateObject(this.food, amount);
	}

	public void addFood(int amout) {
		this.player.getDataWatcher().updateObject(this.food, getFood() + amout);
	}

	public void subtractFood(int amout) {
		if (getFood() > 0) {
			this.player.getDataWatcher().updateObject(this.food, getFood() - amout);
		}
	}
	
	public int getFood() {
		return this.player.getDataWatcher().getWatchableObjectInt(this.food);
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
			this.player.getDataWatcher().updateObject(this.thirst, getThirst() - amout);
		}
	}
	
	public int getThirst() {
		return this.player.getDataWatcher().getWatchableObjectInt(this.thirst);
	}
	
	// ENERGY START
	public final void resetEnergy(int amount) {
		this.player.getDataWatcher().updateObject(this.energy, amount);
	}

	public void addEnergy(int amout) {
		this.player.getDataWatcher().updateObject(this.energy, getEnergy() + amout);
	}

	public void subtractEnergy(int amout) {
		if (getEnergy() > 0) {
			this.player.getDataWatcher().updateObject(this.energy, getEnergy() - amout);
		}
	}
	
	public int getEnergy() {
		return this.player.getDataWatcher().getWatchableObjectInt(this.energy);
	}
	
}
