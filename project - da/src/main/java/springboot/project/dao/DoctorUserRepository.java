package springboot.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import springboot.project.entity.DoctorUser;
import springboot.project.entity.User;

public interface DoctorUserRepository extends JpaRepository<DoctorUser, Integer> {
    DoctorUser findByUser(User user);
}