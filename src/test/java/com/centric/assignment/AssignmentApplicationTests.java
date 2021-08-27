package com.centric.assignment;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.centric.assignment.controller.ProductController;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class AssignmentApplicationTests {

	@Autowired
    public ProductController productController;
	
	@Test
	void contextLoads() {
		Assertions.assertThat(productController).isNot(null);
	}
}