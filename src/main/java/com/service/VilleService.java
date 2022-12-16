package com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.model.Coordonnee;
import com.model.Ville;

@Service
public class VilleService {
	
	public List<Ville> getVilles() throws Exception {
		String host = "http://localhost:8181/";
		String s = "villes";
		HttpResponse<JsonNode> response = Unirest.get(host + s).asJson();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(response.getBody().toString());
		String prettyJsonString = gson.toJson(je);
		JSONArray obj = new JSONArray(prettyJsonString);
		List<Ville> villes = new ArrayList<>();
		for (int i = 0; i < obj.length(); i++) {
			JSONObject objVille = obj.getJSONObject(i);
			Ville ville = new Ville();
			ville.setCodeCommune(objVille.getString("codeCommune"));
			ville.setCodePostal(objVille.getString("codePostal"));
			String latitude = objVille.getJSONObject("coordonnee").getString("latitude");
			String longitude = objVille.getJSONObject("coordonnee").getString("longitude");
			Coordonnee coord = new Coordonnee(latitude, longitude);
			ville.setCoordonnee(coord);
			ville.setLibelleAcheminement(objVille.getString("libelleAcheminement"));
			ville.setLigne(objVille.getString("ligne"));
			ville.setNomCommune(objVille.getString("nomCommune"));
			villes.add(ville);
		}
		
		return villes;
	}
	
	public Ville getVille(String id) throws Exception {
		String host = "http://localhost:8181/";
		String s = "ville?codeCommune=" + id;
		HttpResponse<JsonNode> response = Unirest.get(host + s).asJson();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(response.getBody().toString());
		String prettyJsonString = gson.toJson(je);
		JSONObject obj = new JSONObject(prettyJsonString);
		Ville ville = new Ville();
		ville.setCodeCommune(obj.getString("codeCommune"));
		ville.setCodePostal(obj.getString("codePostal"));
		String latitude = obj.getJSONObject("coordonnee").getString("latitude");
		String longitude = obj.getJSONObject("coordonnee").getString("longitude");
		Coordonnee coord = new Coordonnee(latitude, longitude);
		ville.setCoordonnee(coord);
		ville.setLibelleAcheminement(obj.getString("libelleAcheminement"));
		ville.setLigne(obj.getString("ligne"));
		ville.setNomCommune(obj.getString("nomCommune"));
		return ville;
	}
	
	public String putVille(Ville ville) throws Exception {
		String host = "http://localhost:8181/putVille";
		String codeCommune = "?codeCommune=" + ville.codeCommune;
		String nomCommune = "&nomCommune=" + ville.nomCommune.replace(" ", "_");
		String codePostal = "&codePostal=" + ville.codePostal;
		String libelle = "&libelleAcheminement=" + ville.libelleAcheminement.replace(" ", "_");
		String ligne = "&ligne=" + ville.ligne.replace(" ", "_");
		String lat = "&latitude=" + ville.getCoordonnee().getLatitude();
		String lon = "&longitude=" + ville.getCoordonnee().getLongitude();
		
		String requete = host + codeCommune + nomCommune + codePostal + libelle + ligne + lat + lon;
		System.out.println(requete);
		HttpRequestWithBody response = Unirest.put(requete);
		
		System.out.println(response.asString());
		
		System.out.println("nveorvifoe");
		
		return "Ok";
	}
	
	public double calcDist(Ville villeA, Ville villeB) {
		
		System.out.println(villeA.getCoordonnee());
		double latA = Double.valueOf(villeA.getCoordonnee().getLatitude())*Math.PI/180;
		double lonA = Double.valueOf(villeA.getCoordonnee().getLongitude())*Math.PI/180;
		
		double latB = Double.valueOf(villeB.getCoordonnee().getLatitude())*Math.PI/180;
		double lonB = Double.valueOf(villeB.getCoordonnee().getLongitude())*Math.PI/180;
		
		System.out.println(latA + " ; " + latB);
		System.out.println(lonA + " ; " + lonB);
		
		double rayonTerre = 6371;
		
		double distance = rayonTerre * Math.acos(Math.sin(latA)*Math.sin(latB) + Math.cos(latA)*Math.cos(latB)*Math.cos(lonA - lonB));
		
		return distance;
	}
}
