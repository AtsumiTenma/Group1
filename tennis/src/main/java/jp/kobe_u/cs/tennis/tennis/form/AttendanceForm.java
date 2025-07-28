package jp.kobe_u.cs.tennis.tennis.form;

import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class AttendanceForm {
    private String studentName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date attendanceDate;

    private String status;
}
