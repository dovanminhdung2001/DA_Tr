package springboot.project.service;

import springboot.project.entity.Patient;
import springboot.project.model.PatientDTO;
import springboot.project.model.RegisterDTO;

public interface PatientService {
    Patient addPatient(RegisterDTO registerDTO);


    PatientDTO getInfo(int id);
}
