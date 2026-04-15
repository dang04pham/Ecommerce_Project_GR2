package com.ecommerce.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private int productId;
    private String productName;
    private String productDescription;
    private int stock;
    private int price;
    private int discount;
    private int storeId;
}
