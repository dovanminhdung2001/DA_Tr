package springboot.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.project.dao.MedicalHistoryRepository;
import springboot.project.entity.MedicalHistory;
import springboot.project.model.MedicalHistoryDTO;
import springboot.project.service.MedicalHistoryService;

@Service
public class MedicalHistoryServiceImpl implements MedicalHistoryService {
    @Autowired
    private MedicalHistoryRepository medicalHistoryRepository;

    @Override
    public MedicalHistory create(MedicalHistoryDTO dto) {
        MedicalHistory medicalHistory = new MedicalHistory();

        medicalHistory.setUser(dto.getUser());
        medicalHistory.setAge(dto.getAge());
        medicalHistory.setResult(dto.getResult());
        medicalHistory.setIsDiabetes(dto.getIsDiabetes());
        medicalHistory.setIsCardiovascularDisease(dto.getIsCardiovascularDisease());
        medicalHistory.setIsLipidMetabolismDisorder(dto.getIsLipidMetabolismDisorder());
        medicalHistory.setIsChronicObstructivePulmonaryDisease(dto.getIsChronicObstructivePulmonaryDisease());
        medicalHistory.setIsChronicBronchitis(dto.getIsChronicBronchitis());
        medicalHistory.setIsCancer(dto.getIsCancer());
        medicalHistory.setIsOtherDiseases(dto.getIsOtherDiseases());
        medicalHistory.setSpecificPathology(dto.getSpecificPathology());
        medicalHistory.setCurrentInUseMedical(dto.getCurrentInUseMedical());
        medicalHistory.setIsFever(dto.getIsFever());
        medicalHistory.setIsCough(dto.getIsCough());
        medicalHistory.setIsChestPain(dto.getIsChestPain());
        medicalHistory.setIsShortnessOfBreath(dto.getIsShortnessOfBreath());
        medicalHistory.setIsSubstanceAbuse(dto.getIsSubstanceAbuse());
        medicalHistory.setNameContact(dto.getNameContact());
        medicalHistory.setPhoneContact(dto.getPhoneContact());

        return medicalHistoryRepository.save(medicalHistory);
    }

    @Override
    public MedicalHistory findById(Integer id) {
        return medicalHistoryRepository.findById(id).get();
    }

    @Override
    public MedicalHistory findByUser_Id(Integer userId) {
        return medicalHistoryRepository.findByUser_Id(userId);
    }

    @Override
    public MedicalHistory update(MedicalHistoryDTO dto) {
        MedicalHistory medicalHistory = medicalHistoryRepository.findById(dto.getId()).get();

        if (medicalHistory == null)
            throw new RuntimeException("Medical history not exist");

        medicalHistory.setAge(dto.getAge());
        medicalHistory.setResult(dto.getResult());
        medicalHistory.setIsDiabetes(dto.getIsDiabetes());
        medicalHistory.setIsCardiovascularDisease(dto.getIsCardiovascularDisease());
        medicalHistory.setIsLipidMetabolismDisorder(dto.getIsLipidMetabolismDisorder());
        medicalHistory.setIsChronicObstructivePulmonaryDisease(dto.getIsChronicObstructivePulmonaryDisease());
        medicalHistory.setIsChronicBronchitis(dto.getIsChronicBronchitis());
        medicalHistory.setIsCancer(dto.getIsCancer());
        medicalHistory.setIsOtherDiseases(dto.getIsOtherDiseases());
        medicalHistory.setSpecificPathology(dto.getSpecificPathology());
        medicalHistory.setCurrentInUseMedical(dto.getCurrentInUseMedical());
        medicalHistory.setIsFever(dto.getIsFever());
        medicalHistory.setIsCough(dto.getIsCough());
        medicalHistory.setIsChestPain(dto.getIsChestPain());
        medicalHistory.setIsShortnessOfBreath(dto.getIsShortnessOfBreath());
        medicalHistory.setIsSubstanceAbuse(dto.getIsSubstanceAbuse());
        medicalHistory.setNameContact(dto.getNameContact());
        medicalHistory.setPhoneContact(dto.getPhoneContact());

        return medicalHistoryRepository.save(medicalHistory);
    }
}
