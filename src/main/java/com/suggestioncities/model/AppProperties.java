package com.suggestioncities.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {

	@Value("${app.citidata.path}")
	private String citidataPath;
	

	public String getCitidataPath() {
		return citidataPath;
	}


	public void setCitidataPath(String citidataPath) {
		this.citidataPath = citidataPath;
	}


	public static class MysqlDB {
		private String username;
		private String passowrd;
		private String dbname;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassowrd() {
			return passowrd;
		}

		public void setPassowrd(String passowrd) {
			this.passowrd = passowrd;
		}

		public String getDbname() {
			return dbname;
		}

		public void setDbname(String dbname) {
			this.dbname = dbname;
		}

	}

}
