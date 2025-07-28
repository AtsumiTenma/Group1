package jp.kobe_u.cs.tennis.tennis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.kobe_u.cs.tennis.tennis.form.AttendanceForm;

@Controller
public class StudentController {

    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/schedule")
    public String schedule() {
        return "schedule";
    }

    @GetMapping("/student/attendance")
    public String showAttendanceForm(Model model) {
        model.addAttribute("attendanceForm", new AttendanceForm());
        return "student_attendance";
    }

    @PostMapping("/student/attendance")
    public String submitAttendanceForm(@ModelAttribute("attendanceForm") AttendanceForm form, Model model) {
        // ここでフォームデータを処理します（例：データベースに保存）
        System.out.println("氏名: " + form.getStudentName());
        System.out.println("日付: " + form.getAttendanceDate());
        System.out.println("状況: " + form.getStatus());
        
        model.addAttribute("message", "出席情報が正常に送信されました。");
        return "student_attendance";
    }
}
