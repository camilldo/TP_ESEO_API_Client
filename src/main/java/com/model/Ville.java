package com.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ville {
	private static final long serialVersionUID = -8050478362033217382L;
	
	public String codeCommune;
	public String nomCommune;
	public String codePostal;
	public String libelleAcheminement;
	public String ligne;
	public Coordonnee coordonnee;
	
	public static Ville fromString(String s) {
	    // First, remove the leading "Ville(" and the trailing ")"
	    s = s.substring(6, s.length() - 1);

	    // Split the string on commas, except when they are inside quotation marks
	    String[] parts = s.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

	    // Initialize the Commune object
	    Ville c = new Ville();
	    parts[parts.length-2] = parts[parts.length-2].concat(", " + parts[parts.length-1]);
	    
	    // Parse each part of the string and set the corresponding field in the Commune object
	    for (String part : parts) {
	      String[] keyValue = part.split("=");
	      if (keyValue.length > 2) {
	    	  for (int i = 2; i < keyValue.length; i++) {
	    		  keyValue[1] = keyValue[1].concat("="+keyValue[i]);
	    	  }
	      }
	      String key = keyValue[0].trim();
	      String value;
	      try {
	    	  value = keyValue[1].trim();
	      } catch (Exception e) {
	    	  value = ""; 
	      }

	      switch (key) {
	        case "codeCommune":
	          c.codeCommune = value;
	          break;
	        case "nomCommune":
	          c.nomCommune = value;
	          break;
	        case "codePostal":
	          c.codePostal = value;
	          break;
	        case "libelleAcheminement":
	          c.libelleAcheminement = value;
	          break;
	        case "ligne":
	          c.ligne = value;
	          break;
	        case "coordonnee":
	          // The value of the "coordonnee" field is a string representation of a Coordonnee object
	          c.coordonnee = Coordonnee.fromString(value);
	          break;
	      }
	    }

	    return c;
	  }
}

