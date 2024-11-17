package banquemisr.challenge05.services;

import banquemisr.challenge05.defines.Defines;
import banquemisr.challenge05.dtos.LoginUserDto;
import banquemisr.challenge05.dtos.RegisterUserDto;
import banquemisr.challenge05.models.Role;
import banquemisr.challenge05.models.RoleEnum;
import banquemisr.challenge05.models.User;
import banquemisr.challenge05.repository.RoleRepository;
import banquemisr.challenge05.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    public User signup(RegisterUserDto input) {
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);

        if (optionalRole.isEmpty()) {
            return null;
        }

        User user = new User();
        user.setFULLNAME(input.getFullName());
        user.setEMAIL(input.getEmail());
        user.setPASSWORD(passwordEncoder.encode(input.getPassword()));
        // set role
        user.setRole(optionalRole.get());
        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEMAIL(input.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(Defines.SECURITY_USER_NOT_FOUND));
    }
}
