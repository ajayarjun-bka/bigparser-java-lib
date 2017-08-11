package base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;

public class Grid {

	public String gridId;
	public String authId;
	public int limit;

	Grid(String authId, String gridId) {
		this.authId = authId;
		this.gridId = gridId;
		this.limit = 50;
	}

	Grid(String authId, String gridId, int limit) {
		this.authId = authId;
		this.gridId = gridId;
		this.limit = limit;
	}

	public List<String> getRows() {
		Map<String, String> data = new HashMap<String, String>();
		String rowcount = Integer.toString(this.limit);
		data.put("gridId", this.gridId);
		data.put("rowCount", rowcount);
		String response = BigParser.getData(authId, data);
		return responseToList(response);
	}

	public List<String> getRows(int rows) {
		String rowcount = Integer.toString(rows);
		Map<String, String> data = new HashMap<String, String>();
		data.put("gridId", this.gridId);
		data.put("rowCount", rowcount);
		String response = BigParser.getData(authId, data);
		return responseToList(response);
	}

	public List<String> getRows(Integer rows, Map<String, String> searchFilter) {
		Map<String, String> data = new HashMap<String, String>();
		List<String> keywords = new ArrayList<String>();
		List<Map<String, String>> tags = new ArrayList<Map<String, String>>();
		JSONArray tagsList = null;
		Map<String, String> headers = new HashMap<String, String>();
		headers = getHeaders();
		if (rows != null) {
			String rowcount = Integer.toString(rows);
			data.put("rowCount", rowcount);
		} else {
			String rowcount = Integer.toString(this.limit);
			data.put("rowCount", rowcount);
		}
		if (searchFilter != null) {
			if (searchFilter.containsKey("GLOBAL")) {
				List<String> globalList = new ArrayList<String>(Arrays.asList(searchFilter.get("GLOBAL").split(",")));
				for (String s : globalList) {
					StringBuilder sb = new StringBuilder(s);
					sb.insert(0, "\"");
					String temp = sb + "\"";
					keywords.add(temp);
				}
			}
			Iterator<Entry<String, String>> iterator = searchFilter.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, String> entry = iterator.next();
				if (entry.getKey().equals("GLOBAL")) {
					iterator.remove();
				} else {
					List<String> multiColumnEntry = new ArrayList<String>(Arrays.asList(entry.getValue().split(",")));
//					System.out.println("multicolumn :" + multiColumnEntry);
					for (int i = 0; i < multiColumnEntry.size(); i++) {
						Map<String, String> temp = new HashMap<String, String>();
						temp.put("columnStoreName", headers.get(entry.getKey()));
						temp.put("columnValue", multiColumnEntry.get(i));
						tags.add(temp);
					}
				}
			}
			tagsList = new JSONArray(tags);
			data.put("keywords", keywords.toString());
			data.put("tags", tagsList.toString());
		}
		data.put("gridId", this.gridId);
		// System.out.println(data);
		String response = BigParser.getData(authId, data);
		return responseToList(response);
	}

	public List<String> getRows(Integer rows, Map<String, String> searchFilter, Map<String, String> sort) {
		Map<String, String> data = new HashMap<String, String>();
		List<String> keywords = new ArrayList<String>();
		List<Map<String, String>> tags = new ArrayList<Map<String, String>>();
		List<Map<String, String>> sortKeys = new ArrayList<Map<String, String>>();
		JSONArray tagsList = null;
		JSONArray sortList = null;
		Map<String, String> headers = new HashMap<String, String>();
		headers = getHeaders();
		if (rows != null) {
			String rowcount = Integer.toString(rows);
			data.put("rowCount", rowcount);
		} else {
			String rowcount = Integer.toString(this.limit);
			data.put("rowCount", rowcount);
		}
		if (searchFilter != null) {
			if (searchFilter.containsKey("GLOBAL")) {
				List<String> globalList = new ArrayList<String>(Arrays.asList(searchFilter.get("GLOBAL").split(",")));
				for (String s : globalList) {
					StringBuilder sb = new StringBuilder(s);
					sb.insert(0, "\"");
					String temp = sb + "\"";
					keywords.add(temp);
				}
			}
			Iterator<Entry<String, String>> iterator = searchFilter.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, String> entry = iterator.next();
				if (entry.getKey().equals("GLOBAL")) {
					iterator.remove();
				} else {
					List<String> multiColumnEntry = new ArrayList<String>(Arrays.asList(entry.getValue().split(",")));
//					System.out.println("multicolumn :" + multiColumnEntry);
					for (int i = 0; i < multiColumnEntry.size(); i++) {
						Map<String, String> temp = new HashMap<String, String>();
						temp.put("columnStoreName", headers.get(entry.getKey()));
						temp.put("columnValue", multiColumnEntry.get(i));
						tags.add(temp);
					}
				}
			}
			tagsList = new JSONArray(tags);
			data.put("keywords", keywords.toString());
			data.put("tags", tagsList.toString());
		}
		if (sort != null) {
			Iterator<Entry<String, String>> iterator = sort.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, String> entry = iterator.next();
				Map<String, String> temp = new HashMap<String, String>();
				temp.put("columnStoreName", headers.get(entry.getKey()));
				if (entry.getValue().equals("DSC")) {
					temp.put("ascending", "false");
				} else {
					temp.put("ascending", "true");
				}
				sortKeys.add(temp);
			}
			sortList = new JSONArray(sortKeys);
			data.put("sortKeys", sortList.toString());
		}
		data.put("gridId", this.gridId);
		// System.out.println(data);
		String response = BigParser.getData(authId, data);
		return responseToList(response);
	}

	public List<String> getRows(Integer rows, Map<String, String> searchFilter, Map<String, String> sort,
			String columns) {
		Map<String, String> data = new HashMap<String, String>();

		List<String> keywords = new ArrayList<String>();
		List<String> selectColumnsStoreName = new ArrayList<String>();
		List<Map<String, String>> tags = new ArrayList<Map<String, String>>();
		List<Map<String, String>> sortKeys = new ArrayList<Map<String, String>>();
		JSONArray tagsList = null;
		JSONArray sortList = null;
		Map<String, String> headers = new HashMap<String, String>();
		headers = getHeaders();
		if (rows != null) {
			String rowcount = Integer.toString(rows);
			data.put("rowCount", rowcount);
		} else {
			String rowcount = Integer.toString(this.limit);
			data.put("rowCount", rowcount);
		}
		if (searchFilter != null) {
			if (searchFilter.containsKey("GLOBAL")) {
				List<String> globalList = new ArrayList<String>(Arrays.asList(searchFilter.get("GLOBAL").split(",")));
				for (String s : globalList) {
					StringBuilder sb = new StringBuilder(s);
					sb.insert(0, "\"");
					String temp = sb + "\"";
					keywords.add(temp);
				}
			}
			Iterator<Entry<String, String>> iterator = searchFilter.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, String> entry = iterator.next();
				if (entry.getKey().equals("GLOBAL")) {
					iterator.remove();
				} else {
					List<String> multiColumnEntry = new ArrayList<String>(Arrays.asList(entry.getValue().split(",")));
//					System.out.println("multicolumn :" + multiColumnEntry);
					for (int i = 0; i < multiColumnEntry.size(); i++) {
						Map<String, String> temp = new HashMap<String, String>();
						temp.put("columnStoreName", headers.get(entry.getKey()));
						temp.put("columnValue", multiColumnEntry.get(i));
						tags.add(temp);
					}
				}
			}
			tagsList = new JSONArray(tags);
			data.put("keywords", keywords.toString());
			data.put("tags", tagsList.toString());
		}
		if (sort != null) {
			Iterator<Entry<String, String>> iterator = sort.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, String> entry = iterator.next();
				Map<String, String> temp = new HashMap<String, String>();
				temp.put("columnStoreName", headers.get(entry.getKey()));
				if (entry.getValue().equals("DSC")) {
					temp.put("ascending", "false");
				} else {
					temp.put("ascending", "true");
				}
				sortKeys.add(temp);
			}
			sortList = new JSONArray(sortKeys);
			data.put("sortKeys", sortList.toString());
		}
		if (columns != null) {
			List<String> columnsList = new ArrayList<String>(Arrays.asList(columns.split(",")));
			for (String key : columnsList) {
				selectColumnsStoreName.add(headers.get(key));
			}
			data.put("selectColumnsStoreName", selectColumnsStoreName.toString());
		}
		data.put("gridId", this.gridId);
		// System.out.println(data);
		String response = BigParser.getData(authId, data);
		return responseToList(response);
	}

	public List<String> getLastRow(Integer rows, Map<String, String> searchFilter, Map<String, String> sort,
			String columns) {
		Map<String, String> data = new HashMap<String, String>();
		List<String> keywords = new ArrayList<String>();
		List<String> selectColumnsStoreName = new ArrayList<String>();
		List<Map<String, String>> tags = new ArrayList<Map<String, String>>();
		List<Map<String, String>> sortKeys = new ArrayList<Map<String, String>>();
		JSONArray tagsList = null;
		JSONArray sortList = null;
		Map<String, String> headers = new HashMap<String, String>();
		headers = getHeaders();
		if (rows != null) {
			String rowcount = Integer.toString(rows);
			data.put("rowCount", rowcount);
		} else {
			String rowcount = Integer.toString(this.limit);
			data.put("rowCount", rowcount);
		}
		if (searchFilter != null) {
			if (searchFilter.containsKey("GLOBAL")) {
				List<String> globalList = new ArrayList<String>(Arrays.asList(searchFilter.get("GLOBAL").split(",")));
				for (String s : globalList) {
					StringBuilder sb = new StringBuilder(s);
					sb.insert(0, "\"");
					String temp = sb + "\"";
					keywords.add(temp);
				}
			}
			System.out.println(keywords);
			Iterator<Entry<String, String>> iterator = searchFilter.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, String> entry = iterator.next();
				if (entry.getKey().equals("GLOBAL")) {
					iterator.remove();
				} else {
					List<String> multiColumnEntry = new ArrayList<String>(Arrays.asList(entry.getValue().split(",")));
					for (int i = 0; i < multiColumnEntry.size(); i++) {
						Map<String, String> temp = new HashMap<String, String>();
						temp.put("columnStoreName", headers.get(entry.getKey()));
						temp.put("columnValue", multiColumnEntry.get(i));
						tags.add(temp);
					}
				}
			}
			tagsList = new JSONArray(tags);
			data.put("keywords", keywords.toString());
			data.put("tags", tagsList.toString());
		}
		if (sort != null) {
			Iterator<Entry<String, String>> iterator = sort.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, String> entry = iterator.next();
				Map<String, String> temp = new HashMap<String, String>();
				temp.put("columnStoreName", headers.get(entry.getKey()));
				if (entry.getValue().equals("DSC")) {
					temp.put("ascending", "false");
				} else {
					temp.put("ascending", "true");
				}
				sortKeys.add(temp);
			}
			sortList = new JSONArray(sortKeys);
			data.put("sortKeys", sortList.toString());
		}
		if (columns != null) {
			List<String> columnsList = new ArrayList<String>(Arrays.asList(columns.split(",")));
			for (String key : columnsList) {
				selectColumnsStoreName.add(headers.get(key));
			}
			data.put("selectColumnsStoreName", selectColumnsStoreName.toString());
		}
		data.put("gridId", this.gridId);
		System.out.println(data);
		String response = BigParser.getLastRow(authId, data);
		System.out.println(response);
		return responseToList(response);
	}

	public HashMap<String, String> getHeaders() {
		String response = BigParser.getHeader(authId, gridId);
		LinkedHashMap<String, String> headers;
		int startofColumns = response.indexOf("\"columns\"");
		int startofDictionary = response.indexOf("\"dictionary\"");
		String columns = response.substring(startofColumns + 10, startofDictionary - 1);
		headers = (LinkedHashMap<String, String>) JSONToMap(columns);
		return headers;
	}

	private Map<String, String> JSONToMap(String JSON) {
		JSONArray json = new JSONArray(JSON);
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		for (int i = 0; i < json.length(); ++i) {
			JSONObject record = json.getJSONObject(i);
			String id = record.getString("columnStoreName");
			String columnName = record.getString("columnName").toLowerCase();
			map.put(columnName, id);
		}
		return map;
	}

	private List<String> responseToList(String response) {
		int startOfRows = response.indexOf("rows");
		int endOfRows = response.indexOf("otherGrids");
		String result = response.substring(startOfRows + 6, endOfRows - 2);
		List<String> list = new ArrayList<String>();
		if (startOfRows != -1) {
			JSONArray json = new JSONArray(result);
			for (int i = 0; i < json.length(); i++) {
				list.add(json.getJSONObject(i).get("data").toString());
			}
		}
		return list;
	}
}
