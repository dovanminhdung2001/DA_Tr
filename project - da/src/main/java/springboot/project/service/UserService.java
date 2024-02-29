package springboot.project.service;

import springboot.project.entity.User;
import springboot.project.model.UserDTO;

public interface UserService {
    User addUser(UserDTO userDTO);

    User findUser(Integer id);

    boolean existsByPhone(String phone);
    User findByPhone(String phone);
}
