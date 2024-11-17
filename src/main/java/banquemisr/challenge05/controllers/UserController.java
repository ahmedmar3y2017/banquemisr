package banquemisr.challenge05.controllers;

import banquemisr.challenge05.models.User;
import banquemisr.challenge05.services.UserService;
import banquemisr.challenge05.swagger.UserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequestMapping("/users")
@RestController
public class UserController implements UserApi {

    @Autowired
    UserService userService;


    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> authenticatedUser() {
        // Existing code here...
        return null;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<User>> allUsers() {

        List<User> users = userService.allUsers();
        return new ResponseEntity<>(users,
                HttpStatus.OK);

    }
}
