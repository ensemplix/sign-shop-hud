package ru.ensemplix.shop.parser;

import net.minecraft.init.Bootstrap;
import net.minecraft.item.Item;
import org.junit.Before;
import org.junit.Test;
import ru.ensemplix.shop.ShopItem;
import ru.ensemplix.shop.ShopItemRegistry;
import ru.ensemplix.shop.ShopItemStack;
import ru.ensemplix.shop.SignShop;

import static org.junit.Assert.*;

public class EnsemplixSignShopParserTest {

    private final ShopItemRegistry shopItemRegistry = new ShopItemRegistry();
    private final EnsemplixSignShopParser parser = new EnsemplixSignShopParser(shopItemRegistry, Item.REGISTRY);

    @Before
    public void setUp() {
        Bootstrap.register();

        ShopItemStack shopStack = new ShopItemStack("minecraft:stone", 0, null);
        ShopItem shopItem = new ShopItem("КАМЕНЬ", shopStack);
        shopItemRegistry.addItem(shopItem);
    }

    @Test
    public void testParseNoQuantity() {
        assertNull(parser.parse(new String[] {"ensiriuswOw", "", "К60:П15", "КАМЕНЬ"}));
    }

    @Test
    public void testParseNotIntQuantity() {
        assertNull(parser.parse(new String[] {"ensiriuswOw", "koala", "К60:П15", "КАМЕНЬ"}));
    }

    @Test
    public void testParseNoPrice() {
        assertNull(parser.parse(new String[] {"ensiriuswOw", "64", "", "КАМЕНЬ"}));
    }

    @Test
    public void testParseDualPriceNoSecondPrice() {
        assertNull(parser.parse(new String[] {"ensiriuswOw", "64", "К100:", "КАМЕНЬ"}));
    }

    @Test
    public void testParseIncorrectPrice() {
        assertNull(parser.parse(new String[] {"ensiriuswOw", "64", "Кит", "КАМЕНЬ"}));
    }

    @Test
    public void testParseDoublePrice() {
        assertNull(parser.parse(new String[] {"ensiriuswOw", "64", "К150:П ит", "КАМЕНЬ"}));
    }

    @Test
    public void testParseDualPriceBuyLeft() {
        assertNull(parser.parse(new String[] {"ensiriuswOw", "64", "П100:К150", "КАМЕНЬ"}));
    }

    @Test
    public void testParseDualPriceSellRight() {
        assertNull(parser.parse(new String[] {"ensiriuswOw", "64", "К100:К150", "КАМЕНЬ"}));
    }

    @Test
    public void testParseSinglePriceSuccess() {
        SignShop signShop = parser.parse(new String[] {"ensiriuswOw", "64", "П60", "КАМЕНЬ"});

        assertNotNull(signShop);
        assertEquals("ensiriuswOw", signShop.getOwner());
        assertEquals(64, signShop.getQuantity());
        assertEquals(60, signShop.getSellPrice());
        assertEquals(-1, signShop.getBuyPrice());
    }

    @Test
    public void testParseDoublePriceSuccess() {
        SignShop signShop = parser.parse(new String[] {"ensiriuswOw", "64", "К60:П15", "КАМЕНЬ"});

        assertNotNull(signShop);
        assertEquals("ensiriuswOw", signShop.getOwner());
        assertEquals(64, signShop.getQuantity());
        assertEquals(15, signShop.getSellPrice());
        assertEquals(60, signShop.getBuyPrice());
    }

}
