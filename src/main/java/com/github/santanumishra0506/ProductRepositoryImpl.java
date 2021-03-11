package com.github.santanumishra0506;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpHost;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;

@Component
public class ProductRepositoryImpl implements ProductRepository {
	
	
	 ObjectMapper objectMapper = new ObjectMapper();
	
	RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost",9200,"http")));

	@Override
	public List<Product> findAllProductDetailsFromElastic() {
		// TODO Auto-generated method stub
		
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices("posts");
		
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		
		searchRequest.source(searchSourceBuilder);
		
		List<Product> products = new ArrayList<Product>();
		
		SearchResponse searchResponse = null;
		
		try {
			searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			if(searchResponse.getHits().getTotalHits().value>0)
			{
				SearchHit[] searchHit = searchResponse.getHits().getHits();
				for(SearchHit hit: searchHit)
				{
					Map<String, Object> map = hit.getSourceAsMap();
					products.add(objectMapper.convertValue(map, Product.class));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return products;
	}

	@Override
	public List<Product> findAllProductInfoFromElastic(String productName) {
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices("posts");
		
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("name.keyword", productName)));
		
		searchRequest.source(searchSourceBuilder);
		
		List<Product> products = new ArrayList<Product>();
		
		SearchResponse searchResponse = null;
		
		try {
			searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			if(searchResponse.getHits().getTotalHits().value>0)
			{
				SearchHit[] searchHit = searchResponse.getHits().getHits();
				for(SearchHit hit: searchHit)
				{
					Map<String, Object> map = hit.getSourceAsMap();
					products.add(objectMapper.convertValue(map, Product.class));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return products;
	}

	@Override
	public List<Product> searchProductsinPosts(String keyWord) {
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices("posts");
		
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.multiMatchQuery(keyWord, "name","in_stock")));
		
		searchRequest.source(searchSourceBuilder);
		
		List<Product> products = new ArrayList<Product>();
		
		SearchResponse searchResponse = null;
		
		try {
			searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			if(searchResponse.getHits().getTotalHits().value>0)
			{
				SearchHit[] searchHit = searchResponse.getHits().getHits();
				for(SearchHit hit: searchHit)
				{
					Map<String, Object> map = hit.getSourceAsMap();
					products.add(objectMapper.convertValue(map, Product.class));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return products;
	}

}
