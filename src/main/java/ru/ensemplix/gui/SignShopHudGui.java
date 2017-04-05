package ru.ensemplix.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import ru.ensemplix.SignShop;

public class SignShopHudGui extends GuiScreen {

    private static final Minecraft minecraft = Minecraft.getMinecraft();
    private final SignShop shop;
    private final int width;
    private final int height;

    public SignShopHudGui(SignShop shop) {
        this.shop = shop;
        ScaledResolution scaled = new ScaledResolution(minecraft, minecraft.displayWidth, minecraft.displayHeight);
        this.width = scaled.getScaledWidth();
        this.height = scaled.getScaledHeight();

        drawCenteredString(minecraft.fontRenderer, shop.getOwner(), width / 2, (height / 2) - 4, Integer.parseInt("FFAA00", 16));
    }

}
