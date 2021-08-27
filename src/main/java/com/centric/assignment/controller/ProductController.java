package com.centric.assignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.centric.assignment.model.Product;
import com.centric.assignment.model.ProductResponse;
import com.centric.assignment.service.ProductService;

@RestController
@RequestMapping(value = "/v1/products" )
public class ProductController {

    @Autowired
    public ProductService productService;
    
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ProductResponse insertProduct(@RequestBody Product product){
        return productService.insertProduct(product);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<ProductResponse> getProduct(
    	    @RequestParam(defaultValue = "0") int page,
    	    @RequestParam(defaultValue = "2") int size,
    	    @RequestParam(defaultValue = "createdAt,desc") String[] sort,
    	    @RequestParam String category){
    	
    	//default values for page/size/sorting fields are defined to fetch products
        return productService.getProduct(page, size, sort, category);
    }
}
