package springboot.project.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import springboot.project.dao.DoctorDateRepository;
import springboot.project.dao.DoctorUserRepository;
import springboot.project.entity.DoctorDate;
import springboot.project.entity.DoctorUser;
import springboot.project.model.DoctorDateDTO;
import springboot.project.model.UserPrincipal;
import springboot.project.service.DoctorDateService;
import springboot.project.utils.DateUtils;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class DoctorDateServiceImpl implements DoctorDateService {
    @Autowired
    private DoctorDateRepository doctorDateRepository;
    @Autowired
    private DoctorUserRepository doctorUserRepository;

    @Override
    public DoctorDate create(DoctorDateDTO dto) {
        if (dto.getCost() == null)
            throw new RuntimeException("Cost is null");

        if(doctorDateRepository.existsDoctorDateByWorkingDateAndDoctorUser_Id(dto.getDate(), dto.getDoctorId()))
            throw new RuntimeException("Date registered");

        Integer id = dto.getDoctorId();
        DoctorUser doctorUser = doctorUserRepository.findById(id).get();

        return doctorDateRepository.save(new DoctorDate(doctorUser, dto.getDate(), dto.getCost()));
    }

    @Override
    public DoctorDate update(DoctorDateDTO dto) {
        DoctorDate doctorDate = doctorDateRepository.findById(dto.getId()).get();

        if (doctorDate == null)
            throw new RuntimeException("Id not existed");

//        doctorDate.setWorkingDate(dto.getDate());
        doctorDate.setExaminationCosts(dto.getCost());

        return doctorDateRepository.save(doctorDate);
    }

    @Override
    public Page<DoctorDate> page(Pageable pageable) {
        return doctorDateRepository.findAll(pageable);
    }

    @Override
    public DoctorDate findById(Integer id) {
        return doctorDateRepository.findById(id).get();
    }

    @Override
    public String delete(Integer id) {
        DoctorDate doctorDate = doctorDateRepository.findById(id).get();

        if(doctorDate == null)
            throw new RuntimeException("Id not existed");

        if(doctorDate.getDateShifts().size() > 0)
            throw new RuntimeException("Working date has existed shift");

        doctorDateRepository.delete(doctorDate);
        return "Delete success!";
    }

    @Override
    public List<DoctorDate> findByDate(Pageable pageable, Date date) {
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        DoctorUser doctorUser = doctorUserRepository.findByUser_Id(currentUser.getId());

        return doctorDateRepository.findAllByWorkingDateAndDoctorUser_Id(date, doctorUser.getId());
    }
}
