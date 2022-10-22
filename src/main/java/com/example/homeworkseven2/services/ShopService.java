package com.example.homeworkseven2.services;

import com.example.homeworkseven2.models.Shop;

import java.util.List;

public interface ShopService {
    void addShop(String name);

    void removeShopById(Long id);

    Shop getShopById(Long id);

    List<Shop> getAllShops();

    void updateShopNameById(Long id, String name);
}


