package jp.kobe_u.cs.tennis.tennis.repository;

import jp.kobe_u.cs.tennis.tennis.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByScheduleCoachIdAndAttendanceDateTimeAfterOrderByAttendanceDateTimeDesc(Long coachId, LocalDateTime dateTime);

    @Transactional
    void deleteByAttendanceDateTimeBefore(LocalDateTime dateTime);
}