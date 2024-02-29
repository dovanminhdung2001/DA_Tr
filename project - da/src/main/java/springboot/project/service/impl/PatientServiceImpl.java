package springboot.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.project.dao.PatientRepository;
import springboot.project.entity.Patient;
import springboot.project.entity.Schedule;
import springboot.project.model.PatientDTO;
import springboot.project.model.RegisterDTO;
import springboot.project.model.UserPrincipal;
import springboot.project.service.PatientService;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {
    @Autowired
    PatientRepository patientRepository;

    @Override
    public Patient addPatient(RegisterDTO registerDTO) {
        Patient patient = new Patient();
        patient.setName(registerDTO.getName());
        patient.setBirthDate(registerDTO.getBirthDate());
        patient.setCCCD(registerDTO.getCCCD());
        patient.setAddress(registerDTO.getAddress());
        patientRepository.save(patient);
        return patient;
    }

    @Override
    public PatientDTO getInfo(int id) {
        Patient patient = patientRepository.findById(id).orElse(null);
        PatientDTO patientDTO = convert(patient);
        return patientDTO;
    }

    private PatientDTO convert(Patient patient) {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(patient.getId());
        patientDTO.setName(patient.getName());
        patientDTO.setAddress(patient.getAddress());
        patientDTO.setCccd(patient.getCCCD());
        patientDTO.setBirthDate(patient.getBirthDate());
        Set<String> lists = new HashSet<>();
        for (Schedule schedule : patient.getSchedule()) {
            if (schedule != null){
                lists.add(schedule.getTestResults().getDescription());
            }
        }
        patientDTO.setDescription(lists.toString());
        return patientDTO;
    }
}
