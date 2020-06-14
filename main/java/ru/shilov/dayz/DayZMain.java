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
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import ru.shilov.dayz.common.CommonProxy;
import ru.shilov.dayz.common.entity.EntityDeadBody;
import ru.shilov.dayz.common.events.DayZEventHandler;
import ru.shilov.dayz.common.events.EventPlayerHandler;
import ru.shilov.dayz.common.events.EventTickHandler;
import ru.shilov.dayz.common.items.ItemClip;
import ru.shilov.dayz.common.items.ItemFoodWater;
import ru.shilov.dayz.common.items.ItemMedicine;
import ru.shilov.dayz.common.items.ItemUsable;
import ru.shilov.dayz.common.potion.DayZPotion;
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
	
	public static Item bandage;
	
	public static Item nato30Mag;
	
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	PROXY.preInit(event);
    	DayZPotion.loadLDPotions();
    	DayZPotion.registerLDPotions();
		DayZConfig.loadDayZConfig(event);
		EntityRegistry.registerModEntity(EntityDeadBody.class, "DeadBody", 90, DayZMain.INSTANCE, 80, 1, true);
        FMLCommonHandler.instance().bus().register(new EventPlayerHandler());
        FMLCommonHandler.instance().bus().register(new EventTickHandler());
        MinecraftForge.EVENT_BUS.register(new DayZEventHandler());
        
    	customTab = new CustomCreativeTab();
    	
    	ArrayList list = new ArrayList();
    	list.add("Многоразовая бутылка.");
    	// 					ItemUsable(unlocalizedName, name, stackSize, description, weight, typeAction, useDuration, maxAmountUse, food, water, oneOff) 
    	plasticBottle = new ItemFoodWater("plastic_bottle", "Пластиковая бутылка", 1, list, 1.0F, EnumAction.drink, 30, 10, false, 10, 25);
    	sodaNolaCola = new ItemFoodWater("soda_nolacola", "Nola Cola", 1, list, 0.5F, EnumAction.drink, 30, 10, true, 10, 25);
    	freshApple = new ItemFoodWater("fresh_apple", "Яблоко", 1, list, 1.0F, EnumAction.eat, 30, 5, true, 10, 25);
    	cannedBakedBeans = new ItemFoodWater("canned_bakedbeans", "Банка бобов", 1, list, 0.5F, EnumAction.eat, 30, 3, true, 10, 25);
    	
    	bandage = new ItemMedicine("bandage", "Бинт", 1, list, 0.5F, EnumAction.bow, 30, 4, true, true);
    	
    	//				ItemClip(unlocalizedName, name, stackSize, description, weight, maxBullet)
    	nato30Mag = new ItemClip("nato_30mag", "Обойма NATO 30 патронов", 1, list, 0.5F, 30, 30);
    	
        LOGGER.info("Pre initialization event is done.");
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	PROXY.init(event);
    	PACKETS.initialise();
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
