package ru.ensemplix.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import ru.ensemplix.SignShop;

import static org.lwjgl.opengl.GL11.*;

// Данный класс занимается отрисовкой всплывающего окна.
public class SignShopHudGui extends GuiScreen {

    private static final Minecraft minecraft = Minecraft.getMinecraft();
    private static final TextureManager textureManager = minecraft.getTextureManager();
    private static final FontRenderer fontRenderer = minecraft.fontRendererObj;

    private static final int BACKGROUND_TOP_PADDING = 60;
    private static final int BACKGROUND_WIDTH = 340;
    private static final int BACKGROUND_HEIGHT = 300;

    private final int width;
    private final int scale;
    private final int centerX;

    public SignShopHudGui(SignShop shop) {
        ScaledResolution scaled = new ScaledResolution(minecraft, minecraft.displayWidth, minecraft.displayHeight);
        width = scaled.getScaledWidth();
        scale = scaled.getScaleFactor();
        centerX = width / 2;

        drawBackground();
        drawItem(shop.getStack());
    }

    private void drawBackground() {
        int backgroundXLeft = centerX - BACKGROUND_WIDTH / 2 / scale;
        int backgroundXRight = centerX + BACKGROUND_WIDTH / 2 / scale;
        int backgroundYTop = BACKGROUND_TOP_PADDING / scale;
        int backgroundYBottom = backgroundYTop + BACKGROUND_HEIGHT / scale;

        drawGradientRect(backgroundXLeft, backgroundYTop, backgroundXRight, backgroundYBottom, 0xC0101010, 0xD0101010);
        drawHorizontalLine(backgroundXLeft, backgroundXRight - 1, backgroundYTop, 0xFFFFFFFF);
        drawHorizontalLine(backgroundXLeft, backgroundXRight - 1, backgroundYBottom - 1, 0xFFFFFFFF);
        drawVerticalLine(backgroundXLeft, backgroundYTop, backgroundYBottom, 0xFFFFFFFF);
        drawVerticalLine(backgroundXRight - 1, backgroundYTop, backgroundYBottom, 0xFFFFFFFF);
    }

    private void drawItem(ItemStack stack) {
        int itemScale = 0;
        int itemSize = 0;

        switch(scale) {
            case 1:
                itemScale = 4;
                itemSize = 30;
                break;
            default:
                itemScale = 2;
                itemSize = 15;
                break;
        }

        glPushMatrix();
        glEnable(GL_LIGHTING);
        glEnable(GL_DEPTH_TEST);
        glScalef(itemScale, itemScale, 1.0F);
        itemRender.renderItemAndEffectIntoGUI(fontRenderer, textureManager, stack, (centerX - itemSize) / itemScale, 75 / scale / itemScale);
        itemRender.renderItemIntoGUI(fontRenderer, textureManager, stack, (centerX - itemSize) / itemScale, 75 / scale / itemScale);
        glDisable(GL_LIGHTING);
        glDisable(GL_DEPTH_TEST);
        glPopMatrix();
    }

}
