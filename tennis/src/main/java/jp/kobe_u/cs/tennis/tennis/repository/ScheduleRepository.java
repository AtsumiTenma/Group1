package jp.kobe_u.cs.tennis.tennis.repository;

import jp.kobe_u.cs.tennis.tennis.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByDayOfWeekAndStartTimeBeforeAndEndTimeAfter(DayOfWeek dayOfWeek, LocalTime time1, LocalTime time2);
}