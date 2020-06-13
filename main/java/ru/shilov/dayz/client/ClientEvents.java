package ru.shilov.dayz.client;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Text;

public class ClientEvents {
	public Minecraft mc = Minecraft.getMinecraft();
	
	/* @SubscribeEvent
	public void onGuiOpen(GuiOpenEvent event) {
		if(event.gui instanceof GuiMainMenu && !(event.gui instanceof CustomGuiMainMenu)) {
			event.setCanceled(true);
			Minecraft.getMinecraft().displayGuiScreen(new CustomGuiMainMenu());
		}
	} */
	
	/*@SubscribeEvent
    public void disableDrawHealth(RenderGameOverlayEvent.Pre event) {
        switch (event.type) {
            case HEALTH:
                event.setCanceled(true);
                break;
            case FOOD:
                event.setCanceled(true);
                break;
            case EXPERIENCE:
                event.setCanceled(true);
                break;
            case HOTBAR:
                event.setCanceled(true);
                break;
            case ARMOR:
                event.setCanceled(true);
                break;  
            case CROSSHAIRS:
            	if(!mc.thePlayer.capabilities.isCreativeMode)
            		event.setCanceled(true);
                break;
		default:
			break;
        }
    }*/
	
	@SubscribeEvent
    public void gameRender(RenderGameOverlayEvent event) {
        if (event instanceof Text && !this.mc.gameSettings.showDebugInfo) {

        }
    }
	
}
