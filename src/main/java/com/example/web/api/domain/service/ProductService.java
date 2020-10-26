package com.example.web.api.domain.service;

import com.example.web.api.domain.entity.Product;
import com.example.web.api.domain.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> queryProduct() {
        return productRepository.findAll();
    }

    public Product getProductByProductId(String Id) {
        return productRepository.findByPid(Id);
    }

    public Product createProduct(Product insertProductData) {
        return productRepository.save(insertProductData);
    }

    public String updateProduct(String Id, Product updateProductData) {
        try {
            Product product = productRepository.findByPid(Id);
            if (product == null) {
                throw new Exception("-1");
            }
            product.setName(updateProductData.getName());
            productRepository.save(product);
            return "0";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String deleteProduct(String Id) {
        try {
            if (!productRepository.existsByPid(Id)) {
                throw new Exception("-1");
            }
            productRepository.deleteByPid(Id);
            return "0";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
