package jp.kobe_u.cs.tennis.tennis.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @Column(nullable = false)
    private LocalDateTime attendanceDateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttendanceStatus status;

    public Attendance(User student, Schedule schedule, LocalDateTime attendanceDateTime, AttendanceStatus status) {
        this.student = student;
        this.schedule = schedule;
        this.attendanceDateTime = attendanceDateTime;
        this.status = status;
    }
}