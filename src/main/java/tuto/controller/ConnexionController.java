package tuto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@SessionAttributes( "connected" )
public class ConnexionController {

	@GetMapping( "/" )
	public String home() {
		return "connexion/home";
	}
	
	@GetMapping( "/bonjour" )
	public String bonjour() {
		return "connexion/bonjour";
	}
	
	@PostMapping("/connect")
	public String connect(@RequestParam String username,
	                      @RequestParam String password,
	                      Model model) {

	    // Règle de l’exercice : mot de passe = reverse(username)
	    boolean ok = new StringBuilder(username).reverse().toString().equals(password);

	    if (ok) {
	        // Connexion OK
	        model.addAttribute("connected", username);
	        model.addAttribute("username", username); // utile si tu l’affiches dans la vue bonjour
	        return "connexion/bonjour";               // SANS .html
	    } else {
	        // Connexion KO → on renvoie les valeurs pour préremplir les champs
	        model.addAttribute("alert", "Mot de passe incorrect");

	        // ⬇️ Les 2 variables attendues par th:value dans home.html
	        model.addAttribute("username", username);
	        model.addAttribute("password", password); // ⚠️ Ok pour l’exercice, à éviter en vrai

	        model.addAttribute("connected", false);   // pour masquer "Se déconnecter" dans la navbar
	        return "connexion/home";                  // SANS .html
	    }
	}
	
/*
	@PostMapping( "/connect" )
	public String connect(@RequestParam String username, @RequestParam String password, Model model) {
		
		var sb = new StringBuilder( username );
		boolean ok = sb.reverse().toString().equals(  password  );
		if ( ok ) {
			model.addAttribute(  "connected", true );
			model.addAttribute( "username", username);
			return "connexion/bonjour";
		} else {
			model.addAttribute( "erreur", "Identifiants incorrects");
			model.addAttribute( "loginSaisi", username);
			model.addAttribute( "connected", false);
			model.addAttribute( "alert", "Mot de passe incorrect" );
			return "connexion/home";
		}
	}
	*/
	
	@PostMapping( "/disconnect" )
	public String disconnect(SessionStatus status, RedirectAttributes ra) {
		status.setComplete();
		ra.addFlashAttribute( "alert", "Déconnexion effectuée avec succès" );
		return "redirect:/";
	}


}
