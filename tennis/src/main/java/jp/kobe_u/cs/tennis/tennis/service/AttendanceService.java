package jp.kobe_u.cs.tennis.tennis.service;

import jp.kobe_u.cs.tennis.tennis.entity.Attendance;
import jp.kobe_u.cs.tennis.tennis.entity.AttendanceStatus;
import jp.kobe_u.cs.tennis.tennis.entity.Schedule;
import jp.kobe_u.cs.tennis.tennis.entity.User;
import jp.kobe_u.cs.tennis.tennis.repository.AttendanceRepository;
import jp.kobe_u.cs.tennis.tennis.repository.ScheduleRepository;
import jp.kobe_u.cs.tennis.tennis.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public String recordAttendance(Long studentId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("指定された生徒が見つかりません。ID: " + studentId));

        LocalDateTime now = LocalDateTime.now();
        DayOfWeek today = now.getDayOfWeek();
        LocalTime currentTime = now.toLocalTime();

        // 現在の曜日と時刻に合致するスケジュールを検索
        List<Schedule> currentSchedules = scheduleRepository.findByDayOfWeekAndStartTimeBeforeAndEndTimeAfter(today, currentTime, currentTime);

        if (currentSchedules.isEmpty()) {
            return "現在、出席を受け付けているスケジュールはありません。";
        }

        // 生徒が所属しているスケジュールかを確認
        Schedule targetSchedule = currentSchedules.stream()
                .filter(s -> s.getStudents().contains(student))
                .findFirst()
                .orElse(null);

        if (targetSchedule == null) {
            return "あなたはこの時間のスケジュールに登録されていません。";
        }

        // 新しい出席記録を作成して保存
        Attendance attendance = new Attendance(student, targetSchedule, now, AttendanceStatus.ATTENDED);
        attendanceRepository.save(attendance);

        return String.format("%s さん、出席を記録しました。(%s)", student.getUsername(), now.toLocalTime());
    }

    public List<Attendance> getRecentAttendancesForCoach(Long coachId) {
        // 現在から1ヶ月前の日時を計算
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        return attendanceRepository.findByScheduleCoachIdAndAttendanceDateTimeAfterOrderByAttendanceDateTimeDesc(coachId, oneMonthAgo);
    }

    // 毎日午前4時に実行されるタスク
    @Scheduled(cron = "0 0 4 * * ?")
    @Transactional
    public void deleteOldAttendances() {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        attendanceRepository.deleteByAttendanceDateTimeBefore(oneMonthAgo);
        System.out.println("LOG: 1ヶ月以上前の出席データを削除しました。 - " + LocalDateTime.now());
    }
}
