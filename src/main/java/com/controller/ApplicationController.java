package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.model.BinomeVille;
import com.model.Coordonnee;
import com.model.Ville;
import com.service.VilleService;

@Controller
public class ApplicationController {
	
	@Autowired
	public VilleService villeService;

	@GetMapping("/")
	public String home(Model model) throws Exception {
		List<Ville> villes = villeService.getVilles();
		model.addAttribute("villes", villes);
		BinomeVille binomeVille = new BinomeVille();
		Ville ville1 = new Ville();
		Ville ville2 = new Ville();
		ville1.setCoordonnee(new Coordonnee("47.4711", "-0.547307"));
		ville2.setCoordonnee(new Coordonnee("47.5860921", "1.3359475"));
		binomeVille.setVilleA(ville1);
		binomeVille.setVilleB(ville2);
		model.addAttribute("binomeVille", binomeVille);
		//System.out.println(villeService.calcDist(ville1, ville2));
		return "home";
	}
	
	@PostMapping("/")
	public String distance(Model model, @ModelAttribute("villeA") String villeA, @ModelAttribute("villeB") String villeB) throws Exception {
		
		System.out.println("Hello there !");
		System.out.println(villeA);
		System.out.println(villeB);
		System.out.println(villeA.split(",")[0]);
		Ville ville1 = Ville.fromString(villeA);
		Ville ville2 = Ville.fromString(villeB);
		Double distance = villeService.calcDist(ville1, ville2);
		System.out.println(distance);
		BinomeVille binome = new BinomeVille(ville1, ville2);
		List<Ville> villes = villeService.getVilles();
		model.addAttribute("binomeVille", binome);
		model.addAttribute("villes", villes);
		model.addAttribute("villeA", ville1);
		model.addAttribute("villeB", ville2);
		model.addAttribute("distance", distance);
		
		return "home";
	}
	
	@GetMapping("/listVille")
	public String listVille(Model model) throws Exception {
		List<Ville> villes = villeService.getVilles();
		model.addAttribute("villes", villes);
		return "listVilles";
	}
	
	@GetMapping("/listVille/{codeCommune}")
	public String infosVille(Model model, @PathVariable String codeCommune) throws Exception {
		Ville ville = villeService.getVille(codeCommune);
		model.addAttribute("codeC", codeCommune);
		model.addAttribute("ville", ville);
		return "infosVille";
	}
	
	@PostMapping("/listVille/{codeCommune}")
	public String post(Model model,@ModelAttribute("codeCommune") String codeCommune, @ModelAttribute("nomCommune") String nomCommune, @ModelAttribute("codePostal") String codePostal, @ModelAttribute("libelleAcheminement") String libelleAcheminement, @ModelAttribute("ligne") String ligne, @ModelAttribute("coordonnee.latitude") String latitude, @ModelAttribute("coordonnee.longitude") String longitude) throws Exception {
		Ville ville = new Ville(codeCommune, nomCommune, codePostal, libelleAcheminement, ligne, new Coordonnee(latitude, longitude));
		System.out.println(ville);
		String reponse = villeService.putVille(ville);
		model.addAttribute("codeC", codeCommune);
		model.addAttribute("ville", ville);
		return "infosVille";
	}
	
}
