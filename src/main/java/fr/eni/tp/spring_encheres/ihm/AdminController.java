package fr.eni.tp.spring_encheres.ihm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String afficherPageAdmin() {
        return "admin"; // correspond à admin.html dans /templates
    }
}
