package ru.ensemplix.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import ru.ensemplix.SignShop;
import ru.ensemplix.gui.text.TextRenderer;

import static org.lwjgl.opengl.GL11.*;

// Данный класс занимается отрисовкой всплывающего окна.
public class SignShopHudGui extends GuiScreen {

    private static final Minecraft minecraft = Minecraft.getMinecraft();
    private static final TextureManager textureManager = minecraft.getTextureManager();
    private static final FontRenderer fontRenderer = minecraft.fontRendererObj;
    private static final TextRenderer textRenderer = new TextRenderer(20);

    private static final int BACKGROUND_TOP_PADDING = 60;
    private static final int BACKGROUND_WIDTH = 320;
    private static final int BACKGROUND_HEIGHT = 250;

    private final int scale;
    private final int centerX;

    public SignShopHudGui(SignShop shop) {
        ScaledResolution scaled = new ScaledResolution(minecraft, minecraft.displayWidth, minecraft.displayHeight);
        scale = scaled.getScaleFactor();
        centerX = scaled.getScaledWidth() / 2;

        drawBackground();
        drawItem(shop.getStack());
        drawHeader(shop.getOwner(), shop.getStack().getDisplayName(), shop.getQuantity());
        drawPrices(shop.getBuyPrice(), shop.getSellPrice());
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
        int itemScale;
        int itemSize;

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

    private void drawHeader(String owner, String itemName, int quantity) {
        textRenderer.drawCenteredString(owner.toUpperCase(), centerX, 140 / scale, 0xFF5DE8ED, false);
        textRenderer.drawCenteredString(itemName.toUpperCase(), centerX, 175 / scale, 0xFFFFFFFF, false);
        textRenderer.drawCenteredString(quantity + " ШТ.", centerX, 200 / scale, 0xFFFFFFFF, false);
    }

    private void drawPrices(int buy, int sell) {
        drawHorizontalLine(centerX - 120 / scale, centerX + 120 / scale, 230 / scale, 0xFFFFFFFF);

        if(buy > 0 && sell > 0) {
            drawVerticalLine(centerX, 240 / scale, 290 / scale, 0xFFFFFFFF);
            drawPrice("КУПИТЬ", centerX - 60 / scale, 250 / scale, buy, 0xFF15F745);
            drawPrice("ПРОДАТЬ", centerX + 70 / scale, 250 / scale, sell, 0xFFF7E415);
        } else if(buy > 0) {
            drawPrice("КУПИТЬ", centerX,250 / scale, buy, 0xFF15F745);
        } else if(sell > 0) {
            drawPrice("ПРОДАТЬ", centerX,250 / scale, sell, 0xFFF7E415);
        }
    }

    private void drawPrice(String title, int x, int y, int price, int color) {
        textRenderer.drawCenteredString(title, x, y, color, false);
        textRenderer.drawCenteredString(String.valueOf(price), x, y + 25 / scale, 0xFFFFFFFF, false);
    }

}
