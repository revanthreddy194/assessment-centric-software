package com.centric.assignment.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.centric.assignment.model.Product;
import com.centric.assignment.model.ProductResponse;
import com.centric.assignment.repository.ProductEntity;
import com.centric.assignment.repository.ProductRepository;

@SpringBootTest
public class ProductServiceTest {

	@Autowired
    ProductService productService; 
	
	@MockBean
	ProductRepository productRepository;
 
    @Test
    @DisplayName("Test Insert Product")
    public void testInsertProduct() throws Exception {
    	// Setup our mock repository
    	List<String> tags = Arrays.asList("red", "shirt", "slim fit");    	
    	Product product = new Product("Red Shirt", 
    			"Red hugo boss shirt",
    			"Hugo Boss",
    			tags,
    			"apparel");
    	ProductEntity productResponse = new ProductEntity("357cd2c8-6f69-4bea-a6fa-86e40af0d867","Red Shirt", 
    			"Red hugo boss shirt",
    			"Hugo Boss",
    			"[\"red\",\"shirt\",\"slim fit\"]\r\n",
    			"apparel","2021-08-25T01:02:03Z");        
    	doReturn(productResponse).when(productRepository).save(any());
    	
    	// Execute the service call
    	ProductResponse response =  productService.insertProduct(product);
    			
    	// Assert the response
    	Assertions.assertNotNull(response, "The returned response should not be null");
        Assertions.assertEquals(true, !response.getId().isEmpty(), "The ID is not empty");
    }
    
    @Test
    @DisplayName("Test Get Products")
    public void testGetProducts() throws Exception {
    	// Setup our mock repository
    	ProductEntity productResponse = new ProductEntity("357cd2c8-6f69-4bea-a6fa-86e40af0d867","Red Shirt", 
    			"Red hugo boss shirt",
    			"Hugo Boss",
    			"[\"red\",\"shirt\",\"slim fit\"]\r\n",
    			"apparel","2021-08-25T01:02:03Z");        

    	Page<ProductEntity> pagedTasks = new PageImpl(Arrays.asList(productResponse));
    	
    	doReturn(pagedTasks).when(productRepository).findByCategory(any(), any());

    	// Execute the service call
    	String[] str = {"createdAt","desc"};
    	List<ProductResponse> response = productService.getProduct(0, 1, str, "apparel");
    	
    	// Assert the response
    	Assertions.assertNotNull(response, "The returned response should not be null");
    	Assertions.assertEquals(1, response.size(), "The returned response size is not expected");
    }
}