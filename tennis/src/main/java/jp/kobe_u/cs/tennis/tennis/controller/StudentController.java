package jp.kobe_u.cs.tennis.tennis.controller;

import jp.kobe_u.cs.tennis.tennis.repository.UserRepository;
import jp.kobe_u.cs.tennis.tennis.service.AttendanceService;
import lombok.Data;
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
    public String showQrPage(Model model, @ModelAttribute("message") String flashMessage) {
        // 生徒情報
        userRepository.findById(MOCK_STUDENT_ID)
                .ifPresent(student -> model.addAttribute("student", student));


        // フラッシュメッセージ
        if (flashMessage != null && !flashMessage.isBlank()) {
            model.addAttribute("message", flashMessage);
        }

        return "student_attendance";
    }

    @PostMapping("/attend")
    public String recordAttendance(RedirectAttributes ra) {
        String message = attendanceService.recordAttendance(MOCK_STUDENT_ID);
        ra.addFlashAttribute("message", message);

        return "redirect:/student/qr";
    }

    @GetMapping
    public String studentRoot() {
        return "redirect:/student/qr";
    }

    // フォーム（必要なら項目を増やせます）
    @Data
    public static class AttendanceForm {
        // 例: 連絡メモなどが欲しければここにフィールドを追加
        // private String note;
    }
}
