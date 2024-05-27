package springboot.project.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import springboot.project.entity.Specialization;

public interface SpecializationRepository extends JpaRepository<Specialization, Integer> {
    Page<Specialization> findAllByActive(Pageable pageable, Boolean active);
}
