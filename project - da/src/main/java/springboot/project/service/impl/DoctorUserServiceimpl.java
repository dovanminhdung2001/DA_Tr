package springboot.project.service.impl;

import io.micrometer.common.util.StringUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import java.util.Date;
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
    @Autowired
    DoctorDateRepository doctorDateRepository;

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

        Page<DoctorUserDTO> rs = doctorUserPage.map(this::convert);
        return  rs;
    }

    @Override
    public DoctorUserDTO create(DoctorUserDTO dto) {
        Clinic clinic = clinicRepository.findById(dto.getClinicId()).get();
        Specialization specialization = specializationRepository.findById(dto.getSpecializationId()).get();

        if(doctorUserRepository.findByUser_Phone(dto.getPhone()) != null)
            throw new RuntimeException("regitered phone");
        if(doctorUserRepository.findByUser_Email(dto.getEmail()) != null)
            throw new RuntimeException("regitered email");
        if(doctorUserRepository.findByUser_Cccd(dto.getCccd()) != null)
            throw new RuntimeException("Registered citizen id");

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
        Page<DoctorUser> result ;

        if (dto.getSpecializationId() == null) {
            result =  doctorUserRepository.findAllByWorkingDate(pageable, dto.getWorkingDate());
        } else {
            result = doctorUserRepository.findAllByWorkingDateAndSpecialization(pageable, dto.getWorkingDate(), dto.getSpecializationId());
        }

        return result;
    }

    @Override
    public DoctorUser getById(Integer id) {
        return doctorUserRepository.findById(id).get();
    }

    @Override
    public DoctorUser update(DoctorUserDTO dto) {
        DoctorUser doctorUser = doctorUserRepository.findById(dto.getId()).get();
        DoctorUser check = doctorUserRepository.findByUser_Email(dto.getEmail());

        if (doctorUser == null)
            throw new RuntimeException("not found doctor");

        if (check != null && check.getId() != doctorUser.getId())
            throw new RuntimeException("registered email");

        check = doctorUserRepository.findByUser_Phone(dto.getPhone());
        if (check != null && check.getId() != doctorUser.getId())
            throw new RuntimeException("registered phone");

        check = doctorUserRepository.findByUser_Cccd(dto.getCccd()) ;
        if (check != null && check.getId() != doctorUser.getId())
            throw new RuntimeException("regitered citizen id");

        if (StringUtils.isNotBlank(dto.getPassword())) {
            doctorUser.getUser().setPassword(PasswordGenerator.getHashString(dto.getPassword()));
        }

        doctorUser.getUser().setEmail(dto.getEmail());
        doctorUser.getUser().setName(dto.getName());
        doctorUser.getUser().setGender(dto.getGender());
        doctorUser.getUser().setPhone(dto.getPhone());
        doctorUser.getUser().setAvatar(dto.getAvatar());
        doctorUser.getUser().setAddress(dto.getAddress());
        doctorUser.getUser().setDescription(dto.getDescription());
        doctorUser.getUser().setActive(dto.getActive());
        doctorUser.getUser().setBirthDate(dto.getBirthDate());
        doctorUser.getUser().setCccd(dto.getCccd());
        return doctorUserRepository.save(doctorUser);
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
        doctorUserDTO.setCccd(doctorUser.getUser().getCccd());
        return doctorUserDTO;
    }
}
