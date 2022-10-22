package com.example.homeworkseven2.converters;

import com.example.homeworkseven2.dtos.ShopDto;
import com.example.homeworkseven2.models.Shop;

public class ShopConverter {
    public static ShopDto convertShopToShopDto(Shop shop) {
        ShopDto shopDto = new ShopDto();
        shopDto.setId(shop.getId());
        shopDto.setName(shop.getName());
        shopDto.setProducts(shop.getProducts());
        return shopDto;
    }

    public static Shop convertShopDtoToShop(ShopDto shopDto) {
        Shop shop = new Shop();
        shop.setId(shopDto.getId());
        shop.setName(shopDto.getName());
        shop.setProducts(shopDto.getProducts());
        return shop;
    }
}