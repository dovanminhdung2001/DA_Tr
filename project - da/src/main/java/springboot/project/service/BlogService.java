package springboot.project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import springboot.project.entity.Blog;
import springboot.project.model.BlogDTO;

public interface BlogService {
    Blog create (BlogDTO dto);
    Blog update (BlogDTO dto);
    Blog findById (Integer blogId);
    Page<Blog> findAllByDoctorId(Pageable pageable, Integer doctorId, Boolean isActive);
    Page<Blog> findAll(Pageable pageable, Boolean isActive);

    Blog delete(Integer id, Boolean status);
}
