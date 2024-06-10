package springboot.project.service.impl;

import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.project.dao.*;
import springboot.project.entity.*;
import springboot.project.model.NotificationRequest;
import springboot.project.model.ScheduleDTO;
import springboot.project.model.RegisterDTO;
import springboot.project.model.UserPrincipal;
import springboot.project.service.FcmService;
import springboot.project.service.ScheduleService;
import springboot.project.utils.Const;
import springboot.project.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleServiceServiceImpl implements ScheduleService {
    private final DoctorUserRepository doctorUserRepository;
    private final ScheduleRepository scheduleRepository;
    private final DateShiftRepository dateShiftRepository;
    private final DoctorDateRepository doctorDateRepository;
    private final UserRepository userRepository;
    private final FcmService fcmService;

    @Override
    public ScheduleDTO booking(ScheduleDTO dto) throws FirebaseMessagingException {
        DoctorUser doctorUser = doctorUserRepository.findById(dto.getDoctorId()).get();
        DoctorDate doctorDate = doctorDateRepository.findByDoctorUserAndWorkingDate(doctorUser, dto.getWorkingDate());
        DateShift dateShift = dateShiftRepository.findById(dto.getShiftId()).get();
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userRepository.findById(currentUser.getId()).get();

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
        ) throw new RuntimeException("Some required fields are null");

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
                currentUser.getId(),
                Const.SCHEDULE_STATUS_BOOKED,
                dto.getPathological(),
                dto.getSpecializationId()
        );

        schedule.setType(doctorUser.getType());

        doctorUser.setNumberChoose(doctorUser.getNumberChoose() + 1);
        dateShift.setIsActive(false);

        dateShiftRepository.save(dateShift);
        doctorUserRepository.save(doctorUser);
        schedule = scheduleRepository.save(schedule);

        sendNotify(doctorUser, user, schedule);

        return convert(schedule);
    }

    @Override
    public ScheduleDTO unBooking(ScheduleDTO dto) {
        Schedule schedule = scheduleRepository.findById(dto.getId()).get();
        DateShift dateShift = dateShiftRepository.findById(schedule.getShiftId()).get();
        DoctorUser doctorUser = doctorUserRepository.findById(schedule.getDoctorUser().getId()).get();

        dateShift.setIsActive(true);
        doctorUser.setNumberChoose(doctorUser.getNumberChoose() - 1);

        schedule.setStatus(Const.SCHEDULE_STATUS_CANCELLED);
        schedule.setDescription(dto.getDescription());

        doctorUserRepository.save(doctorUser);
        dateShiftRepository.save(dateShift);
        schedule = scheduleRepository.save(schedule);
        return convert(schedule);
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
        Date toDay = DateUtils.today();
        Date past = new Date(toDay.getTime());
        Date later3Day = new Date(toDay.getTime() + DateUtils.daysToTime(3));
        Page<Schedule> schedulePage = scheduleRepository.getByIdAndDateRange(pageable, id, past, later3Day);
        List<Schedule> schedules = schedulePage.getContent();
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for (Schedule schedule : schedules) {
            scheduleDTOS.add(convert(schedule));
        }
        return new PageImpl<>(scheduleDTOS, schedulePage.getPageable(), schedulePage.getTotalElements());
    }

    @Override
    public Page<ScheduleDTO> getAllByDoctor(Pageable pageable, int doctor_id) {
        Date toDay = DateUtils.today();
        Date past = new Date(toDay.getTime());
        Date later3Day = new Date(toDay.getTime() + DateUtils.hoursToTime(60));
        Page<Schedule> schedulePage = scheduleRepository.getAllByDoctorUser_IdAndDateBetween(
                pageable, doctor_id, past, later3Day);
        List<Schedule> schedules = schedulePage.getContent();
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for (Schedule schedule : schedules) {
            scheduleDTOS.add(convert(schedule));
        }
        return new PageImpl<>(scheduleDTOS, schedulePage.getPageable(), schedulePage.getTotalElements());
    }

    @Override
    public Page<ScheduleDTO> getAllForUserInFuture(Pageable pageable, Integer id, Integer recent, Date date, Integer type) {
        Page<Schedule> repo;

        if (recent != null)
            repo = scheduleRepository.getAllForUserIn3NextDays(pageable, id, type);
        else if (date != null)
            repo = scheduleRepository.getAllForUserAt(pageable, id, date, type);
        else repo = scheduleRepository.getAllForUserInFuture(pageable, id, type);

        List<Schedule> data = repo.getContent();
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for (Schedule schedule : data) {
            scheduleDTOS.add(convert(schedule));
        }
        return new PageImpl<>(scheduleDTOS, repo.getPageable(), repo.getTotalElements());
    }

    @Override
    public Page<ScheduleDTO> getAllForUserInPast(Pageable pageable, Integer status, Date date, Integer type) {
        int userId = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        Page<Schedule> repo;

        if (status != null && date != null)
            repo = scheduleRepository.getPageForUserInPastByTypeAt(pageable, userId, status, date, type);
        else if (status != null)
            repo = scheduleRepository.getPageForUserInPastByType(pageable, userId, status, type);
        else repo = scheduleRepository.getAllForUserInPast(pageable, userId, type);

        List<Schedule> data = repo.getContent();
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for (Schedule schedule : data) {
            ScheduleDTO scheduleDTO = convert(schedule);
            if (schedule.getStatus() == Const.SCHEDULE_STATUS_BOOKED)
                scheduleDTO.setTestResults(null);

            scheduleDTOS.add(scheduleDTO);
        }
        return new PageImpl<>(scheduleDTOS, repo.getPageable(), repo.getTotalElements());
    }

    public Page<ScheduleDTO> getAllForDoctorInFuture(Pageable pageable) {
        Integer doctorId = doctorUserRepository.findByUser_Id(
                ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()
        ).getId();

        Page<Schedule> repo = scheduleRepository.getAllForDoctorInFuture(pageable, doctorId);
        List<Schedule> data = repo.getContent();
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for (Schedule schedule : data) {
            scheduleDTOS.add(convert(schedule));
        }
        return new PageImpl<>(scheduleDTOS, repo.getPageable(), repo.getTotalElements());
    }

    @Override
    public Page<ScheduleDTO> getAllForDoctorInPast(Pageable pageable, Integer status, Date date, Integer type) {
        Integer doctorId = doctorUserRepository.findByUser_Id(
                ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()
        ).getId();
        Page<Schedule> repo;

        if (type != null )
            if (date != null)
                repo = scheduleRepository.findAllByDateBeforeAndTestResults_Status(pageable, date, type);
            else
                repo = scheduleRepository.findAllByTestResults_Status(pageable, type);
        else if (status != null && date != null)
            repo = scheduleRepository.getPageForDoctorInPastByTypeAt(pageable, doctorId, status, date);
        else if (status != null)
            repo = scheduleRepository.getPageForDoctorInPastByType(pageable, doctorId, status);
        else repo = scheduleRepository.getAllForDoctorInPast(pageable, doctorId);

        List<Schedule> data = repo.getContent();
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for (Schedule schedule : data) {
            scheduleDTOS.add(convert(schedule));
        }
        return new PageImpl<>(scheduleDTOS, repo.getPageable(), repo.getTotalElements());
    }

    @Override
    public List<Integer> scheduleDay(ScheduleDTO atClinic) {
        Integer userId = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        List<Schedule> schedules = scheduleRepository.findAllByDateAndCreatedByAndStatusInAndType(
                atClinic.getWorkingDate(),
                userId,
                List.of(Const.SCHEDULE_STATUS_BOOKED, Const.SCHEDULE_STATUS_RESULTED),
                atClinic.getType()
        );
        List<Integer> shiftToday = schedules.stream().map(Schedule::getTime).collect(Collectors.toList());

        return shiftToday;
    }

    @Override
    public Page<Schedule> getAllUnassigned(Pageable pageable, Integer doctorId) {
        return scheduleRepository.findAllByDoctorUser_IdAndAssignedToAndStatus(
                pageable, doctorId, null, Const.SCHEDULE_STATUS_BOOKED
        );
    }

    @Override
    public String assign(Integer employeeId, Integer scheduleId) {
        User user = userRepository.findByIdAndRole_IdAndIsActive(employeeId, Const.ROLE_ID_EMPLOYEE, true);

        if (user == null)
            throw new RuntimeException("Employee not found");

        Schedule schedule = scheduleRepository.findByIdAndStatus(scheduleId, Const.SCHEDULE_STATUS_BOOKED);

        if (schedule == null)
            throw new RuntimeException("Schedule not found  or cancelled or resulted");

        schedule.setAssignedTo(user.getId());
        scheduleRepository.save(schedule);

        return "Assign success";
    }

    private ScheduleDTO convert(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setDoctorId(schedule.getDoctorUser().getId());
        scheduleDTO.setWorkingDate(schedule.getDate());
        scheduleDTO.setTime(schedule.getTime());
        scheduleDTO.setShiftId(schedule.getShiftId());
        scheduleDTO.setExaminationPrice(schedule.getExaminationPrice().toString());
        scheduleDTO.setUserPhone(schedule.getUserPhone());
        scheduleDTO.setStatus(schedule.getStatus());
        scheduleDTO.setType(schedule.getType());
        scheduleDTO.setPathological(schedule.getPathological());
        scheduleDTO.setTestResults(schedule.getTestResults());
        scheduleDTO.setCreatedAt(schedule.getCreatedAt());
        scheduleDTO.setUpdateAt(schedule.getUpdatedAt());
        scheduleDTO.setDeleteAt(schedule.getDeletedAt());
        scheduleDTO.setDescription(schedule.getDescription());
        scheduleDTO.setName(schedule.getName());
        scheduleDTO.setAddress(schedule.getAddress());
        scheduleDTO.setGender(schedule.getGender());
        scheduleDTO.setBirthDate(schedule.getBirthDate());
        scheduleDTO.setCccd(schedule.getCccd());
        scheduleDTO.setDistrictId(schedule.getDistrictId());
        scheduleDTO.setProvinceId(schedule.getProvinceId());
        scheduleDTO.setCommuneId(schedule.getCommuneId());
        scheduleDTO.setGuardian(schedule.getGuardian());
        scheduleDTO.setPhoneGuardian(schedule.getPhoneGuardian());
        scheduleDTO.setRelationship(schedule.getRelationship());
        scheduleDTO.setCreatedBy(schedule.getCreatedBy());
        scheduleDTO.setSpecializationId(schedule.getSpecializationId());
//        scheduleDTO.setDoctorUser(schedule.getDoctorUser());
        scheduleDTO.setDate(DateUtils.sdf.format(schedule.getDate()));
        scheduleDTO.setDoctorName(schedule.getDoctorUser().getUser().getName());
        scheduleDTO.setAssignedTo(schedule.getAssignedTo());
        return scheduleDTO;
    }

    private void sendNotify(DoctorUser doctorUser, User sender, Schedule schedule) throws FirebaseMessagingException {
        User receiver = doctorUser.getUser();
        DateShift shift = dateShiftRepository.findById(schedule.getShiftId()).get();

        if (!receiver.getDevice().getFirebaseToken().isEmpty()) {
            NotificationRequest notificationRequest = new NotificationRequest();

            notificationRequest.setBody(DateUtils.sdf.format(schedule.getDate()));
            notificationRequest.setTitle("Bạn có lịch khám mới");
            notificationRequest.getData().put("sender", sender.getName());
            notificationRequest.getData().put("date", DateUtils.sdf.format(schedule.getDate()));
            notificationRequest.getData().put("shift", shift.getShiftTime().toString());
            notificationRequest.getData().put("type", schedule.getType().toString());
            notificationRequest.setTargetToken(receiver.getDevice().getFirebaseToken());

            fcmService.sendNotification(notificationRequest);
        }
    }
}
