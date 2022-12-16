package com.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coordonnee {
	public String latitude;
	public String longitude;
	
	public static Coordonnee fromString(String s) {
		System.out.println("=========================================");
		System.out.println("\n");
		System.out.println(s);
	    // First, remove the leading "Coordonnee(" and the trailing ")"
	    s = s.substring(11, s.length() - 1);

	    // Split the string on commas, except when they are inside quotation marks
	    String[] parts = s.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
	    
	    // Initialize the Coordonnee object
	    Coordonnee c = new Coordonnee();

	    // Parse each part of the string and set the corresponding field in the Coordonnee object
	    for (String part : parts) {
	      String[] keyValue = part.split("=");
	      String key = keyValue[0].trim();
	      String value = keyValue[1].trim();
	      System.out.print(key);
	      System.out.println(value);

	      switch (key) {
	        case "latitude":
	          c.latitude = value;
	          System.out.println("OkLat");
	          break;
	        case "longitude":
	          c.longitude = value;
	          System.out.println("OkLon");
	          break;
	      }
	    }
	    return c;
	}
}
