package tuto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConnexionController {

	@GetMapping( "/" )
	public String page1() {
		return "connexion/home";
	}

	@GetMapping( "/connect" )
	public String page2(String username, String password, Model model) {
		var sb = new StringBuilder( username );
		if ( sb.reverse().toString().equals(  password  ) ) {
			model.addAttribute(  "connected", username  );
			return "connexion/bonjour";
		} else {
			model.addAttribute( "alert", "Mot de passe incorrect" );
			return "connexion/home";
		}
	}

}
