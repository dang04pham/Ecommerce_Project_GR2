package com.ecommerce.application.service;

import com.ecommerce.application.dto.ProductDto;
import com.ecommerce.application.model.Product;

public interface ProductService {
    Product addProduct(ProductDto productDto);
}
