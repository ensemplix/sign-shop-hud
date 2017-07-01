package ru.ensemplix.shop.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.ensemplix.shop.ShopItemRegistry;
import ru.ensemplix.shop.SignShop;
import ru.ensemplix.shop.parser.EnsemplixSignShopParser;
import ru.ensemplix.shop.parser.SignShopParser;
import ru.ensemplix.shop.render.gui.SignShopHudGuiRender;

import static net.minecraft.util.math.RayTraceResult.Type.BLOCK;

public class SignShopHudRender {

    private static final Minecraft minecraft = Minecraft.getMinecraft();
    private final SignShopParser parser;

    public SignShopHudRender(ShopItemRegistry shopItemRegistry) {
        parser = new EnsemplixSignShopParser(shopItemRegistry, Item.REGISTRY);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        if(event.isCanceled() || event.getType() != ElementType.EXPERIENCE) {
            return;
        }

        EntityPlayerSP player = minecraft.thePlayer;
        WorldClient world = minecraft.theWorld;

        RayTraceResult rayTraceResult = player.rayTrace(3, 0);

        if(rayTraceResult == null || rayTraceResult.typeOfHit != BLOCK) {
            return;
        }

        TileEntity tileEntity = world.getTileEntity(rayTraceResult.getBlockPos());

        if(tileEntity == null) {
            return;
        }

        if(!(tileEntity instanceof TileEntitySign)) {
            return;
        }

        TileEntitySign tileEntitySign = (TileEntitySign) tileEntity;
        ITextComponent[] components = tileEntitySign.signText;
        int length = components.length;
        String[] text = new String[length];

        for(int i = 0; i < length; i++) {
            text[i] = components[i].getUnformattedText();
        }

        SignShop shop = parser.parse(text);

        if(shop == null) {
            return;
        }

        new SignShopHudGuiRender(shop);
    }

}
