package jp.kobe_u.cs.tennis.tennis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.kobe_u.cs.tennis.tennis.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserRepository userRepository;
    private final Long MOCK_STUDENT_ID = 1L;

    @GetMapping("/")
    public String home(Model model, Principal principal) {
        userRepository.findById(MOCK_STUDENT_ID)
                .ifPresent(student -> model.addAttribute("student", student));
        return "home"; // templates/home.html
    }
}
