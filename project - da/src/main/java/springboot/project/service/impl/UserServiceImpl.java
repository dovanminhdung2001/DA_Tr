package springboot.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.project.dao.PatientRepository;
import springboot.project.dao.UserRepository;
import springboot.project.entity.Patient;
import springboot.project.entity.User;
import springboot.project.model.UserDTO;
import springboot.project.security.PasswordGenerator;
import springboot.project.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PatientRepository patientRepository;

    @Override
    public User addUser(UserDTO userDTO) {
        User user = new User();

        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(PasswordGenerator.getHashString(userDTO.getPassword()));
        user.setAddress(userDTO.getAddress());
        user.setPhone(userDTO.getPhone());
        user.setAvatar(userDTO.getAvatar());
        user.setGender(userDTO.getGender());
        user.setDescription(userDTO.getDescription());
        user.setRole(userDTO.getRole());
        user.setCccd(userDTO.getCccd());

        user = userRepository.save(user);

        Patient patient = userDTO.getPatient();
        patient.setUser(user);
        patient = patientRepository.save(patient);
        user.setPatient(patient);

        return user;
    }

    @Override
    public User findUser(Integer id) {
        return userRepository.findById(id).get();
    }

    @Override
    public boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }

    @Override
    public User findByPhone(String phone) {
        return userRepository.findUserByPhone(phone);
    }

}
