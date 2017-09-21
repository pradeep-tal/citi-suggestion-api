package com.suggestioncities;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.suggestioncities.model.Response;
import com.suggestioncities.service.CitiSuggestionService;
import com.suggestioncities.service.CitiSuggestionServiceImpl;

@RestController
public class CitiSuggestionController {

	@RequestMapping("/suggest_cities")
	public Object getMatchedCities(@RequestParam("start") String start, @RequestParam("atmost") int atmost) {

		System.out.println("start " + start);

		if (start.length() == 0) {
			return new Response("Error", "Empty keyword");
		} else if (atmost <= 0) {
			return new Response("Error", "Invalid value for atmost");
		} else {

			CitiSuggestionService citiSuggestionService = new CitiSuggestionServiceImpl();

			try {
				return citiSuggestionService.getMatchedCities(start, atmost);
			} catch (Exception e) {
				e.printStackTrace();
				return new Response("Error", e.getMessage());
			}
		}
	}

	@RequestMapping("/load_cities")
	public String refreshCities() {
		CitiSuggestionService citiSuggestionService = new CitiSuggestionServiceImpl();
		try {
			citiSuggestionService.loadCities();

			return new Response("Success", "Loading Cities completed").toString();

		} catch (Exception e) {

			return new Response("Error", "Loading Cities failed ").toString();
		}

	}

}
