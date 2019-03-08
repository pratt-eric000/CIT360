package com.prt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author P-ratt
 */
public class URLHelper {

	private final Scanner input = new Scanner(System.in);
	private final String OMDBURL = "http://www.omdbapi.com/?apikey=3851964&s=";
	private Map<Integer, JSONObject> results;
	private String resultMenu;
	private String[] vars;
	private String response;
	private final String template = "%s)\t\t%s\n";

	public URLHelper() {

	}

	public void start() {
		resultMenu = "";
		vars = new String[0];
		System.out.println("Welcome to the Open Movie Database (OMDB)");
		questionUser();
	}

	public void questionUser() {
		System.out.print("\nPlease type a movie you would like to know information on: ");
		String answer = input.nextLine().replace(" ", "+");
		if (answer.isEmpty()) {
			answer = input.nextLine().replace(" ", "+");
		}
		buildResponse(OMDBURL + answer);
		buildResults();
		runProgram();
	}

	public void buildResults() {
		results = new HashMap<>();
		resultMenu = "";
		ArrayList<String> varitems = new ArrayList<>();
		try {
			JSONObject obj = new JSONObject(response);
			JSONArray search = obj.getJSONArray("Search");
			int totalResults = search.length();
			if (totalResults > 0) {
				resultMenu = "%s";
				varitems.addAll(new ArrayList<>(Arrays.asList(new String[]{"\n********************************************\nSelect one of the following results\n\n"})));
				for (int i = 0; i < search.length(); i++) {
					JSONObject item = (JSONObject) search.get(i);
					results.put(i + 1, item);
					String title = item.getString("Title");
					resultMenu += template;
					varitems.addAll(new ArrayList<>(Arrays.asList(new String[]{String.valueOf(i + 1), title})));
				}
				resultMenu += template + template + template + "********************************************\n\nSelection> ";
				varitems.addAll(new ArrayList<>(Arrays.asList(new String[]{String.valueOf(totalResults + 1), "Search a different movie"})));
				varitems.addAll(new ArrayList<>(Arrays.asList(new String[]{String.valueOf(totalResults + 3), "Print JSON format of results"})));
				varitems.addAll(new ArrayList<>(Arrays.asList(new String[]{"0", "Exit Program"})));
				vars = varitems.toArray(new String[varitems.size()]);
			}
		} catch (JSONException e) {
			System.out.println("There was a problem parsing the json response. Please try again");
		}
	}

	public void buildResponse(String urlstr) {
		StringBuilder sb = new StringBuilder();
		try {
			URL url = new URL(urlstr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
				String line;
				sb = new StringBuilder();
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		response = sb.toString();
	}

	public void runProgram() {
		int choice = 0;
		do {
			System.out.printf(resultMenu, vars);
			try {
				choice = input.nextInt();
				if (results.get(choice) != null) {
					printMovieData(results.get(choice));
					resetMenuOptions();
				} else if (choice == results.size() + 2) {
					buildResults();
				} else if (choice == results.size() + 3) {
					printJSON();
					resetMenuOptions();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println("That is not a valid option. Please try again.");
			}
		} while (choice != 0 && choice != (results.size() + 1));
		if (choice == results.size() + 1) {
			questionUser();
		} else if (choice == 0) {
			System.out.println("\nThank you for using our services");
		}
	}

	public void printJSON() {
		try {
			JSONObject obj = new JSONObject(response);
			System.out.println(obj.toString(2));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void printMovieData(JSONObject obj) {
		try {
			String format = "\n*****************************INFO***************************\nTitle:\t\t%s\nYear:\t\t%s\nimdbID:\t\t%s\nType:\t\t%s\nPoster:\t\t%s\n*****************************INFO***************************\n\n";
			String title = obj.getString("Title");
			String year = obj.getString("Year");
			String imdbID = obj.getString("imdbID");
			String type = obj.getString("Type");
			String poster = obj.getString("Poster");
			System.out.printf(format, title, year, imdbID, type, poster);
		} catch (JSONException e) {
			System.out.println("There was an error printing movie data. Please try again");
		}
	}

	public void resetMenuOptions() {
		resultMenu = "********************************************\nWhat would you like to do next?\n\n";
		for (int i = 0; i < 3; i++) {
			resultMenu += template;
		}
		resultMenu += "********************************************\n\nSelection> ";
		vars = new String[]{String.valueOf(results.size() + 1), "Search for another movie", String.valueOf(results.size() + 2), "Select another movie from results", "0", "Exit Program"};
	}
}
