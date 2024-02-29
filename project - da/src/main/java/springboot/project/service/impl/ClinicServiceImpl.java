package springboot.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import springboot.project.dao.ClinicRepository;
import springboot.project.entity.Clinic;
import springboot.project.model.ClinicDTO;
import springboot.project.service.ClinicService;

@Service
public class ClinicServiceImpl implements ClinicService {
    @Autowired
    private ClinicRepository clinicRepository;

    @Override
    public Clinic create(ClinicDTO dto) {
        if (dto.getId() != null)
            throw new RuntimeException("Create clinic id not null");

        if (clinicRepository.existsByPhone(dto.getPhone()))
            throw new RuntimeException("Registered phone");

        return clinicRepository.save(toClinic(dto));
    }


    @Override
    public Clinic update(ClinicDTO dto) {
        if (dto.getId() == null)
            throw new RuntimeException("Create clinic id is null");

        Clinic clinic = clinicRepository.findById(dto.getId()).get();

        if (clinic == null)
            throw new RuntimeException("Not existed id");

        if (!clinic.getPhone().equals(dto.getPhone()) && clinicRepository.existsByPhone(dto.getPhone()))
            throw new RuntimeException("Registered phone");


        clinic.setName(dto.getName());
        clinic.setAddress(dto.getAddress());
        clinic.setAddress(dto.getAddress());
        clinic.setPhone(dto.getPhone());
        clinic.setIntroductionHTML(dto.getIntroductionHTML());
        clinic.setIntroductionMarkdown(dto.getIntroductionMarkdown());
        clinic.setImage(dto.getImage());
        clinic.setWokingTime(dto.getWorkingTime());
//        clinic.setExaminationCosts(dto.getExaminationCosts());
//        clinic.setDoctorUsers();

        return clinicRepository.save(clinic);
    }

    @Override
    public Clinic getById(Integer id) {
        return clinicRepository.findById(id).get();
    }

    @Override
    public Page<Clinic> page(Pageable pageable) {
        return null;
    }

    private Clinic toClinic(ClinicDTO dto) {
        return  new Clinic(
                dto.getName(),
                dto.getAddress(),
                dto.getPhone(),
                dto.getIntroductionHTML(),
                dto.getIntroductionMarkdown(),
                dto.getDescription(),
                dto.getImage(),
                dto.getWorkingTime(),
                dto.getExaminationCosts(),
                dto.getDoctorUserList(),
                dto.getCreatedAt()
        );
    }
}
