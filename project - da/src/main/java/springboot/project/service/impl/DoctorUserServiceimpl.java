package springboot.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.project.dao.DoctorUserRepository;
import springboot.project.dao.SpecializationRepository;
import springboot.project.dao.UserRepository;
import springboot.project.entity.DoctorUser;
import springboot.project.entity.Role;
import springboot.project.entity.User;
import springboot.project.model.DoctorUserDTO;
import springboot.project.service.DoctorUserService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DoctorUserServiceimpl implements DoctorUserService {
    @Autowired
    DoctorUserRepository doctorUserRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SpecializationRepository specializationRepository;

    @Override
    public List<DoctorUserDTO> getListDoctorUser() {
        List<DoctorUser> doctorUsers = doctorUserRepository.findAll();
        List<DoctorUserDTO> doctorUserDTOS = new ArrayList<>();
        for (DoctorUser doctorUser : doctorUsers) {
            doctorUserDTOS.add(convert(doctorUser));
        }
        return doctorUserDTOS;
    }

    @Override
    public Page<DoctorUserDTO> page(Pageable pageable) {
        Page<DoctorUser> doctorUserPage = doctorUserRepository.findAll(pageable);

        return doctorUserPage.map(this::convert);
    }

    @Override
    public DoctorUserDTO create(DoctorUserDTO dto) {
        User user = new User(
                dto.getName(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getAddress(),
                dto.getPhone(),
                dto.getAvatar(),
                dto.getGender(),
                dto.getDescription(),
                new Role(dto.getRoleId()),
                dto.getIsActive(),
                dto.getCccd(),
                dto.getBirthDate()
        );

        user = userRepository.save(user);

        DoctorUser doctorUser = new DoctorUser(

        );
        return null;
    }

    @Override
    public DoctorUserDTO findById(Integer id) {
        return null;
    }

    @Override
    public DoctorUserDTO findByUser(User user) {
//        return doctorUserRepository.findByUser(user);
        return  null;
    }

    private DoctorUserDTO convert(DoctorUser doctorUser) {
        DoctorUserDTO doctorUserDTO = new DoctorUserDTO();
        doctorUserDTO.setId(doctorUser.getId());
        doctorUserDTO.setName(doctorUser.getUser().getName());
        doctorUserDTO.setGeneralIntroduction(doctorUser.getGeneralIntroduction());
        doctorUserDTO.setTrainingProcess(doctorUser.getTrainingProcess());
        doctorUserDTO.setAchievementsAchieved(doctorUser.getAchievementsAchieved());
        doctorUserDTO.setSpecialtiesInCharge(doctorUser.getSpecialtiesInCharge());
        doctorUserDTO.setClinic(doctorUser.getClinic());
        doctorUserDTO.setSpecialization(doctorUser.getSpecialization());
        return doctorUserDTO;
    }
}
