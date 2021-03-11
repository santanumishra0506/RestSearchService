package com.github.santanumishra0506;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value ="/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;

	@GetMapping (value="/allProduct", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Product> getAllProduct(){
		return productService.getAllProductInfo();
	}
	
	@GetMapping (value="/allProduct/{productName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Product> getProductByName(@PathVariable String productName){
		return productService.getProductsByName(productName);
	}
	
	@GetMapping (value="/searchproduct/{keyWord}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Product> searchProduct(@PathVariable String keyWord){
		return productService.searchProducts(keyWord);
	}
}
