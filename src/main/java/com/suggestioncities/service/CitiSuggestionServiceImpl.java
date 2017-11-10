package com.suggestioncities.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.suggestioncities.dao.AutoSuggestionDAO;
import com.suggestioncities.exception.FileClosingException;
import com.suggestioncities.model.Response;

@Component
public class CitiSuggestionServiceImpl implements CitiSuggestionService {

	private static Logger logger = Logger.getLogger(CitiSuggestionServiceImpl.class.getName());

	@Autowired
	AutoSuggestionDAO autoSuggestionDAO;

	// @Value("${citidata.path}")
	private static final String path = "/home/pradeepr/Downloads/All_India_pincode_data_18082017.csv";

	@Override
	public String getMatchedCities(String start, int maxSugg) throws Exception {
		List<String> cities = autoSuggestionDAO.getCities(start, maxSugg);

		if (cities.size() > 0)
			return org.apache.commons.lang3.StringUtils.join(cities, System.getProperty("line.separator"));
		else
			return new Response("Success", "No Suggestions found").toString();
	}

	@Override
	public void loadCities() throws Exception {

		Set<String> uniqueCities = new HashSet<String>();

		uniqueCities = readAddressFromFile();

		autoSuggestionDAO.loadCities(uniqueCities);

	}

	private Set<String> readAddressFromFile() throws FileClosingException {

		BufferedReader reader = null;

		Set<String> uniqueCities = new HashSet<String>();
		try {

			System.out.println("path " + path);

			reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path))));

			String line = null;
			if (reader != null) {

				while ((line = reader.readLine()) != null) {

					String[] arr = line.split(",");

					if (arr.length == 10) {
						String dname = arr[4];
						String rname = arr[5];
						String circleName = arr[7];
						String taluk = arr[8];

						uniqueCities.add(dname);

						uniqueCities.add(rname);

						uniqueCities.add(circleName);

						uniqueCities.add(taluk);

					}
				}
			}

		} catch (Exception e) {
			logger.log(Level.SEVERE, "Exception while reading address from file " + path);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Exception while reading address from file " + path);
				throw new FileClosingException(e);
			}
		}
		return uniqueCities;
	}

}
