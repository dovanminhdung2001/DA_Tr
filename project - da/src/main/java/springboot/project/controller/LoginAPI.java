package springboot.project.controller;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import springboot.project.dao.PatientRepository;
import springboot.project.entity.Patient;
import springboot.project.entity.User;
import springboot.project.model.JwtResponseDTO;
import springboot.project.model.MessageResponseDTO;
import springboot.project.model.UserDTO;
import springboot.project.service.DoctorUserService;
import springboot.project.service.JwtTokenService;
import springboot.project.service.PatientService;
import springboot.project.service.UserService;
import springboot.project.utils.Const;

@CrossOrigin(origins = "*")
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
    @Autowired
    PatientRepository patientRepository;

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestParam("phone") String phone,
                        @RequestParam("password") String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(phone, password));
            // tạo ra 1 token trả về client
            User user = userService.findByPhone(phone);

            String accessToken = jwtTokenService.createToken(phone);
            String refreshToken = jwtTokenService.createRefreshToken(phone);

            if (user.getRole().getId() == Const.ROLE_ID_DOCTOR)
                return ResponseEntity.ok(new JwtResponseDTO(accessToken, refreshToken, doctorUserService.findByUser(user)));

            return ResponseEntity.ok(new JwtResponseDTO(accessToken, refreshToken, user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponseDTO("Wrong phone or password", e));
        }
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
//        Patient patient = new Patient();
//        patient.setUser(user);
//        patient.setAddress(userDTO.getAddress());
//        patient.setName(user.getName());
//        patientRepository.save(patient);

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

    @PostMapping("/api/refresh")
    public ResponseEntity<?> refreshToken(
            @RequestParam String accessToken,
            @RequestParam String refreshToken
    ) {
        try {
            if (jwtTokenService.validateRefreshToken(refreshToken)) {
                String email = jwtTokenService.getEmailFromJwt(accessToken);

                String a = jwtTokenService.createToken(email);
                String r = jwtTokenService.createRefreshToken(email);

                return ResponseEntity.ok(new JwtResponseDTO(a, r));
            }
        }  catch (ExpiredJwtException ex){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.noContent().build();
    }
}
