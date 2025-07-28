package jp.kobe_u.cs.tennis.tennis.repository;

import jp.kobe_u.cs.tennis.tennis.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

// findByQrCodeValueメソッドが不要になったため、インターフェースの中身は空になります。
// JpaRepositoryを継承するだけで、基本的なCRUD（保存、検索、削除など）操作は可能です。
public interface UserRepository extends JpaRepository<User, Long> {
    // このファイルはこれで完成です
}