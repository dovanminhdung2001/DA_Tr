package springboot.project.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import springboot.project.entity.User;

import java.util.Date;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByPhone(String phone);
    boolean existsByPhone(String phone);
    User findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByCccd(String cccd);
    boolean existsByIdAndRole_Id(Integer id, Integer roleId);

    Page<User> findAllByRole_Id(Pageable pageable, Integer roleId);
    Page<User> findAllByRole_IdAndIsActive(Pageable pageable, Integer roleId, Boolean active);
    Page<User> findAllByRole_IdAndIsActiveAndNameContainingIgnoreCase(Pageable pageable, Integer roleId, Boolean active,String name);
    User findByIdAndRole_Id(Integer id, Integer roleId);

    Integer countAllByRole_Id(Integer integer);
    Page<User> findAllByRole_IdOrderById(Pageable pageable, Integer roleId);

    @Query(value = "SELECT DATE_FORMAT(created_date, '%b-%Y') AS month, COUNT(*) " +
            "FROM users " +
            "WHERE role_id = 3 AND created_date > :startDate " +
            "GROUP BY DATE_FORMAT(created_date, '%b-%Y')", nativeQuery = true)
    List<Object[]> countUsersGroupedByMonthNative(@Param("startDate") Date startDate);
}