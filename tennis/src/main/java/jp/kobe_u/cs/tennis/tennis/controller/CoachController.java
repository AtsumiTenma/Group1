package jp.kobe_u.cs.tennis.tennis.controller;

import jp.kobe_u.cs.tennis.tennis.entity.Attendance;
import jp.kobe_u.cs.tennis.tennis.repository.UserRepository;
import jp.kobe_u.cs.tennis.tennis.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/coach")
@RequiredArgsConstructor
public class CoachController {

    private final AttendanceService attendanceService;
    private final UserRepository userRepository;

    // 本来はログイン機能で認証情報から取得するが、今回は固定ID(コーチ)を使用
    private final Long MOCK_COACH_ID = 3L;

    @GetMapping("/attendance")
    public String showAttendanceList(Model model) {
        List<Attendance> attendances = attendanceService.getRecentAttendancesForCoach(MOCK_COACH_ID);
        model.addAttribute("attendances", attendances);
        // コーチ情報をビューに渡す
        userRepository.findById(MOCK_COACH_ID).ifPresent(coach -> model.addAttribute("coach", coach));
        return "coach/attendance";
    }
}