package ru.ensemplix;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = "signshophud", name = "SignShopHud", version = "1.0", acceptedMinecraftVersions = "[1.7.10]", acceptableRemoteVersions = "*")
public class SignShopHud {

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new SignShopHudRender());
    }

}
