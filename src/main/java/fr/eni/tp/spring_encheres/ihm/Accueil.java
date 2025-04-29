package fr.eni.tp.spring_encheres.ihm;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Accueil {

    @GetMapping("/")
    public String afficherAccueil(Model model) {
       // model.addAttribute();
       // model.addAttribute();
        return "index";
    }
}
