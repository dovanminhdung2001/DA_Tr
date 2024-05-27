package springboot.project.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import springboot.project.dao.SpecializationRepository;
import springboot.project.entity.Specialization;
import springboot.project.model.SpecializationDTO;
import springboot.project.service.SpecializationService;

@Service
@Transactional
public class SpecializationServiceImpl implements SpecializationService {
    @Autowired
    private SpecializationRepository specializationRepository;

    // tạo chuyên khoa
    @Override
    public Specialization create(SpecializationDTO dto) {
        if (dto.getId() == null)
            return specializationRepository.save(new Specialization(dto));

        return null;
    }

    // sửa chuyên khoa
    @Override
    public Specialization update(SpecializationDTO dto) {
        if (dto.getId() != null) {
            Specialization specialization = specializationRepository.findById(dto.getId()).get();

            if (specialization == null)
                return null;

            if (dto.getName() != null && !dto.getName().isEmpty())
                specialization.setName(dto.getName());
            if (dto.getDescription() != null && !dto.getDescription().isEmpty())
                specialization.setDescription(dto.getDescription());
            specialization.setActive(dto.getActive());
            return specializationRepository.save(specialization);
        }

        return null;
    }

    // phân trang chuyên khoa
    @Override
    public Page<Specialization> page(Pageable pageable) {
        return specializationRepository.findAll(pageable);
    }

    @Override
    public boolean delete(Integer id) {
        Specialization specialization = specializationRepository.findById(id).get();

        if (specialization == null || specialization.getActive() == false)
            return false;

        specialization.setActive(false);
        specializationRepository.save(specialization);

        return true;
    }

    @Override
    public Page<Specialization> findAllActive(Pageable pageable) {
        return specializationRepository.findAllByActive(pageable, true);
    }
}
