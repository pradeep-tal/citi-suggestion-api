package com.suggestioncities.model;

public class Response {

	private String type;
	private String message;

	public Response(String type, String message) {

		this.type = type;
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {

		return "{\"type\":" + "\"" + type.toLowerCase() + "\"" + " , " + "\"message\":" + "\"" + message.toLowerCase() + "\"}";
	}

}
