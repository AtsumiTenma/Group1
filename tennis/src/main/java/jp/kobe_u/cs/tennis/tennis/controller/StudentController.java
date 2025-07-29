package jp.kobe_u.cs.tennis.tennis.controller;

import jp.kobe_u.cs.tennis.tennis.repository.UserRepository;
import jp.kobe_u.cs.tennis.tennis.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final UserRepository userRepository;
    private final AttendanceService attendanceService;

    // 本来はログインから取得。今回は固定
    private final Long MOCK_STUDENT_ID = 1L;

    @GetMapping("/qr")
    public String showQrPage(Model model) {
        // 生徒情報
        userRepository.findById(MOCK_STUDENT_ID)
                .ifPresent(student -> model.addAttribute("student", student));

        return "student_attendance";
    }

    @PostMapping("/attend")
    public String recordAttendance(RedirectAttributes ra) {
        String message = attendanceService.recordAttendance(MOCK_STUDENT_ID);
        ra.addFlashAttribute("message", message);

        return "redirect:/student/qr";
    }

     @GetMapping("/schedule")
    public String schedule() {
        return "student_schedule";
    }

    @GetMapping("/absence")
    public String absence() {
        return "student_absence";
    }

    @GetMapping("/transfer")
    public String transfer() {
        return "student_transfer";
    }
}
