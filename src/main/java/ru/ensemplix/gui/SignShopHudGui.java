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

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        drawGradientRect(backgroundXL, backgroundYT, backgroundXR, backgroundYB, 0xC0101010, 0xD0101010);
        drawHorizontalLine(backgroundXL, backgroundXR - 1, backgroundYT, 0xFFFFFFFF);
        drawHorizontalLine(backgroundXL, backgroundXR - 1, backgroundYB - 1, 0xFFFFFFFF);
        drawVerticalLine(backgroundXL, backgroundYT, backgroundYB, 0xFFFFFFFF);
        drawVerticalLine(backgroundXR - 1, backgroundYT, backgroundYB, 0xFFFFFFFF);
    }

}
