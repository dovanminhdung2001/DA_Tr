package springboot.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import springboot.project.dao.DateShiftRepository;
import springboot.project.dao.DoctorDateRepository;
import springboot.project.entity.DateShift;
import springboot.project.entity.DoctorDate;
import springboot.project.model.DateShiftDTO;
import springboot.project.service.DateShiftService;

@Service
public class DateShiftServiceImpl implements DateShiftService {
    @Autowired
    private DateShiftRepository dateShiftRepository;
    @Autowired
    private DoctorDateRepository doctorDateRepository;
    @Override
    public DateShift create(DateShiftDTO dto) {
        DoctorDate doctorDate = doctorDateRepository.findById(dto.getDateId()).get();

        return  dateShiftRepository.save(new DateShift(
                doctorDate,
                dto.getShiftTime(),
                true
        ));
    }

    @Override
    public DateShift update(DateShiftDTO dto) {
        return null;
    }

    @Override
    public Page<DateShift> page(Pageable pageable) {
        return dateShiftRepository.findAll(pageable);
    }
}
