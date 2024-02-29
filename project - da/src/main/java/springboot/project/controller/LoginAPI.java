package springboot.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springboot.project.entity.User;
import springboot.project.model.MessageResponseDTO;
import springboot.project.model.UserDTO;
import springboot.project.service.DoctorUserService;
import springboot.project.service.JwtTokenService;
import springboot.project.service.UserService;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@RestController
@Slf4j
public class LoginAPI {
    @Autowired
    JwtTokenService jwtTokenService;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    DoctorUserService doctorUserService;
    @Autowired
    UserService userService;


    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestParam("phone") String phone,
                        @RequestParam("password") String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(phone, password));
        // tạo ra 1 token trả về client
        User user = userService.findByPhone(phone);

//        if (user.getRole().getId() == 2)
//            return ResponseEntity.ok(new MessageResponseDTO(jwtTokenService.createToken(phone, doctorUserService.f)))

        return ResponseEntity.ok(new MessageResponseDTO(jwtTokenService.createToken(phone), user));
    }

    // đăng kí tài khoản user
    @PostMapping("/api/register/user")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        // kiểm tra phone đã tồn tại hay chưa ?
        if (userService.existsByPhone(userDTO.getPhone())) {
            return ResponseEntity.badRequest().body(new MessageResponseDTO("Error: Phone is already !"));
        }

        if (userDTO.getRole().getId() != 3)
            return ResponseEntity.badRequest().body("Wrong role");

        User user = userService.addUser(userDTO);
        return ResponseEntity.ok(user);
    }

    // đăng ký tài khoản doctor
    @PostMapping("/api/register/doctor")
    public ResponseEntity<?> registerDoctor(@RequestBody UserDTO userDTO) {
        // kiểm tra email đã tồn tại hay chưa ?
        if (userService.existsByPhone(userDTO.getPhone())) {
            return ResponseEntity.badRequest().body(new MessageResponseDTO("Error: Phone is already !"));
        }

        if (userDTO.getRole().getId() != 2)
            return ResponseEntity.badRequest().body(new MessageResponseDTO("Wrong role"));

        Integer createdBy = userDTO.getCreatedBy();

        if (createdBy == null)
            return ResponseEntity.badRequest().body(new MessageResponseDTO("No permission"));

        User admin = userService.findUser(createdBy);

        if (admin == null || admin.getRole().getId() != 1)
            return ResponseEntity.badRequest().body(new MessageResponseDTO("No permission"));



        User user = userService.addUser(userDTO);
        return ResponseEntity.ok(user);
    }
}
