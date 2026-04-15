package com.ecommerce.application.service.implementation;

import com.ecommerce.application.dto.ProductDto;
import com.ecommerce.application.model.Product;
import com.ecommerce.application.repository.ProductRepository;
import com.ecommerce.application.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(ProductDto productDto) {
        Product product = new Product();

        product.setProductId(productDto.getProductId());
        product.setProductName(productDto.getProductName());
        product.setProductDescription(productDto.getProductDescription());
        product.setStock(productDto.getStock());
        product.setPrice(productDto.getPrice());
        product.setDiscount(productDto.getDiscount());

        return productRepository.save(product);

    }
}
