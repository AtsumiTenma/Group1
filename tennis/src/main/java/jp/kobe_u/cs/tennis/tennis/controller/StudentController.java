package jp.kobe_u.cs.tennis.tennis.controller;

import jp.kobe_u.cs.tennis.tennis.repository.UserRepository;
import jp.kobe_u.cs.tennis.tennis.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final UserRepository userRepository;
    private final AttendanceService attendanceService;
    
    // 本来はログイン機能で認証情報から取得するが、今回は固定ID(生徒)を使用
    private final Long MOCK_STUDENT_ID = 1L;

    @GetMapping("/qr")
    public String showQrPage(Model model) {
        // ログイン中の生徒情報をビューに渡す
        userRepository.findById(MOCK_STUDENT_ID).ifPresent(student -> model.addAttribute("student", student));
        return "student/qr";
    }
    
    @PostMapping("/attend")
    public String recordAttendance(RedirectAttributes redirectAttributes) {
        String message = attendanceService.recordAttendance(MOCK_STUDENT_ID);
        // 処理結果のメッセージをリダイレクト先に渡す
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/student/qr";
    }
}
