package jp.kobe_u.cs.tennis.tennis.controller;

import jp.kobe_u.cs.tennis.tennis.entity.Attendance;
import jp.kobe_u.cs.tennis.tennis.repository.UserRepository;
import jp.kobe_u.cs.tennis.tennis.service.AttendanceService;
import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/coach")
@RequiredArgsConstructor
public class CoachController {

    private final AttendanceService attendanceService;
    private final UserRepository userRepository;

    // 本来はログイン機能で認証情報から取得するが、今回は固定ID(コーチ)を使用
    private final Long MOCK_COACH_ID = 3L;

    @GetMapping({ "", "/" })
    public String showCoachHome(Model model) {
        userRepository.findById(MOCK_COACH_ID).ifPresent(coach -> model.addAttribute("coach", coach));
        return "coach"; // templates/coach.html
    }

    @GetMapping("/attendance")
    public String showAttendanceList(
        @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        Model model) {

    List<Attendance> attendances = attendanceService.getRecentAttendancesForCoach(MOCK_COACH_ID);

    if (date != null) {
        attendances = attendances.stream()
                .filter(att -> att.getAttendanceDateTime().toLocalDate().isEqual(date))
                .collect(Collectors.toList());
        model.addAttribute("selectedDate", date.toString());
    }

    model.addAttribute("attendances", attendances);
    return "coach_attendance_list";
}
}