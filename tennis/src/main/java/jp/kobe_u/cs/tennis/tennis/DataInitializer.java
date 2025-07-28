package jp.kobe_u.cs.tennis.tennis;

import jp.kobe_u.cs.tennis.tennis.entity.Role;
import jp.kobe_u.cs.tennis.tennis.entity.Schedule;
import jp.kobe_u.cs.tennis.tennis.entity.User;
import jp.kobe_u.cs.tennis.tennis.repository.ScheduleRepository;
import jp.kobe_u.cs.tennis.tennis.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
    public void run(String... args) throws Exception {
        // DBにデータがなければ初期データを投入
        if (userRepository.count() > 0) {
            return;
        }

        System.out.println("--- テストデータの初期化を開始します ---");

        // ユーザー作成
        User student1 = userRepository.save(new User("田中 太郎", Role.STUDENT));
        User student2 = userRepository.save(new User("鈴木 花子", Role.STUDENT));
        User coach1 = userRepository.save(new User("佐藤 コーチ", Role.COACH));

        // スケジュール作成
        // 1. 動作確認しやすいように、必ず「現在時刻」が含まれるスケジュールを作成
        DayOfWeek today = LocalDateTime.now().getDayOfWeek();
        LocalTime now = LocalTime.now();
        Schedule activeSchedule = new Schedule(
                today,
                now.minusHours(1), // 現在時刻の1時間前
                now.plusHours(1),  // 現在時刻の1時間後
                coach1
        );
        activeSchedule.setStudents(Set.of(student1, student2));
        scheduleRepository.save(activeSchedule);

        // 2. その他の曜日のスケジュール
        Schedule otherSchedule = new Schedule(
                today.plus(2), // 2日後の曜日
                LocalTime.of(18, 30),
                LocalTime.of(19, 30),
                coach1
        );
        otherSchedule.setStudents(Set.of(student1));
        scheduleRepository.save(otherSchedule);
        
        System.out.println("--- テストデータの初期化が完了しました ---");
    }
}