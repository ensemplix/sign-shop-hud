package ru.ensemplix.parser;

import cpw.mods.fml.common.Loader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import ru.ensemplix.SignShop;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class LegacyEnsemplixSignShopParser implements SignShopParser {

    private static final IResourceManager resourceManager = Minecraft.getMinecraft().getResourceManager();
    private final Map<String, ItemInfo> items = new HashMap<>();

    public LegacyEnsemplixSignShopParser() {
        ResourceLocation resource = new ResourceLocation("signshophud", "legacy_items.csv");
        boolean hitech = Loader.isModLoaded("IC2");
        boolean magic = Loader.isModLoaded("Thaumcraft");
        String line;

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(resourceManager.getResource(resource).getInputStream(), "UTF-8"))) {
            while ((line = reader.readLine()) != null) {
                String[] args = line.replaceAll("\"", "").split(",");
                String name = args[0];
                int id = Integer.parseInt(args[1]);
                int data = Integer.parseInt(args[2]);
                String server = args[4];

                if(server.equalsIgnoreCase("hitech") && !hitech) {
                    continue;
                }

                if(server.equalsIgnoreCase("magic") && !magic) {
                    continue;
                }

                items.put(name, new ItemInfo(id, data));
            }
        } catch(Exception e) {
            throw new RuntimeException("Failed to parse legacy items", e);
        }
    }

    @Override
    public SignShop parse(String[] lines) {
        if(lines[0].isEmpty() || lines[1].isEmpty() || lines[2].isEmpty() || lines[3].isEmpty()) {
            return null;
        }

        if(!items.containsKey(lines[3])) {
            return null;
        }

        String owner = lines[0];

        int amount;
        int buy;
        int sell = 0;

        try {
            amount = Integer.parseInt(lines[1]);
        } catch(NumberFormatException e) {
            return null;
        }

        if(lines[2].contains(":")) {
           String[] args = lines[2].split(":");

            try {
                buy = Integer.parseInt(args[0]);
            } catch(NumberFormatException e) {
                return null;
            }

            try {
                sell = Integer.parseInt(args[1]);
            } catch(NumberFormatException e) {
                return null;
            }
        } else {
            try {
                buy = Integer.parseInt(lines[2]);
            } catch(NumberFormatException e) {
                return null;
            }
        }

        ItemInfo itemInfo = items.get(lines[3]);
        Item item = Item.getItemById(itemInfo.id);

        if(item == null) {
            return null;
        }

        return new SignShop(owner, amount, new ItemStack(item, amount, itemInfo.data), buy, sell);
    }

    private static class ItemInfo {
        private final int id;
        private final int data;

        public ItemInfo(int id, int data) {
            this.id = id;
            this.data = data;
        }
    }

}
