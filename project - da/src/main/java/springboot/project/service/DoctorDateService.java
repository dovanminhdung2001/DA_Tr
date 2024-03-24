package springboot.project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import springboot.project.entity.DoctorDate;
import springboot.project.model.DoctorDateDTO;

import java.util.Date;
import java.util.List;

public interface DoctorDateService {
    DoctorDate create (DoctorDateDTO dto);
    DoctorDate update (DoctorDateDTO dto);
    Page<DoctorDate> page (Pageable pageable);

    DoctorDate findById(Integer id);

    String delete(Integer id);

    List<DoctorDate> findByDate(Pageable pageable, Date date);
//    Boolean delete (Integer id);
}
