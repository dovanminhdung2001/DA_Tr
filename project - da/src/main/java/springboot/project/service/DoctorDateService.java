package springboot.project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import springboot.project.entity.DoctorDate;
import springboot.project.model.DoctorDateDTO;

public interface DoctorDateService {
    DoctorDate create (DoctorDateDTO dto);
    DoctorDate update (DoctorDateDTO dto);
    Page<DoctorDate> page (Pageable pageable);

    DoctorDate findById(Integer id);

    String delete(Integer id);
//    Boolean delete (Integer id);
}
