package base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Sample program to show how to use BpData.java to fetch data from BigParser
 * 
 * @author Ajay Arjun
 * @version 1.0
 */
public class App {

	public static void main(String args[]) {
		// String result = null;
		// String authId=null;
		String gridId = "57a34c80e4b017cc76c37c25";

		// //Function Call to login into BigParser account and store the authId\
		// System.out.println("Login Functionality");
		// authId = BigParser.login("arjun.bka@gmail.com", "AjayArjun");
		// System.out.println(authId);
		//
		//
		// // Function Call to fetch header of the specified grid
		// System.out.println("Get Header Functionality");
		// result = BigParser.getHeader(authId,"57a33a99e4b019ed65d2b00d");
		// System.out.println(result);
		// // Function Call to fetch rows from the specified grid based on the
		// // filtering parameters
		// // Build Map Containing parameters body of the request to fetch data

		// Map<String, String> data = new HashMap<String, String>();
		// data.put("rowCount", "10");
		// data.put("selectColumnsStoreName", "[\"0\",\"3\"]");
		// data.put("keywords", "[\"wine bar\"]");
		// data.put("sortKeys", "[{\"columnStoreName\": \"1\",\"ascending\":
		// true}]");

		// System.out.println("Get Data Functionality");
		// result = BigParser.getData(authId,"57a33a99e4b019ed65d2b00d",data);
		// System.out.println(result);

		// System.out.println("Get Last Row Functionality");
		// result = BigParser.getLastRow(authId, gridId, data);
		// System.out.println(result);

		// System.out.println("SignUp Functionality");
		// result=BigParser.signup("loroli@matchpol.net", "password", "a", "b",
		// "c", "d");
		// System.out.println(result);
		//
		// System.out.println("Search Column Functionality");
		// result=BigParser.searchColumn(authId, gridId, "2", "bar", "10");
		// System.out.println(result);

		
		String authId = Auth.login("arjun.bka@gmail.com", "AjayArjun");
//		System.out.println(authId);
		List<String> result = new ArrayList<String>();
		Map<String, String> searchfilter = new HashMap<String, String>();
		searchfilter.put("GLOBAL", "x-men");
		searchfilter.put("year", "2000,2016");
//		searchfilter.put("language ", "English,French");
		Map<String, String> sort = new HashMap<String, String>();
		sort.put("year", "ASC");
		String columns = "film name ,year";

		Grid movies = new Grid(authId, gridId);
//		movies.getRows();
//		result = movies.getRows(null,searchfilter, sort, columns);
//		System.out.println(result);
//		movies.getHeaders();
		result = movies.getLastRow(null,searchfilter,null,columns);
		System.out.println(result);
	}
}
