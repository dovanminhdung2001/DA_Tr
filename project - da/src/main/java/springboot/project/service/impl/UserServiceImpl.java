package springboot.project.service.impl;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.project.dao.*;
import springboot.project.entity.Device;
import springboot.project.entity.MedicalHistory;
import springboot.project.entity.User;
import springboot.project.model.UserDTO;
import springboot.project.model.UserPrincipal;
import springboot.project.security.PasswordGenerator;
import springboot.project.service.UserService;
import springboot.project.utils.Const;
import springboot.project.utils.DateUtils;
import springboot.project.utils.MailUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final DeviceRepo deviceRepo;
    private final RoleRepository roleRepository;
    private final MedicalHistoryRepository medicalHistoryRepository;
    @Override
    public User addUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail()))
            throw new RuntimeException("registered email");

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
        user.setActive(true);
        user = userRepository.save(user);
        user.setBirthDate(userDTO.getBirthDate());
        user.setCreatedDate(DateUtils.now());

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

    @Override
    public Page<UserDTO> page(Pageable pageable) {
        Page<User> userPage = userRepository.findAllByRole_Id(pageable, 3);
        return userPage.map(this::convertToDTO);
    }

    @Override
    public User findOnlyUser(Integer id) {
        return userRepository.findByIdAndRole_Id(id, 3);
    }

    @Override
    public User create(UserDTO dto) {
        if (userRepository.existsByPhone(dto.getPhone()))
            throw new RuntimeException("registered phone");

        if (userRepository.existsByEmail(dto.getEmail()))
            throw new RuntimeException("registered email");

        if (dto.getRoleId() != Const.ROLE_ID_USER)
            throw new RuntimeException("wrong role");

        User user = new User(
                dto.getName(),
                dto.getEmail(),
                PasswordGenerator.getHashString(dto.getPassword()),
                dto.getAddress(),
                dto.getPhone(),
                dto.getAvatar(),
                dto.getGender(),
                dto.getDescription(),
                roleRepository.findById(dto.getRoleId()).get(),
                dto.isActive(),
                dto.getCccd(),
                dto.getBirthDate()
        );

        user.setCreatedDate(DateUtils.now());

        return userRepository.save(user);
    }

    @Override
    public User createEmployee(UserDTO dto) {
        if (userRepository.existsByPhone(dto.getPhone()))
            throw new RuntimeException("registered phone");

        if (userRepository.existsByEmail(dto.getEmail()))
            throw new RuntimeException("registered email");

        if (dto.getRoleId() != Const.ROLE_ID_EMPLOYEE)
            throw new RuntimeException("wrong role");

        User user = new User(
                dto.getName(),
                dto.getEmail(),
                PasswordGenerator.getHashString(dto.getPassword()),
                dto.getAddress(),
                dto.getPhone(),
                dto.getAvatar(),
                dto.getGender(),
                dto.getDescription(),
                roleRepository.findById(dto.getRoleId()).get(),
                dto.isActive(),
                dto.getCccd(),
                dto.getBirthDate()
        );

        user.setCreatedDate(DateUtils.now());

        return userRepository.save(user);
    }

    @Override
    public User update(UserDTO dto) {
        User user = userRepository.findByIdAndRole_Id(dto.getId(), 3);

        if (user == null)
            throw new RuntimeException("not found patient");

        if (!dto.getEmail().equals(user.getEmail()) && userRepository.existsByEmail(dto.getEmail()))
            throw new RuntimeException("registered email");

        if ( !dto.getPhone().equals(user.getPhone())  && userRepository.existsByPhone(dto.getPhone()))
            throw new RuntimeException("registered phone");

        if (!dto.getCccd().equals(user.getCccd()) && userRepository.existsByCccd(dto.getCccd()))
            throw new RuntimeException("registered citizen id");

        if (StringUtils.isNotBlank(dto.getPassword()))
            user.setPassword(PasswordGenerator.getHashString(dto.getPassword()));

        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setGender(dto.getGender());
        user.setPhone(dto.getPhone());
        user.setAvatar(dto.getAvatar());
        user.setAddress(dto.getAddress());
        user.setDescription(dto.getDescription());
        user.setActive(dto.isActive());
        user.setBirthDate(dto.getBirthDate());
        user.setCccd(dto.getCccd());

        return userRepository.save(user);
    }

    @Override
    public String forgotPassword(String email) {
        User user = userRepository.findByEmail(email);

        if (user == null)
            throw new RuntimeException("Mail not found");

        String newPassword = String.valueOf((int) (Math.random() * 1000000000));

        MailUtils.sendEmail(user.getEmail(), "Password reset", "Your new password is:<br>" + newPassword);
        user.setPassword(PasswordGenerator.getHashString(newPassword));
        userRepository.save(user);

        return "mail sent";
    }

    @Override
    public User saveDevice(Device device) {
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        User user = userRepository.findById(currentUser.getId()).get();

        Device check = deviceRepo.findByDeviceId(device.getDeviceId());

        if (check == null) {
            device = deviceRepo.save(device);
        } else {
            check.setFirebaseToken(device.getFirebaseToken());
            device = deviceRepo.save(check);
        }

        user.setDevice(device);
        return userRepository.save(user);
    }

    @Override
    public Page<User> pageEmployee(Pageable pageable) {
        return userRepository.findAllByRole_Id(pageable, Const.ROLE_ID_EMPLOYEE);
    }

    @Override
    public Page<User> pageEmployeeForDoctor(Pageable pageable, String name) {
        if (name != null && !name.isBlank())
            return userRepository.findAllByRole_IdAndIsActiveAndNameContainingIgnoreCase(
                    pageable, Const.ROLE_ID_EMPLOYEE, true, name.trim()
            );

        return userRepository.findAllByRole_IdAndIsActive(pageable, Const.ROLE_ID_EMPLOYEE, true);
    }

    @Override
    public boolean deleteUser(Integer userId) {
        User user = userRepository.findById(userId).get();

        if (user == null || user.getRole().getId() != Const.ROLE_ID_USER)
            return false;

        MedicalHistory medicalHistory = user.getMedicalHistory();

        if (medicalHistory != null) {
            medicalHistory.setUser(null);
            medicalHistoryRepository.save(medicalHistory);
        }

        userRepository.deleteById(userId);

        return true;
    }

    @Override
    public boolean deleteEmployee(Integer employeeId) {
        User user = userRepository.findById(employeeId).get();

        if (user == null || user.getRole().getId() != Const.ROLE_ID_EMPLOYEE)
            return false;

        userRepository.deleteById(employeeId);

        return true;
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO  = new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAddress(),
                user.getPhone(),
                user.getAvatar(),
                DateUtils.getAge(user.getBirthDate())
        );
        userDTO.setMedicalHistory(user.getMedicalHistory());

        return userDTO;
    }
}
