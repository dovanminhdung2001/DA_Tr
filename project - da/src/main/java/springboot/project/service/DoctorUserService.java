package springboot.project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import springboot.project.entity.DoctorUser;
import springboot.project.entity.User;
import springboot.project.model.DoctorUserDTO;
import springboot.project.model.Index2DTO;

import java.text.ParseException;
import java.util.List;

public interface DoctorUserService {
    List<DoctorUserDTO> getListDoctorUser();

    Page<?> page(Pageable pageable, String name);

    DoctorUserDTO create(DoctorUserDTO dto);
    DoctorUserDTO findByUser(User user);

    Page<DoctorUser> find(Pageable pageable, DoctorUserDTO dto);

    DoctorUser getById(Integer id);

    DoctorUser update(DoctorUserDTO dto);

    Index2DTO index2(Pageable pageable) throws ParseException;

    boolean delete(Integer doctorId);
}
