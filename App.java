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
		String gridId = "57a34c80e4b017cc76c37c25";
		String authId = Auth.login("arjun.bka@gmail.com", "AjayArjun");
		Grid movies = new Grid(authId, gridId);
		List<String> result = new ArrayList<String>();
		
		// Build search filter
		Map<String, String> searchfilter = new HashMap<String, String>();
		searchfilter.put("GLOBAL", "x-men");
//		searchfilter.put("year", "2000,2016");
		searchfilter.put("language ", "English");
		
		//Build Sort 
		Map<String, String> sort = new HashMap<String, String>();
		sort.put("year", "ASC");
		
		
		//columns to be displayed 
		String columns = "film name ,year";

		
		result = movies.getRows();
		System.out.println(result);
		
		result = movies.getRows(20,null,null,columns);
		System.out.println(result);
		
		result = movies.getRows(null,searchfilter, sort, columns);
		System.out.println(result);
		
		Map<String, String> headers = new HashMap<>();
		headers = movies.getHeaders();
		System.out.println(headers);

		result = movies.getLastRow(searchfilter,null,columns,4);
		System.out.println(result);
	}
}
