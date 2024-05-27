package springboot.project.service.impl;

import io.micrometer.common.util.StringUtils;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.project.dao.*;
import springboot.project.entity.*;
import springboot.project.model.DoctorUserDTO;
import springboot.project.model.Index2DTO;
import springboot.project.model.UserPrincipal;
import springboot.project.security.PasswordGenerator;
import springboot.project.service.DoctorUserService;
import springboot.project.utils.Const;
import springboot.project.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DoctorUserServiceimpl implements DoctorUserService {
    private static final Logger log = LoggerFactory.getLogger(DoctorUserServiceimpl.class);
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
    @Autowired
    ScheduleRepository scheduleRepository;

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
    public Page<?> page(Pageable pageable, String name) {

        if (name == null) {
            Page<DoctorUser> doctorUserPage = doctorUserRepository.findAll(pageable);
            Page<DoctorUserDTO> rs = doctorUserPage.map(this::convert);
            return  rs;
        }

        return doctorUserRepository.findAllByUser_NameContainingIgnoreCase(pageable, name);
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
            user,
            dto.getType()
        );

        doctorUser.setExaminationPrice(dto.getExaminationPrice());

        return convert(doctorUserRepository.save(doctorUser));
    }

    @Override
    public DoctorUserDTO findByUser(User user) {
        return convert(doctorUserRepository.findByUser(user));
    }


    @SneakyThrows
    @Override
    public Page<DoctorUser> find(Pageable pageable, DoctorUserDTO dto) {
        Page<DoctorUser> result ;
        Page<DoctorDate> pre;

        if ((dto.getName() != null && !dto.getName().isEmpty()) ) {
            System.out.println("find by name and type");

            result = doctorUserRepository.findAllByUser_NameContainingIgnoreCaseAndTypeAndDoctorDates_WorkingDate(
                    pageable, dto.getName(), dto.getType(), dto.getWorkingDate()
            );
            result = result.map(doctorUser -> {
                doctorUser.getDoctorDates().removeIf(doctorDate -> doctorDate.getWorkingDate().getTime() == dto.getWorkingDate().getTime());
                return doctorUser;
            });

            return  result;
        } else if (dto.getSpecializationId() == null) {
            result  =  doctorUserRepository.findAllByDoctorDates_WorkingDateAndType(pageable, dto.getWorkingDate(), dto.getType());
        } else {
            pre = doctorDateRepository.findAllByWorkingDateAndDoctorUser_TypeAndDoctorUser_Specialization_Id(pageable, dto.getWorkingDate(), dto.getType(), dto.getSpecializationId());
            result = pre.map(DoctorDate::getDoctorUser);
        }

        result = result.map(doctorUser -> {
            DoctorDate doctorDate = new DoctorDate();
            for (DoctorDate date : doctorUser.getDoctorDates())
                if (date.getWorkingDate().getTime() == dto.getWorkingDate().getTime())
                    doctorDate = date;

            doctorUser.setDoctorDates(List.of(doctorDate));

            return doctorUser;
        });

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
        doctorUser.setType(dto.getType());
        doctorUser.setExaminationPrice(dto.getExaminationPrice());
        return doctorUserRepository.save(doctorUser);
    }

    @Override
    public Index2DTO index2(Pageable pageable) {
        Index2DTO dto = new Index2DTO();
        dto.setClinicDoctors(doctorUserRepository.countAllByType(Const.DOCTOR_TYPE_CLINIC));
        dto.setHomeDoctors(doctorUserRepository.countAllByType(Const.DOCTOR_TYPE_HOME));
        dto.setPatients(userRepository.countAllByRole_Id(Const.ROLE_ID_USER));
        dto.setSchedules(scheduleRepository.countAllByStatus(Const.SCHEDULE_STATUS_BOOKED));
        dto.setUsers(userRepository.findAllByRole_Id(pageable, Const.ROLE_ID_USER));
        dto.setDoctorUsers(doctorUserRepository.findAll(pageable));
        return dto;
    }

    @Override
    public boolean delete(Integer doctorId) {
        DoctorUser doctorUser = doctorUserRepository.findById(doctorId).get();

        if (doctorUser == null)
            return false;

        userRepository.delete(doctorUser.getUser());
        List<DoctorDate> list = doctorUser.getDoctorDates();

        if (!list.isEmpty()) {
            list.stream().map(doctorDate -> {
                doctorDate.setDoctorUser(null);
                return doctorDate;
            });
            doctorDateRepository.saveAll(list);
        }

        doctorUserRepository.deleteById(doctorId);
        return true;
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
        doctorUserDTO.setType(doctorUser.getType());
        doctorUserDTO.setExaminationPrice(doctorUser.getExaminationPrice());
        return doctorUserDTO;
    }
}
