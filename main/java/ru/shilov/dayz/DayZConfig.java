package ru.shilov.dayz;

import java.io.File;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

public class DayZConfig {
	public static int bleedingStartChance;
	public static int bloodMax;
	public static int bloodAddAmount;
	public static int bloodSubtractAmount;
	public static int bloodSubtractTime;
	public static int bloodAddTime;
	
	public static int thirstMax;
	public static int thirstAddAmount;
	public static int thirstSubtractAmount;
	public static int thirstSubtractTime;
	public static int thirstAddTime;
	
	
	public static void loadDayZConfig(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(new File(event.getModConfigurationDirectory(), "DAYZ_MOD.cfg"));
		config.load();
		bleedingStartChance = config.get(Configuration.CATEGORY_GENERAL, "bleeding_start_chance", 15, "Шанс кровотечения.").getInt(15);
		bloodMax = config.get(Configuration.CATEGORY_GENERAL, "blood_max", 5000, "Максимальное количество крови игрока.").getInt(5000);
		bloodAddAmount = config.get(Configuration.CATEGORY_GENERAL, "blood_add_amount", 5, "Значение добавляемой крови.").getInt(5);
		bloodSubtractAmount = config.get(Configuration.CATEGORY_GENERAL, "blood_subtract_amount", 5, "Значения убываемой крови.").getInt(5);
		bloodSubtractTime = config.get(Configuration.CATEGORY_GENERAL, "blood_subtract_time", 20, "Промежуток времени за который убывает кровь.").getInt(20);
		bloodAddTime = config.get(Configuration.CATEGORY_GENERAL, "blood_add_time", 20, "Промежуток времени за который поднимается кровь.").getInt(20);
		
		thirstMax = config.get(Configuration.CATEGORY_GENERAL, "thirst_max", 5000, "Максимальное количество жажды игрока.").getInt(5000);
		thirstAddAmount = config.get(Configuration.CATEGORY_GENERAL, "thirst_add_amount", 5, "Значение добавляемой жажды.").getInt(5);
		thirstSubtractAmount = config.get(Configuration.CATEGORY_GENERAL, "thirst_subtract_amount", 5, "Значения убываемой жажды.").getInt(5);
		thirstSubtractTime = config.get(Configuration.CATEGORY_GENERAL, "thirst_subtract_time", 20, "Промежуток времени за который убывает жажда.").getInt(20);
		thirstAddTime = config.get(Configuration.CATEGORY_GENERAL, "thirst_add_time", 20, "Промежуток времени за который поднимается жажда.").getInt(20);
		config.save();
	}
}
