package ru.shilov.dayz;

import java.io.File;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

public class DayZConfig {
	public static int bleedingStartChance;
	
	public static void loadDayZConfig(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(new File(event.getModConfigurationDirectory(), "DAYZ_MOD.cfg"));
		config.load();
		bleedingStartChance = config.get(Configuration.CATEGORY_GENERAL, "bleeding_start_chance", 15, "Bleeding start chance.").getInt(15);
		config.save();
	}
}
