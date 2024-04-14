package springboot.project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import springboot.project.entity.Clinic;
import springboot.project.model.ClinicDTO;

public interface ClinicService {
    Clinic create(ClinicDTO dto);
    Clinic update(ClinicDTO dto);
    Clinic getById(Integer id);
    Page<Clinic> page(Pageable pageable);

    Clinic find(Integer id);
}
