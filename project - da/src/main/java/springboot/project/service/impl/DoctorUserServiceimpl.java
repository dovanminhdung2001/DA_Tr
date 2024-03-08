package springboot.project.service.impl;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.project.dao.*;
import springboot.project.entity.*;
import springboot.project.model.DoctorUserDTO;
import springboot.project.model.UserPrincipal;
import springboot.project.security.PasswordGenerator;
import springboot.project.service.DoctorUserService;
import springboot.project.utils.DateUtils;

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
    @Autowired
    ClinicRepository clinicRepository;
    @Autowired
    RoleRepository roleRepository;

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
        Clinic clinic = clinicRepository.findById(dto.getClinicId()).get();
        Specialization specialization = specializationRepository.findById(dto.getSpecializationId()).get();
        User user = new User(
                dto.getName(),
                dto.getEmail(),
                PasswordGenerator.getHashString(dto.getPassword()),
                dto.getAddress(),
                dto.getPhone(),
                dto.getAvatar(),
                dto.getGender(),
                dto.getDescription(),
                roleRepository.findById(dto.getRoleId()).get(),
                dto.getIsActive(),
                dto.getCccd(),
                dto.getBirthDate()
        );

        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(currentUser);

        user = userRepository.save(user);

        DoctorUser doctorUser = new DoctorUser(
            0,
            clinic,
            specialization,
            user
        );

        return convert(doctorUserRepository.save(doctorUser));
    }

    @Override
    public DoctorUserDTO findById(Integer id) {
        return null;
    }

    @Override
    public DoctorUserDTO findByUser(User user) {
        return convert(doctorUserRepository.findByUser(user));
    }


    @SneakyThrows
    @Override
    public Page<DoctorUser> find(Pageable pageable, DoctorUserDTO dto) {
        if (dto.getSpecializationId() == null) {
            return  doctorUserRepository.findAllByWorkingDate(pageable, dto.getWorkingDate());
        }
        return  doctorUserRepository.findAllByWorkingDateAndSpecialization(pageable, dto.getWorkingDate(), dto.getSpecializationId());
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
        doctorUserDTO.setUser(doctorUser.getUser());
        return doctorUserDTO;
    }
}
