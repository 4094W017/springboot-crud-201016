package com.example.web.api.controller;

import com.example.web.api.core.exception.NotFoundException;
import com.example.web.api.domain.entity.Product;
import com.example.web.api.domain.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/product", produces=MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts(@RequestParam(value = "u", defaultValue = "") String UserId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "max-age=30, public");

        List<Product> listProduct = productService.queryProduct();

        return new ResponseEntity<List<Product>>(listProduct, headers, HttpStatus.OK);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<Product> getProductById(@PathVariable String Id, @RequestParam(value = "u", defaultValue = "") String UserId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "max-age=30, public");

        try {
            Product product = productService.getProductByProductId(Id);

            if (product == null) {
                throw new Exception("productId not found");
            }

            return new ResponseEntity<Product>(product, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping
    public ResponseEntity<Product> postProductById(@RequestBody Product postData, @RequestParam(value = "u", defaultValue = "") String UserId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "max-age=30, public");

        try {
            Product product = productService.createProduct(postData);

            return new ResponseEntity<Product>(product, headers, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{Id}")
    public ResponseEntity putProductById(@PathVariable String Id, @RequestBody Product putData, @RequestParam(value = "u", defaultValue = "") String UserId) {
        HttpHeaders headers = new HttpHeaders();

        String rsCode = productService.updateProduct(Id, putData);

        if (rsCode == "0") {
            return new ResponseEntity(headers, HttpStatus.NO_CONTENT);
        } else {
            logger.error("rsCode: " + rsCode);
            return new ResponseEntity(headers, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<Product> deleteProductById(@PathVariable String Id, @RequestParam(value = "u", defaultValue = "") String UserId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "max-age=30, public");

        String rsCode = productService.deleteProduct(Id) ;
        if (rsCode == "0") {
            return new ResponseEntity<Product>(headers, HttpStatus.NO_CONTENT);
        } else {
            logger.error("rsCode: " + rsCode);
            return new ResponseEntity<Product>(headers, HttpStatus.NOT_FOUND);
        }
    }



    @RequestMapping("/trace")
    public String hello() {
        return "OK";
    }

}
