package ru.shilov.dayz;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import ru.shilov.dayz.common.CommonEvents;
import ru.shilov.dayz.common.CommonProxy;
import ru.shilov.dayz.common.items.ItemClip;
import ru.shilov.dayz.common.items.ItemEat;
import ru.shilov.dayz.common.items.ItemWater;
import ru.shilov.dayz.network.PacketHandler;

@Mod(modid = DayZMain.MODID, name = DayZMain.NAME, version = DayZMain.MODID)

public class DayZMain {
	@Instance(DayZMain.MODID)
	public static DayZMain INSTANCE;
	public static final String MODID = "dayz", NAME = "DayzMod", VERSION = "a0.1";
    @SidedProxy(clientSide = "ru.shilov.dayz.client.ClientProxy", serverSide = "ru.shilov.dayz.common.CommonProxy")
    public static CommonProxy PROXY;
	public static final PacketHandler PACKETS = new PacketHandler();
	public static final Logger LOGGER = LogManager.getLogger(NAME);
	public static final boolean SERVER = true;
	public static CreativeTabs customTab;
	
	public static Item plasticBottle;
	public static Item sodaNolaCola;
	public static Item freshApple;
	public static Item cannedBakedBeans;
	
	public static Item nato30Mag;
	
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	customTab = new CustomCreativeTab();
    	
    	ArrayList list = new ArrayList();
    	list.add("Многоразовая бутылка.");
    	//					ItemWater(unlocalizedName, name, fileName, description, weight, oneUse, useDuration) {
    	plasticBottle = new ItemWater("plastic_bottle", "Пластиковая бутылка", 1, list, 1.0F, 10000.0F, 32, EnumAction.drink, false);
    	sodaNolaCola = new ItemWater("soda_nolacola", "Nola Cola", 1, list, 0.5F, 100.0F, 25, EnumAction.drink, true);
    	freshApple = new ItemEat("fresh_apple", "Яблоко", 1, list, 1.0F, 100.0F, 16, EnumAction.eat);
    	cannedBakedBeans = new ItemEat("canned_bakedbeans", "Банка бобов", 1, list, 0.5F, 100.0F, 25, EnumAction.eat);
    	
    	//				ItemClip(unlocalizedName, name, stackSize, description, weight, maxBullet)
    	nato30Mag = new ItemClip("nato_30mag", "Обойма NATO 30 патронов", 1, list, 0.5F, 30, 30);
    	
    	PROXY.preInit(event);
        LOGGER.info("Pre initialization event is done.");
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	PROXY.init(event);
    	PACKETS.initialise();
    	CommonEvents handler = new CommonEvents();
        MinecraftForge.EVENT_BUS.register(handler);
        FMLCommonHandler.instance().bus().register(handler);
        LOGGER.info("Initialization event is done.");
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	PROXY.postInit(event);
    	PACKETS.postInitialise();
        LOGGER.info("Post initialization event is done.");
    }
    
    @EventHandler
    public void serverStart(FMLServerStartingEvent e) {
        LOGGER.info("Server started is done.");
    }
    
    @EventHandler
    public void serverStop(FMLServerStoppingEvent e) {
        LOGGER.info("Server stoped is done.");
    }

}
