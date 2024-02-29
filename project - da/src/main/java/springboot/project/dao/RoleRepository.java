package springboot.project.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import springboot.project.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
