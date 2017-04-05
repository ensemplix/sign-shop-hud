package ru.ensemplix.parser;

import ru.ensemplix.SignShop;

public class EnsemplixSignShopParser implements SignShopParser {

    @Override
    public SignShop parse(String[] lines) {
        if(lines[0] != null) {
            return new SignShop(lines[0], 0, null, 0 ,0);
        }

        return null;
    }

}
