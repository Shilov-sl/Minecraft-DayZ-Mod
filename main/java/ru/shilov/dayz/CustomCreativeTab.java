package ru.shilov.dayz;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class CustomCreativeTab extends CreativeTabs {

   public CustomCreativeTab() {
      super(DayZMain.MODID);
   }

   public String getTranslatedTabLabel() {
      return this.getTabLabel();
   }

   @SideOnly(Side.CLIENT)
   public Item getTabIconItem() {
      return Items.cake;
   }
}
