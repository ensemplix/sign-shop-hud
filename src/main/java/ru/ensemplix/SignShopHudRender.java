package ru.ensemplix;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import ru.ensemplix.gui.SignShopHudGui;
import ru.ensemplix.parser.EnsemplixSignShopParser;
import ru.ensemplix.parser.SignShopParser;

public class SignShopHudRender {

    private static final SignShopParser parser = new EnsemplixSignShopParser();
    private static final Minecraft minecraft = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        if(event.isCancelable()) {
            return;
        }

        if(event.type != ElementType.EXPERIENCE) {
            return;
        }

        EntityClientPlayerMP player = minecraft.thePlayer;
        WorldClient world = minecraft.theWorld;

        Vec3 position = player.getPosition(0);
        Vec3 look = player.getLook(0);
        Vec3 distance = position.addVector(look.xCoord * 2, look.yCoord * 2, look.zCoord * 2);

        MovingObjectPosition mop = world.rayTraceBlocks(position, distance, true);

        if(mop == null) {
            return;
        }

        TileEntity tileEntity = world.getTileEntity(mop.blockX, mop.blockY, mop.blockZ);

        if(tileEntity == null) {
            return;
        }

        if(!(tileEntity instanceof TileEntitySign)) {
            return;
        }

        TileEntitySign tileEntitySign = (TileEntitySign) tileEntity;
        SignShop shop = parser.parse(tileEntitySign.signText);

        if(shop == null) {
            return;
        }

        new SignShopHudGui(shop);
    }

}
