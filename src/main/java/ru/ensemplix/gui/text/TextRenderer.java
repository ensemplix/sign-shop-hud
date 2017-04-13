package ru.ensemplix.gui.text;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import ru.ensemplix.gui.text.internal.StringCache;

import java.awt.*;

import static java.awt.Font.TRUETYPE_FONT;

public class TextRenderer {

    private static final IResourceManager resourceManager = Minecraft.getMinecraft().getResourceManager();
    private final StringCache stringCache;

    public TextRenderer(int fontSize) {
        ResourceLocation resource = new ResourceLocation("signshophud", "fonts/MyriadProSemiBold.otf");
        Font font;

        try {
            font = Font.createFont(TRUETYPE_FONT, resourceManager.getResource(resource).getInputStream());
        } catch(Exception e) {
            throw new RuntimeException(e);
        }

        stringCache = new StringCache(colorCodes());
        stringCache.setDefaultFont(font, fontSize, true);
    }

    public void drawCenteredString(String text, int x, int y, int color, boolean shadow) {
        drawString(text, x  - stringCache.getStringWidth(text) / 2, y, color, shadow);
    }

    public void drawString(String text, int x, int y, int color, boolean shadow) {
        stringCache.renderString(text, x, y, color, shadow);
    }

    private int[] colorCodes() {
        int[] colorCode = new int[32];

        for (int i = 0; i < 32; ++i) {
            int j = (i >> 3 & 1) * 85;
            int k = (i >> 2 & 1) * 170 + j;
            int l = (i >> 1 & 1) * 170 + j;
            int i1 = (i & 1) * 170 + j;

            if (i == 6) {
                k += 85;
            }

            if (i >= 16) {
                k /= 4;
                l /= 4;
                i1 /= 4;
            }

            colorCode[i] = (k & 255) << 16 | (l & 255) << 8 | i1 & 255;
        }

        return colorCode;
    }

}
