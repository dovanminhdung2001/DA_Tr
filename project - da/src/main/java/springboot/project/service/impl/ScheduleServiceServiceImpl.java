package springboot.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.project.dao.DateShiftRepository;
import springboot.project.dao.DoctorDateRepository;
import springboot.project.dao.DoctorUserRepository;
import springboot.project.dao.ScheduleRepository;
import springboot.project.entity.DateShift;
import springboot.project.entity.DoctorDate;
import springboot.project.entity.DoctorUser;
import springboot.project.entity.Schedule;
import springboot.project.model.ScheduleDTO;
import springboot.project.model.RegisterDTO;
import springboot.project.model.UserPrincipal;
import springboot.project.service.PatientService;
import springboot.project.service.ScheduleService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ScheduleServiceServiceImpl implements ScheduleService {
    @Autowired
    DoctorUserRepository doctorUserRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    PatientService patientService;
    @Autowired
    DateShiftRepository dateShiftRepository;
    @Autowired
    DoctorDateRepository doctorDateRepository;

    @Override
    public Schedule booking(ScheduleDTO dto) {
        DoctorUser doctorUser = doctorUserRepository.findById(dto.getDoctorId()).get();
        DoctorDate doctorDate = doctorDateRepository.findByDoctorUserAndWorkingDate(doctorUser, dto.getWorkingDate());
        DateShift dateShift = dateShiftRepository.findById(dto.getShiftId()).get();
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();



        if (doctorUser == null)
            throw new RuntimeException("Doctor id  not existed");

        if (dto.getWorkingDate() == null)
            throw new RuntimeException("Empty working date");

        if (dateShift == null)
            throw new RuntimeException("Shift id not existed");

        if (dateShift.getIsActive() == false)
            throw new RuntimeException("Shift registered by another");

        if (dto.getUserPhone() == null
                || dto.getName() == null
                || dto.getGender() == null
                || dto.getCccd() == null
                || dto.getDistrictId() == null
                || dto.getProvinceId() == null
        )  throw new RuntimeException("Some required fields are null");

        Schedule schedule = new Schedule(
                doctorUser,
                dto.getWorkingDate(),
                dto.getShiftId(),
                dateShift.getShiftTime(),
                doctorDate.getExaminationCosts(),
                dto.getUserPhone(),
                dto.getName(),
                dto.getAddress(),
                dto.getGender(),
                dto.getBirthDate(),
                dto.getCccd(),
                dto.getDistrictId(),
                dto.getProvinceId(),
                dto.getCommuneId(),
                dto.getGuardian(),
                dto.getPhoneGuardian(),
                dto.getRelationship(),
                currentUser.getId()
        );

        doctorUser.setNumberChoose(doctorUser.getNumberChoose() + 1);
        dateShift.setIsActive(false);

        dateShiftRepository.save(dateShift);
        doctorUserRepository.save(doctorUser);
        schedule = scheduleRepository.save(schedule);

        return schedule;
    }

    @Override
    public Schedule unBooking(ScheduleDTO dto) {
        Schedule schedule = scheduleRepository.findById(dto.getId()).get();
        DateShift dateShift = dateShiftRepository.findById(schedule.getShiftId()).get();
        DoctorUser doctorUser = doctorUserRepository.findById(schedule.getDoctorUser().getId()).get();

        dateShift.setIsActive(true);
        doctorUser.setNumberChoose(doctorUser.getNumberChoose() - 1);

        schedule.setStatus(0);
        schedule.setDescription(dto.getDescription());

        doctorUserRepository.save(doctorUser);
        dateShiftRepository.save(dateShift);
        scheduleRepository.save(schedule);
        return null;
    }

    @Override
    public Schedule addMakeAnAppointment(RegisterDTO registerDTO) {
//        Schedule schedule = new Schedule(doctorUser, dto.getWorkingDate(), dateShift.getShiftTime(), doctorDate.getExaminationCosts(), dto.getUserPhone());
//        DoctorUser doctorUser = doctorUserRepository.findById(registerDTO.getDoctorUser().getId()).orElse(null);
//        schedule.setDoctorUser(doctorUser);
//
//        schedule.setPathological(registerDTO.getPathological());
//        schedule.setType(registerDTO.getType());
//
//        scheduleRepository.save(schedule);
        return null;
    }

    @Override
    public void deleteMakeAnAppointment(int id) {
        Schedule schedule = scheduleRepository.findById(id).orElse(null);
        scheduleRepository.delete(schedule);
    }

    @Override
    public Page<ScheduleDTO> getListSchedule(Pageable pageable, int id) {
        Page<Schedule> schedulePage = scheduleRepository.getById(pageable, id);
        List<Schedule> schedules = schedulePage.getContent();
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for (Schedule schedule : schedules) {
            scheduleDTOS.add(convert(schedule));
        }
        return new PageImpl<>(scheduleDTOS, schedulePage.getPageable(), schedulePage.getTotalElements());
    }

    @Override
    public Page<ScheduleDTO> getAllByDoctor(Pageable pageable, int doctor_id) {
        Page<Schedule> schedulePage = scheduleRepository.getAllByDoctorUser_Id(pageable, doctor_id);
        List<Schedule> schedules = schedulePage.getContent();
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for (Schedule schedule : schedules) {
            scheduleDTOS.add(convert(schedule));
        }
        return new PageImpl<>(scheduleDTOS, schedulePage.getPageable(), schedulePage.getTotalElements());
    }

    private ScheduleDTO convert(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setDoctorUser(schedule.getDoctorUser());
//        scheduleDTO.setPatient(schedule.getPatient());
        scheduleDTO.setType(schedule.getType());
        return scheduleDTO;
    }
}
