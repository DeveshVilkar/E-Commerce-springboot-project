package com.jsp.ECommerce.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.ECommerce.common.ApiResponse;
import com.jsp.ECommerce.exception.ProductNotFoundException;
import com.jsp.ECommerce.model.Category;
import com.jsp.ECommerce.model.Product;
import com.jsp.ECommerce.repository.CategoryRepository;
import com.jsp.ECommerce.service.CategoryService;
import com.jsp.ECommerce.service.ProductService;

@RestController
public class ProductController {
	@Autowired
	ProductService productService;
	@Autowired
	CategoryService categoryService;
	
	@PostMapping("/product")
	public ResponseEntity<ApiResponse> saveProduct(@RequestBody Product product) {
		Category category=categoryService.getCategoryById(product.getCategory().getId());
		if (category!=null) {
			productService.saveProduct(product);
			return new ResponseEntity<ApiResponse>(new ApiResponse(true,"product has been added"),HttpStatus.CREATED);
		}else {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false,"category not found"),HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping("/product")
	public ResponseEntity<List<Product>> getProducts(){
		List<Product> products=productService.getAllProducts();
		return new ResponseEntity<List<Product>>(products,HttpStatus.OK);
	}
	
	@DeleteMapping("/product/{id}")
	public boolean deleteProduct(@PathVariable int id) {
		return productService.deleteProduct(id);
	}
	
	@PutMapping("/product/{id}")
	public Product updateProduct(@PathVariable int id,@RequestBody Product product) throws ProductNotFoundException {
		return productService.updateCategory(id, product);
	}

}
