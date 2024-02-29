package springboot.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.project.entity.Specialization;

public interface SpecializationRepository extends JpaRepository<Specialization, Integer> {

}
