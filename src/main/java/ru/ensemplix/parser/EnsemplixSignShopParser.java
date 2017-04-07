package ru.ensemplix.parser;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import ru.ensemplix.SignShop;

public class EnsemplixSignShopParser implements SignShopParser {

    @Override
    public SignShop parse(String[] lines) {
        if(lines[0] != null) {
            return new SignShop(lines[0], 1, new ItemStack(Items.diamond, 1), 15 ,25);
        }

        return null;
    }

}
