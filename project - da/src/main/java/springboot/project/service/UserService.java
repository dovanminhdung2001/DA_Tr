package springboot.project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import springboot.project.entity.Device;
import springboot.project.entity.User;
import springboot.project.model.UserDTO;

public interface UserService {
    User addUser(UserDTO userDTO);

    User findUser(Integer id);

    boolean existsByPhone(String phone);
    User findByPhone(String phone);

    Page<UserDTO> page(Pageable pageable);

    User findOnlyUser(Integer id);

    User create(UserDTO userDTO);

    User createEmployee(UserDTO userDTO);

    User update(UserDTO dto);

    User saveDevice(Device device);

    Page<User> pageEmployee(Pageable pageable);

    boolean deleteUser(Integer userId);

    boolean deleteEmployee(Integer employeeId);
}
