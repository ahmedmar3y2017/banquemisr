package banquemisr.challenge05.repository;

import banquemisr.challenge05.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEMAIL(String email);
}

