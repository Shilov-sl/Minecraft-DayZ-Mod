package ru.shilov.dayz.common.potion;

import net.minecraft.potion.Potion;

public class DayZPotion extends Potion {
	public static DayZPotion bleeding;
	public static DayZPotion hypothermia;

	public DayZPotion(int id, boolean isBadEffect, int color, String name) {
		super(id, isBadEffect, color);
		setPotionName("potion." + name);
	}

	@Override
	public Potion setIconIndex(int x, int y) {
		super.setIconIndex(x, y);
		return this;
	}

	public static void loadLDPotions() {
		bleeding = new DayZPotion(29, true, 5149489, "bleeding");
		hypothermia = new DayZPotion(30, true, 0, "hypothermia");
	}

	public static void registerLDPotions() {
		Potion.potionTypes[29] = bleeding;
		Potion.potionTypes[30] = hypothermia;
	}
}
