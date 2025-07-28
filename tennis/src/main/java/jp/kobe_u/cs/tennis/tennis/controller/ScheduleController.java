package jp.kobe_u.cs.tennis.tennis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ScheduleController {

    @GetMapping("/schedule")
    public String schedule() {
        return "schedule";
    }
}
