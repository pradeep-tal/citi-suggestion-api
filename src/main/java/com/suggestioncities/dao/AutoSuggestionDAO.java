package com.suggestioncities.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import com.pd.util.CitiUtil;

@Component
public class AutoSuggestionDAO {

	private static Logger logger = Logger.getLogger(AutoSuggestionDAO.class.getName());

	private static final String GET_MATCHED_CITIES = "select distinct cname from cities where cname like 'C_NAME%' limit ?";

	private static final String DELETE_CITIES = "delete from cities";

	private static final String INSERT_CITY = "INSERT INTO cities(cname) values ( ? )";

	public List<String> getCities(String keyword, int maxSuggestions) throws Exception {

		Connection connection = null;

		PreparedStatement pst = null;
		ResultSet resultSet = null;

		List<String> mactchedCities = new ArrayList<>();
		try {

			connection = getConnetion();

			logger.info("getCities:: Query " + GET_MATCHED_CITIES);

			String query = GET_MATCHED_CITIES.replace("C_NAME", keyword);

			pst = connection.prepareStatement(query);

			pst.setInt(1, maxSuggestions);

			resultSet = pst.executeQuery();

			while (resultSet.next()) {
				mactchedCities.add(resultSet.getString("cname"));
			}

		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Exception while fetching cities from db " + e.getMessage());
			throw new Exception(e);
		} finally {
			if (resultSet != null)
				resultSet.close();
			if (pst != null)
				pst.close();
			if (connection != null)
				connection.close();
		}

		return mactchedCities;

	}

	private Connection getConnetion() throws Exception {

		try {

			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/citidb", "root", "root123");
		} catch (SQLException e) {

			logger.severe("Exception while creating Mysql citidb connection " + e.getMessage());
			throw new SQLException(e.getMessage());
		}

	}

	public void loadCities(Set<String> uniqueCities) throws Exception {

		Connection con = null;
		
		PreparedStatement preparedStatement = null;

		CitiUtil citiUtil=new CitiUtil();
		
		logger.info("loadCities:: Start ");

		try {
			deleteCities();

			con = getConnetion();

			con.setAutoCommit(false);

			preparedStatement = con.prepareStatement(INSERT_CITY);

			for (String city : uniqueCities) {
				preparedStatement.setString(1, city);
				preparedStatement.addBatch();
			}

			preparedStatement.executeBatch();

			con.commit();

		} catch (Exception e) {

			logger.severe("Exception while creating Mysql citidb connection " + e.getMessage());

			throw new Exception(e.getMessage());
		} finally {
			if (preparedStatement != null)
				preparedStatement.close();
			if (con != null)
				con.close();
		}

		logger.info("loadCities:: Complete ");

	}

	private void deleteCities() throws Exception {
		Connection con = null;

		PreparedStatement preparedStatement = null;

		logger.info("deleteCities:: Start ");

		try {
			con = getConnetion();

			preparedStatement = con.prepareStatement(DELETE_CITIES);

			preparedStatement.executeUpdate();

		} catch (Exception e) {

			logger.severe("Exception while creating Mysql citidb connection " + e.getMessage());

			throw new Exception(e.getMessage());
		} finally {
			if (preparedStatement != null)
				preparedStatement.close();
			if (con != null)
				con.close();
		}

		logger.info("deleteCities:: END ");

	}

}
