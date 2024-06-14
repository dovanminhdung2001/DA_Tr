package springboot.project.service.impl;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import springboot.project.dao.DateShiftRepository;
import springboot.project.dao.DoctorDateRepository;
import springboot.project.entity.DateShift;
import springboot.project.entity.DoctorDate;
import springboot.project.model.DateShiftDTO;
import springboot.project.service.DateShiftService;
import springboot.project.utils.AESUtil;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

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

    @Override
    public boolean delete(int dateShiftId) {
        try {
            dateShiftRepository.deleteById(dateShiftId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String createAES(String reqAES) {
        try {
            String dateShiftStr = AESUtil.decrypt(reqAES);
            JSONObject dateShiftJson = new JSONObject(dateShiftStr);

            DoctorDate doctorDate = doctorDateRepository.findById(dateShiftJson.getInt("dateId")).get();

            DateShift dateShiftEntity = dateShiftRepository.save(new DateShift(
                    doctorDate,
                    dateShiftJson.getInt("shiftTime"),
                    true
            ));

            System.out.println(dateShiftEntity);

            return AESUtil.encrypt(dateShiftEntity.toString());
        } catch (Exception e) {
            System.out.println(e);
            return "";
        }
    }
}
