package com.example.web.api.domain.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.web.api.domain.entity.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    public Product findByPid(String Id);
    public Product deleteByPid(String Id);
    public boolean existsByPid(String Id);
}