package banquemisr.challenge05.services;

import banquemisr.challenge05.dtos.RegisterUserDto;
import banquemisr.challenge05.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public List<User> allUsers();

    public User createAdministrator(RegisterUserDto input);

    public User createSuperAdministrator(RegisterUserDto input);

    User findByEMAIL(String email);

}
