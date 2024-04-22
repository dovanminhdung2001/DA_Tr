package springboot.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.project.entity.MedicalHistory;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Integer> {
    MedicalHistory findByUser_Id(Integer id);
}
