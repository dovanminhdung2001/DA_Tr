package springboot.project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import springboot.project.entity.DoctorUser;
import springboot.project.entity.User;
import springboot.project.model.DoctorUserDTO;

import java.util.List;

public interface DoctorUserService {
    List<DoctorUserDTO> getListDoctorUser();

    Page<DoctorUserDTO> page(Pageable pageable);

    DoctorUserDTO create(DoctorUserDTO dto);
    DoctorUserDTO findById(Integer id);
    DoctorUserDTO findByUser(User user);

    Page<DoctorUser> find(Pageable pageable, DoctorUserDTO dto);

    DoctorUser getById(Integer id);

    DoctorUser update(DoctorUserDTO dto);
}
