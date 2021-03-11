package com.github.santanumishra0506;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository {

	List<Product> findAllProductDetailsFromElastic();

	List<Product> findAllProductInfoFromElastic(String productName);

	List<Product> searchProductsinPosts(String keyWord);

}
