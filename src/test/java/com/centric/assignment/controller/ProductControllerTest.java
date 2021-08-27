package com.centric.assignment.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.centric.assignment.model.Product;
import com.centric.assignment.model.ProductResponse;
import com.centric.assignment.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

	@MockBean
    ProductService productService; 
	
	@Autowired
    private ObjectMapper objectMapper;
    
	@Autowired
    MockMvc mockMvc;
 
    @Test
    @DisplayName("POST /v1/products")
    public void testCreateProducts() throws Exception {
    	List<String> tags = Arrays.asList("red", "shirt", "slim fit");
    	Product product = new Product("Red Shirt", 
    			"Red hugo boss shirt",
    			"Hugo Boss",
    			tags,
    			"apparel");
    	ProductResponse productResponse = new ProductResponse("357cd2c8-6f69-4bea-a6fa-86e40af0d867","Red Shirt", 
    			"Red hugo boss shirt",
    			"Hugo Boss",
    			tags,
    			"apparel","2021-08-25T01:02:03Z");
 
        Mockito.when(productService.insertProduct(product)).thenReturn(productResponse);
 
        // Execute the POST request
        mockMvc.perform(MockMvcRequestBuilders
        		.post("/v1/products")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(objectMapper.writeValueAsString(product)))
        		
        		// Validate the response code and content type
        		.andExpect(status().isCreated())
        		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
        		
        		// Validate the returned fields
                .andExpect(jsonPath("$.id",Matchers.is("357cd2c8-6f69-4bea-a6fa-86e40af0d867")))
        		.andExpect(jsonPath("$.name",Matchers.is("Red Shirt")))
                .andExpect(jsonPath("$.description",Matchers.is("Red hugo boss shirt")))
                .andExpect(jsonPath("$.brand",Matchers.is("Hugo Boss")))
                .andExpect(jsonPath("$.tags",Matchers.is(tags)))
                .andExpect(jsonPath("$.category",Matchers.is("apparel")))
        		.andExpect(jsonPath("$.created_at",Matchers.is("2021-08-25T01:02:03Z"))); 		
    }
    
    @Test
    @DisplayName("GET /v1/products")
    public void testGetProducts() throws Exception {
    	List<String> tags = Arrays.asList("red", "shirt", "slim fit");
    	ProductResponse productOneResponse = new ProductResponse("357cd2c8-6f69-4bea-a6fa-86e40af0d867","Red Shirt", 
    			"Red hugo boss shirt",
    			"Hugo Boss",
    			tags,
    			"apparel","2021-08-25T01:02:03Z");
    	
    	ProductResponse productTwoResponse = new ProductResponse("345cd2c8-6f69-5bea-a7fa-12e64af0d651","Black Shirt", 
    			"Black hugo boss shirt",
    			"Hugo Boss",
    			tags,
    			"apparel","2021-08-24T01:02:03Z");
    	
        Mockito.when(productService
        		.getProduct(Mockito.anyInt(), Mockito.anyInt(), 
        				Mockito.any(String[].class), Mockito.anyString())).thenReturn(Arrays.asList(productOneResponse,productTwoResponse));
        	
        // Execute the GET request
        mockMvc.perform(MockMvcRequestBuilders
        		.get("/v1/products")
        		.param("category", "apparel")
        		.contentType(MediaType.APPLICATION_JSON))
        
        		// Validate the response code and content type
        		.andExpect(status().isOk())
        		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
        		
        		// Validate the returned fields
        		.andExpect(jsonPath("$[0].name",Matchers.is("Red Shirt")))
                .andExpect(jsonPath("$[0].description",Matchers.is("Red hugo boss shirt")))
                .andExpect(jsonPath("$[0].brand",Matchers.is("Hugo Boss")))
                .andExpect(jsonPath("$[0].tags",Matchers.is(tags)))
                .andExpect(jsonPath("$[0].category",Matchers.is("apparel")))
        		.andExpect(jsonPath("$[0].created_at",Matchers.is("2021-08-25T01:02:03Z"))); 		
    }
}