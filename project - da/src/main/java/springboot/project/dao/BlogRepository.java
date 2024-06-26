package springboot.project.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import springboot.project.entity.Blog;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
    Page<Blog> findAllByDoctorId(Pageable pageable, Integer doctorId);
    Page<Blog> findAllByDoctorIdAndIsActive(Pageable pageable, Integer doctorId, Boolean isActive);
    Page<Blog> findAllByIsActive(Pageable pageable, Boolean isActive);
    Page<Blog> findAllByTitleContainingIgnoreCaseAndIsActiveTrue(Pageable pageable, String title);
    Page<Blog> findAllBySpecializationIdList(Pageable pageable, String specializationId);
}
