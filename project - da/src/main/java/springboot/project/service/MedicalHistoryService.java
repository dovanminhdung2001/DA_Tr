package springboot.project.service;

import springboot.project.entity.MedicalHistory;
import springboot.project.model.MedicalHistoryDTO;

public interface MedicalHistoryService {
    MedicalHistory create(MedicalHistoryDTO dto);
    MedicalHistory findById(Integer id);
    MedicalHistory findByUser_Id(Integer userId);
    MedicalHistory update(MedicalHistoryDTO dto);
}
