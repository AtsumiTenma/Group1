package jp.kobe_u.cs.tennis.tennis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model, Principal principal) {
        boolean authenticated = (principal != null);
        String username = authenticated ? principal.getName() : "田中 太郎"; // デフォルトのユーザー名

        model.addAttribute("username", username);
        model.addAttribute("authenticated", authenticated);
        return "home"; // templates/home.html
    }
}
