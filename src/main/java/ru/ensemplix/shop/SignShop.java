package ru.ensemplix.shop;

import net.minecraft.item.ItemStack;

public class SignShop {

    private final String owner;
    private final int quantity;
    private final ItemStack stack;
    private final int buyPrice;
    private final int sellPrice;

    public SignShop(String owner, int quantity, ItemStack stack, int buyPrice, int sellPrice) {
        this.owner = owner;
        this.quantity = quantity;
        this.stack = stack;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }

    public String getOwner() {
        return owner;
    }

    public int getQuantity() {
        return quantity;
    }

    public ItemStack getStack() {
        return stack;
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public int getSellPrice() {
        return sellPrice;
    }

}
