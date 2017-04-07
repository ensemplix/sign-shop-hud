package ru.ensemplix.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;
import ru.ensemplix.SignShop;

public class SignShopHudGui extends GuiScreen {

    private static final Minecraft minecraft = Minecraft.getMinecraft();

    public SignShopHudGui(SignShop shop) {
        ScaledResolution scaled = new ScaledResolution(minecraft, minecraft.displayWidth, minecraft.displayHeight);
        int width = scaled.getScaledWidth();
        int height = scaled.getScaledHeight();
        int factor = scaled.getScaleFactor();
        int centerX = width / 2;

        int backgroundXL = centerX - 170 / factor;
        int backgroundXR = centerX + 170 / factor;
        int backgroundYT = 60 / factor;
        int backgroundYB = backgroundYT + 300 / factor;

        drawGradientRect(backgroundXL, backgroundYT, backgroundXR, backgroundYB, 0xC0101010, 0xD0101010);
        drawHorizontalLine(backgroundXL, backgroundXR - 1, backgroundYT, 0xFFFFFFFF);
        drawHorizontalLine(backgroundXL, backgroundXR - 1, backgroundYB - 1, 0xFFFFFFFF);
        drawVerticalLine(backgroundXL, backgroundYT, backgroundYB, 0xFFFFFFFF);
        drawVerticalLine(backgroundXR - 1, backgroundYT, backgroundYB, 0xFFFFFFFF);

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glScalef(2.0f, 2.0f, 1.0f);
        itemRender.renderItemAndEffectIntoGUI(minecraft.fontRenderer, minecraft.getTextureManager(), shop.getStack(), (centerX - 15) / 2, 75 / factor / 2);
        itemRender.renderItemIntoGUI(minecraft.fontRenderer, minecraft.getTextureManager(), shop.getStack(), (centerX - 15) / 2, 75 / factor / 2);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glPopMatrix();
    }

}
