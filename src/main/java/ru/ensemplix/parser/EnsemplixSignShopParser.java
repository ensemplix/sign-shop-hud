package ru.ensemplix.parser;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.RegistryNamespaced;
import ru.ensemplix.SignShop;
import ru.ensemplix.shop.ShopItem;
import ru.ensemplix.shop.ShopItemRegistry;
import ru.ensemplix.shop.ShopItemStack;

public class EnsemplixSignShopParser implements SignShopParser {

    private final ShopItemRegistry shopItemRegistry;
    private final RegistryNamespaced itemRegistry;

    public EnsemplixSignShopParser(ShopItemRegistry shopItemRegistry, RegistryNamespaced itemRegistry) {
        this.shopItemRegistry = shopItemRegistry;
        this.itemRegistry = itemRegistry;
    }

    @Override
    public SignShop parse(String[] lines) {
        String ownerNameLine = lines[0];
        String quantityLine = lines[1];
        String priceLine = lines[2];
        String itemNameLine = lines[3];

        if(ownerNameLine.isEmpty() || quantityLine.isEmpty() || priceLine.isEmpty() || itemNameLine.isEmpty()) {
            return null;
        }

        ShopItem shopItem = shopItemRegistry.getItemByName(itemNameLine);

        if(shopItem == null) {
            return null;
        }

        ShopItemStack shopStack = shopItem.getItemStack();
        Item item = (Item) itemRegistry.getObject(shopStack.getId());

        if(item == null) {
            return null;
        }

        int quantity = getInt(quantityLine);

        if(quantity == 0) {
            return null;
        }

        boolean sellProvided = false, buyProvided = false;
        int sellPrice = 0, buyPrice = 0;

        if(priceLine.contains(":")) {
            String[] args = priceLine.split(":");

            if(args.length == 1) {
                return null;
            }

            // Покупка товара должна быть слева.
            if(!args[0].startsWith("К")) {
                return null;
            }

            // Продажа товара должна быть справа.
            if(!args[1].startsWith("П")) {
                return null;
            }

            sellPrice = getPrice(args[0]);
            buyPrice = getPrice(args[1]);
            buyProvided = true;
            sellProvided = true;
        } else if(priceLine.startsWith("К")) {
            sellPrice = getPrice(priceLine);
            sellProvided = true;
        } else if(priceLine.startsWith("П")) {
            buyPrice = getPrice(priceLine);
            buyProvided = true;
        }

        if((sellPrice <= 0 && sellProvided) || (buyPrice <= 0 && buyProvided)) {
            return null;
        }

        ItemStack stack = new ItemStack(item, quantity, shopStack.getData());
        return new SignShop(ownerNameLine, quantity, stack, buyPrice, sellPrice);
    }

    private int getInt(String text) {
        try {
            return Integer.parseInt(text);
        } catch(NumberFormatException e) {
            return 0;
        }
    }

    private int getPrice(String price) {
        return getInt(price.substring(1).trim());
    }

}
