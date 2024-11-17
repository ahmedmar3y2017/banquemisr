package banquemisr.challenge05.services;

import banquemisr.challenge05.defines.Defines;
import banquemisr.challenge05.dtos.RegisterUserDto;
import banquemisr.challenge05.models.Role;
import banquemisr.challenge05.models.RoleEnum;
import banquemisr.challenge05.models.User;
import banquemisr.challenge05.repository.RoleRepository;
import banquemisr.challenge05.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<User> allUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public User createAdministrator(RegisterUserDto input) {

        Optional<Role> byName = roleRepository.findByName(RoleEnum.ADMIN);

        if (byName.isEmpty()) {
            return null;
        }

        User user = new User();
        user.setEMAIL(input.getEmail());
        user.setFULLNAME(input.getFullName());
        user.setPASSWORD(passwordEncoder.encode(input.getPassword()));
        user.setRole(byName.get());

        return userRepository.save(user);

    }

    @Override
    public User createSuperAdministrator(RegisterUserDto input) {

        Optional<Role> byName = roleRepository.findByName(RoleEnum.SUPER_ADMIN);

        if (byName.isEmpty()) {
            return null;
        }

        User user = new User();
        user.setEMAIL(input.getEmail());
        user.setFULLNAME(input.getFullName());
        user.setPASSWORD(passwordEncoder.encode(input.getPassword()));
        user.setRole(byName.get());

        return userRepository.save(user);

    }

    @Override
    public User findByEMAIL(String email) {
        return userRepository.findByEMAIL(email).
                orElseThrow(() -> new UsernameNotFoundException(Defines.SECURITY_USER_NOT_FOUND));

    }
}
