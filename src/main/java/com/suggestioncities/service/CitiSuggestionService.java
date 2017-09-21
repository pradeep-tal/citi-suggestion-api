package com.suggestioncities.service;

public interface CitiSuggestionService {

	String getMatchedCities(String start, int maxSugg) throws Exception;
	
	void loadCities() throws Exception;

}
