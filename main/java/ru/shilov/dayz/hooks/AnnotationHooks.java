package ru.shilov.dayz.hooks;

import org.lwjgl.opengl.GL11;

import gloomyfolken.hooklib.asm.Hook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import ru.shilov.dayz.common.items.ItemUsable;

public class AnnotationHooks {
	private static FontRenderer fontRendererObj;
    private static Minecraft mc;
    private static GuiScreen screen;
    
	@Hook(injectOnExit = true)
    public static void renderItemOverlayIntoGUI(RenderItem clazz, FontRenderer p_94148_1_, TextureManager p_94148_2_, ItemStack p_94148_3_, int p_94148_4_, int p_94148_5_, String p_94148_6_) {        
            if(p_94148_3_ != null && p_94148_3_.getItem() instanceof ItemUsable && p_94148_3_.stackTagCompound != null) {
            	//ItemWater item = (ItemWater) p_94148_3_.getItem();
                 //double health = p_94148_3_.getItem().getDurabilityForDisplay(p_94148_3_);
                 //int k = (int)Math.round(255.0D - health * 255.0D);
                 int k = (int)Math.round((double)p_94148_3_.getTagCompound().getFloat("currentAmountUse") * 0.14);
                 GL11.glDisable(GL11.GL_LIGHTING);
                 GL11.glDisable(GL11.GL_DEPTH_TEST);
                 GL11.glDisable(GL11.GL_TEXTURE_2D);
                 GL11.glDisable(GL11.GL_ALPHA_TEST);
                 GL11.glDisable(GL11.GL_BLEND);
                 Tessellator tessellator = Tessellator.instance;
                 renderQuad(tessellator, p_94148_4_ + 2, p_94148_5_ + 14, 13, 1, 1);
                 renderQuad(tessellator, p_94148_4_ + 2, p_94148_5_ + 14, k - 1, 1, -1);  
                 //GL11.glEnable(GL11.GL_BLEND); // Forge: Disable Bled because it screws with a lot of things down the line.
                 GL11.glEnable(GL11.GL_ALPHA_TEST);
                 GL11.glEnable(GL11.GL_TEXTURE_2D);
                 GL11.glEnable(GL11.GL_LIGHTING);
                 GL11.glEnable(GL11.GL_DEPTH_TEST);
                 GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            }
        
    }

    private static void renderQuad(Tessellator p_77017_1_, int p_77017_2_, int p_77017_3_, int p_77017_4_, int p_77017_5_, int p_77017_6_) {
        p_77017_1_.startDrawingQuads();
        p_77017_1_.setColorOpaque_I(p_77017_6_);
        p_77017_1_.addVertex((double)(p_77017_2_ - 1), (double)(p_77017_3_ + 0), 0.0D);
        p_77017_1_.addVertex((double)(p_77017_2_ - 1), (double)(p_77017_3_ + p_77017_5_), 0.0D);
        p_77017_1_.addVertex((double)(p_77017_2_ + p_77017_4_), (double)(p_77017_3_ + p_77017_5_), 0.0D);
        p_77017_1_.addVertex((double)(p_77017_2_ + p_77017_4_), (double)(p_77017_3_ + 0), 0.0D);
        p_77017_1_.draw();
    }
    
}