package ru.shilov.dayz;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;

public class DayZDamageSource extends DamageSource {
	public static final DamageSource bleedOut = new DayZDamageSource("bleedOut")
			.setDamageBypassesArmor();
	public static final DamageSource thirstDeath = new DayZDamageSource(
			"thirstDeath").setDamageBypassesArmor();
	public static final DamageSource hypertermia = new DayZDamageSource(
			"hypertermia").setDamageBypassesArmor();

	public DayZDamageSource(String damageType) {
		super(damageType);
	}

}