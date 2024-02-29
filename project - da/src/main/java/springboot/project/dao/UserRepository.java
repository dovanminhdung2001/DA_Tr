package springboot.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.project.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByPhone(String phone);

    User findByEmail(String email);

    boolean existsByPhone(String phone);
}