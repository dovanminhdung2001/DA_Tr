package springboot.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.project.entity.Clinic;

public interface ClinicRepository extends JpaRepository<Clinic, Integer> {
    Boolean existsByPhone(String phone);

    Clinic getByPhone(String phone);
}
