package springboot.project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import springboot.project.entity.Specialization;
import springboot.project.model.SpecializationDTO;

import java.util.List;

public interface SpecializationService {
    public Specialization create (SpecializationDTO dto);

    public Specialization update (SpecializationDTO dto);

    public Page<Specialization> page (Pageable pageable);

    boolean delete(Integer id);

    Page<Specialization> findAllActive(Pageable pageable);
}
