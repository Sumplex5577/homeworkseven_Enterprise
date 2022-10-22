package com.example.homeworkseven2.dtos;

import com.example.homeworkseven2.models.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShopDto {
    private Long id;
    private String name;
    private List<Product> products;

}


