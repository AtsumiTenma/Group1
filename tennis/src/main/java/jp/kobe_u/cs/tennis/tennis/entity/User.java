package jp.kobe_u.cs.tennis.tennis.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // 生徒のみ
    private Integer remainingTransfers;

    public User(String username, Role role) {
        this.username = username;
        this.role = role;
        if (role == Role.STUDENT) {
            this.remainingTransfers = 10; // デフォルトの振替回数
        }
    }
}
