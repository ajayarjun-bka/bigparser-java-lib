package bigparser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * BpData This class implements the methods needed to connect to BigParser's API
 * to authenticate and fetch the required data
 * 
 * @author Ajay Arjun
 * @version 1.0
 */

class BigParser {
	static String prod_uri = "https://www.bigparser.com/APIServices/";
	static String qa_uri = "https://qa.bigparser.com/APIServices/";

	/**
	 * This method makes generic post calls
	 * 
	 * @param endPoint
	 *            Target Url
	 * @param headers
	 *            Headers to be passed for the current request
	 * @param data
	 *            Body of the post request
	 * @return String returns the response as JSON Object
	 */

	private static String post(String endpoint, Map<String, String> headers, String data) {
		// System.out.println(data);
		StringBuilder response = new StringBuilder();
		try {
			URL uri = new URL(endpoint);
			HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
			for (String key : headers.keySet()) {
				conn.setRequestProperty(key, headers.get(key));
			}
			// System.out.println(data);
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			OutputStream output = conn.getOutputStream();
			output.write(data.getBytes());
			output.flush();
			output.close();
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				String temp;
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				while ((temp = br.readLine()) != null) {
					response.append(temp);
				}
				// throw new RuntimeException(
				// String.format("Error Code:%s\nMessage%s",
				// conn.getResponseCode(), conn.getResponseMessage()));
			} else {
				// System.out.println("Success Inside Else");
				String temp;
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				while ((temp = br.readLine()) != null) {
					response.append(temp);
				}
				if (!(response.length() > 0)) {
					response.append("200");
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return response.toString();
	}

	/**
	 * This method makes generic get calls
	 * 
	 * @param endPoint
	 *            Target Url
	 * @param headers
	 *            Headers to be passed for the current request
	 * @return String returns the response as JSON Object
	 */

	private static String get(String endpoint, Map<String, String> headers) {
		StringBuilder response = new StringBuilder();
		try {
			URL uri = new URL(endpoint);
			HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
			for (String key : headers.keySet()) {
				conn.setRequestProperty(key, headers.get(key));
			}
			conn.setRequestMethod("GET");

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException(
						String.format("Error Code:%s\nMessage%s", conn.getResponseCode(), conn.getResponseMessage()));
			} else {
				String temp;
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				while ((temp = br.readLine()) != null) {
					response.append(temp);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return response.toString();
	}

	/**
	 * This method performs the task of login into BigParser account and fetch
	 * authId for future calls
	 * 
	 * @param emailId
	 *            emailId/username of your account
	 * 
	 * @param password
	 *            password to login into BigParser account
	 * 
	 * @return String returns the response as JSON Object
	 */
	public static String login(String emailId, String password) {
		String authId = null;
		String uri = "https://www.bigparser.com/APIServices/api/common/login";
		Map<String, String> header = new HashMap<String, String>();
		header.put("content-type", "application/json");
		Map<String, String> data = new HashMap<String, String>();
		if (emailId != null) {
			data.put("emailId", emailId);
		}
		if (password != null) {
			data.put("password", password);
		}
		String JSONData = BigParser.maptoJSON(data);
		String response = post(uri, header, JSONData);
		int startOfAuthID = response.indexOf("authId");
		if (startOfAuthID > 0) {
			authId = response.substring(startOfAuthID + 9, startOfAuthID + 45);
			return authId;
		}
		else
		{
			try{
				throw new RuntimeException();
			}
			catch (Exception e)
			{
				System.out.println("Unauthorized User.Please check your credentials");
				System.exit(-1);
			}
			return null;
		}
	}

	/**
	 * This method is responsible for new BigParser account signup.
	 * 
	 * @param emailId
	 *            emailId/username for theaccount
	 * @param password
	 *            password to login into BigParser account
	 * @param fullname
	 * 
	 * @param mobileNumber
	 * 
	 * @return String returns the response as JSON Object
	 */

	public static String signup(String emailId, String password, String fullName, String mobileNumber, String srcName,
			String visitId) {
		String uri = "https://www.bigparser.com/APIServices/api/common/signup";
		Map<String, String> data = new HashMap<String, String>();
		if (emailId != null) {
			data.put("emailId", emailId);
		}
		if (password != null) {
			data.put("password", password);
		}
		if (fullName != null) {
			data.put("fullName", fullName);
		}

		if (mobileNumber != null) {
			data.put("mobileNumber", mobileNumber);
		}

		if (srcName != null) {
			data.put("srcName", srcName);
		}

		if (visitId != null) {
			data.put("visitId", visitId);
		}

		String JSONData = BigParser.maptoJSON(data);
		Map<String, String> header = new HashMap<String, String>();
		header.put("Content-Type", "application/json");
		String response = post(uri, header, JSONData);
		if (response.equals("200")) {
			response = "Please confirm your email address by signing in to your email to complete registration";
		}
		if (response.length() > 0) {
			return response;
		}
		return "Please Check Your Request";
	}

	/**
	 * Fetches rows from the specified grid. Parameters to query the grid are
	 * passed as a part of the post request
	 * 
	 * @param emailId
	 *            emailId/username of your account
	 * @param password
	 *            password to login into BigParser account
	 * @param data
	 *            comprises the options to query the grid in the form of JSON
	 *            object.
	 * @return String returns the response as JSON in string format
	 */

	public static String getData(String authId, Map<String, String> data) {
		String uri = "https://www.bigparser.com/APIServices/api/query/table";
		String JSONData = BigParser.maptoJSON(data);
		if (authId != null) {
			Map<String, String> header = new HashMap<String, String>();
			header.put("Content-Type", "application/json");
			header.put("authId", authId);
			String response = post(uri, header, JSONData);
			return response;
		}
		return "please check your request";
	}

	/**
	 * Function to fetch header of a grid. gridId is required.
	 *
	 * @param emailId
	 *            emailId/username of your account
	 * @param password
	 *            password to login into BigParser account
	 * @param gridId
	 *            gridId of the required grid
	 * @return String returns the response as JSON in string format
	 */

	public static String getHeader(String authId, String gridId) {
		String uri = String.format("https://www.bigparser.com/APIServices/api/grid/headers?gridId=%s", gridId);
		if (authId != null) {
			Map<String, String> header = new HashMap<String, String>();
			header.put("Content-Type", "application/json");
			header.put("authId", authId);
			String response = get(uri, header);
			return response;
		}
		return "please check your request";
	}

	/**
	 * Fetches rows from the specified grid. Parameters to query the grid are
	 * passed as a part of the post request
	 * 
	 * @param emailId
	 *            emailId/username of your account
	 * @param password
	 *            password to login into BigParser account
	 * @param data
	 *            comprises the options to query the grid in the form of JSON
	 *            object.
	 * @return String returns the response as JSON in string format
	 */

	public static String searchColumn(String authId, String gridId, String columnStoreName, String searchKey,
			String pageSize) {
		String uri = "https://www.bigparser.com/APIServices/api/query/search";
		Map<String, String> data = new HashMap<String, String>();
		if (gridId != null) {
			data.put("gridId", gridId);
		}

		if (searchKey != null) {
			data.put("searchKey", searchKey);
		}

		if (columnStoreName != null) {
			data.put("columnStoreName", columnStoreName);
		}

		if (pageSize != null) {
			data.put("pageSize", pageSize);
		}

		String JSONData = BigParser.maptoJSON(data);
		if (authId != null) {
			Map<String, String> header = new HashMap<String, String>();
			header.put("Content-Type", "application/json");
			header.put("authId", authId);
			String response = post(uri, header, JSONData);
			return response;
		}
		return "please check your request";
	}

	public static String getLastRow(String authId, Integer count, Map<String, String> data) {
		String uri = "https://www.bigparser.com/APIServices/api/query/table";
		String JSONData = BigParser.maptoJSON(data);
		try {
			if (authId != null) {
				Map<String, String> header = new HashMap<String, String>();
				header.put("Content-Type", "application/json");
				header.put("authId", authId);
				String response = post(uri, header, JSONData);
				int startOfCount = response.indexOf("count");
				int startOfRows = response.indexOf("rows");
				if ((startOfCount > 0) && (startOfRows > 0)) {
					String endpoint = null;
					int rowcount = Integer.parseInt(response.substring(startOfCount + 7, startOfRows - 2));
					if (count == null) {
						endpoint = String.format("api/query/table?startIndex=%d&endIndex=%d",rowcount, rowcount);
					}
					else
					{
						endpoint = String.format("api/query/table?startIndex=%d&endIndex=%d",
								((rowcount - count) + 1), rowcount);
					}

					uri = prod_uri + endpoint;
					response = post(uri, header, JSONData);
					return (response);
				}
			}
		} catch (NullPointerException e) {
			System.out.println("Response Not as Expected");
		}

		return "please check your request";
	}

	private static String maptoJSON(Map<String, String> data) {
		int dataLength = data.size();
		int count = 1;
		StringBuilder JSONData = new StringBuilder();
		JSONData.append("{");
		for (String key : data.keySet()) {
			if (data.get(key).startsWith("[") || data.get(key).startsWith("{")) {
				JSONData.append("\"" + key + "\"" + ":" + data.get(key));
				if (count < dataLength) {
					JSONData.append(",");
				}
				count = count + 1;
			} else {
				JSONData.append("\"" + key + "\"" + ":" + "\"" + data.get(key) + "\"");
				if (count < dataLength) {
					JSONData.append(",");
				}
				count = count + 1;
			}
		}
		JSONData.append("}");
		return JSONData.toString();
	}
}
