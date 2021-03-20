package com.github.santanumishra0506;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpHost;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.ClearScrollResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.SuggestionBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
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
		
		return products;
	}

	@Override
	public List<Product> searchProductsinPostswithPagination(String keyWord, Integer from, Integer size) {
		// TODO Auto-generated method stub
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices("posts");
		
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder
			.from(from)
			.size(size)
			.query(QueryBuilders.boolQuery().must(QueryBuilders.multiMatchQuery(keyWord, "name","in_stock")));
		
		searchRequest.source(searchSourceBuilder);
		
		List<Product> products = new ArrayList<Product>();
		
		SearchResponse searchResponse = null;
		
		try {
			searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		//	String scrollId =  searchResponse.getScrollId();
			
			if(searchResponse.getHits().getTotalHits().value>0)
			{
				
				
//				final SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
//				scrollRequest.scroll(TimeValue.timeValueSeconds(600));
//			    searchResponse = client.scroll(scrollRequest,RequestOptions.DEFAULT);
//				
				
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
		
//		ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
//
//		clearScrollRequest.addScrollId(scrollId);
//
//		client.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
	}

	@Override
	public List<String> retrieveSuggestions(String term) {
		
		
		List<String> suggestions = new ArrayList<String>();
		
		SearchRequest searchRequest = new SearchRequest("productsuggestion");
		
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		
		SuggestionBuilder productSuggestionBuilder = SuggestBuilders.completionSuggestion("suggest").prefix(term);
		
		SuggestBuilder suggestBuilder = new SuggestBuilder();
		
		suggestBuilder.addSuggestion("product-suggest", productSuggestionBuilder);
		searchSourceBuilder.suggest(suggestBuilder);
		
		searchRequest.source(searchSourceBuilder);
		
		SearchResponse searchResponse = null;
		
		try {
			searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			Suggest suggest = searchResponse.getSuggest();
			
			CompletionSuggestion entries = suggest.getSuggestion("product-suggest");
			
			for(CompletionSuggestion.Entry entry:entries)
			{
				for(CompletionSuggestion.Entry.Option option:entry.getOptions())
				{
					suggestions.add(option.getText().string());
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		return suggestions;
	}

}
