package ru.ensemplix.shop.parser;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.RegistryNamespaced;
import ru.ensemplix.shop.SignShop;
import ru.ensemplix.shop.*;

import static ru.ensemplix.shop.ShopPrice.Result.SUCCESS;

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

        ShopPrice price = ShopPriceParser.parse(priceLine);

        if(price.getResult() != SUCCESS) {
            return null;
        }

        ItemStack stack = new ItemStack(item, quantity, shopStack.getData());
        return new SignShop(ownerNameLine, quantity, stack, price.getBuyPrice(), price.getSellPrice());
    }

    private int getInt(String text) {
        try {
            return Integer.parseInt(text);
        } catch(NumberFormatException e) {
            return 0;
        }
    }

}
