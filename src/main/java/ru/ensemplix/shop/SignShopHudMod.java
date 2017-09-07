package ru.ensemplix.shop;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import ru.ensemplix.shop.render.SignShopHudRender;
import ru.ensemplix.shop.importer.JsonShopItemImporter;
import ru.ensemplix.shop.importer.ShopItemImporter;
import ru.ensemplix.shop.importer.ZipShopItemImporter;
import ru.ensemplix.shop.render.text.TextRenderer;

import java.nio.file.Path;

@Mod(modid = "signshophud", name = "SignShopHud", version = "1.2", acceptedMinecraftVersions = "[1.7.10]", acceptableRemoteVersions = "*")
public class SignShopHudMod {

    public static TextRenderer textRenderer;
    private Path modPath;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        modPath = event.getSourceFile().toPath();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        textRenderer = new TextRenderer(20);

        ShopItemImporter importer = new ZipShopItemImporter(new JsonShopItemImporter(), "assets/signshophud/data");
        ShopItemRegistry shopItemRegistry = new ShopItemRegistry();

        for(ShopItem item : importer.importFromFile(modPath)) {
            shopItemRegistry.addItem(item);
        }

        MinecraftForge.EVENT_BUS.register(new SignShopHudRender(shopItemRegistry));
    }

}
