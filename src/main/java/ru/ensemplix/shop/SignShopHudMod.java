package ru.ensemplix.shop;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import ru.ensemplix.shop.importer.JsonShopItemImporter;
import ru.ensemplix.shop.importer.ShopItemImporter;
import ru.ensemplix.shop.importer.ZipShopItemImporter;
import ru.ensemplix.shop.render.SignShopHudRender;

import java.nio.file.Path;

@Mod(modid = "signshophud", name = "SignShopHud", version = "1.0.1", acceptedMinecraftVersions = "[1.7.10]", acceptableRemoteVersions = "*")
public class SignShopHudMod {

    private Path modPath;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        modPath = event.getSourceFile().toPath();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        ShopItemImporter importer = new ZipShopItemImporter(new JsonShopItemImporter(), "assets/signshophud/data");
        ShopItemRegistry shopItemRegistry = new ShopItemRegistry();

        for(ShopItem item : importer.importFromFile(modPath)) {
            shopItemRegistry.addItem(item);
        }

        MinecraftForge.EVENT_BUS.register(new SignShopHudRender(shopItemRegistry));
    }

}
