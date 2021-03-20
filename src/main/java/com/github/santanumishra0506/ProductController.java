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

	//All Products search
	@GetMapping (value="/allproduct", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Product> getAllProduct(){
		return productService.getAllProductInfo();
	}
	
	//term search
	@GetMapping (value="/fieldsearch/q={productName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Product> getProductByName(@PathVariable String productName){
		return productService.getProductsByName(productName);
	}

	//free text search with pagination
	@GetMapping (value="/search/q={keyWord}/from={from}/size={size}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Product> searchWithPagination(@PathVariable String keyWord,@PathVariable Integer from,@PathVariable Integer size){
		return productService.searchProductsWithPagination(keyWord,from,size);
	}
	
	//auto suggestion
	@GetMapping (value="/suggestions/q={term}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getSuggestions(@PathVariable String term){
		return productService.getSuggestionTerms(term);
	}
}
