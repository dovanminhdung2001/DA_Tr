package springboot.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springboot.project.dao.UserRepository;
import springboot.project.entity.Schedule;
import springboot.project.entity.User;
import springboot.project.model.UserPrincipal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserPrincipalService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    /* Lấy ra user đã đang nhập */
    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        User user = userRepository.findUserByPhone(phone);

        if (user == null){
            throw new UsernameNotFoundException("User not found");
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));

        UserPrincipal accountDTO = new UserPrincipal(user.getPhone(),user.getPassword(),true,
                true,true, true,authorities);
        accountDTO.setId(user.getId());
        accountDTO.setName(user.getName());
        accountDTO.setRoleId(user.getRole());
        accountDTO.setEmail(user.getEmail());
        accountDTO.setAddress(user.getAddress());
        accountDTO.setPhone(user.getPhone());
        accountDTO.setActive(user.isActive());
        accountDTO.setGender(user.getGender());
        accountDTO.setPassword(user.getPassword());
        accountDTO.setCccd(user.getCccd());

        if (null == user.getPatient()){
            return accountDTO;
        } else {
            accountDTO.setHistoryBreath(user.getPatient().getTestResults().getDescription());
        }

        if (user.getPatient().getSchedule() == null){
            return accountDTO;
        } else {
            accountDTO.setSchedules(user.getPatient().getSchedule());
        }

        return accountDTO;
    }
}
