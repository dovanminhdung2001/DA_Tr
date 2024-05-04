package springboot.project.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import springboot.project.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByPhone(String phone);
    boolean existsByPhone(String phone);
    User findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByCccd(String cccd);
    Page<User> findAllByRole_Id(Pageable pageable, Integer roleId);
    User findByIdAndRole_Id(Integer id, Integer roleId);

    Integer countAllByRole_Id(Integer integer);
    Page<User> findAllByRole_IdOrderById(Pageable pageable, Integer roleId);

}