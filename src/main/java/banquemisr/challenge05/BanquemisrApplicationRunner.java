package banquemisr.challenge05;

import banquemisr.challenge05.dtos.RegisterUserDto;
import banquemisr.challenge05.models.Role;
import banquemisr.challenge05.models.RoleEnum;
import banquemisr.challenge05.models.Task;
import banquemisr.challenge05.models.TaskStatus;
import banquemisr.challenge05.repository.RoleRepository;
import banquemisr.challenge05.services.AuthenticationService;
import banquemisr.challenge05.services.TaskService;
import banquemisr.challenge05.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Optional;


@Component
public class BanquemisrApplicationRunner implements ApplicationRunner {

    @Autowired
    TaskService taskService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    UserService userService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // load roles
        loadRoles();
        createSuperAdmin();
        // insert default tasks
        for (int i = 1; i <= 1000; i++) {

            Task task = new Task("Task " + i, "Task Desc " + i, TaskStatus.randomDirection(), i, new Date());
            taskService.saveTask(task);

        }


    }

    private void createSuperAdmin() {

        // insert default User (Super Admin)
        RegisterUserDto registerUserDto = new RegisterUserDto("superAdmin@asset.com.eg", "admin", "ahmed marey");

        userService.createSuperAdministrator(registerUserDto);

    }

    private void loadRoles() {
        RoleEnum[] roleNames = new RoleEnum[]{RoleEnum.USER, RoleEnum.ADMIN, RoleEnum.SUPER_ADMIN};
        Map<RoleEnum, String> roleDescriptionMap = Map.of(
                RoleEnum.USER, "Default user role",
                RoleEnum.ADMIN, "Administrator role",
                RoleEnum.SUPER_ADMIN, "Super Administrator role"
        );

        Arrays.stream(roleNames).forEach((roleName) -> {
            Optional<Role> optionalRole = roleRepository.findByName(roleName);

            optionalRole.ifPresentOrElse(System.out::println, () -> {
                Role roleToCreate = new Role();

                roleToCreate.setName(roleName);

                roleToCreate.setDescription(roleDescriptionMap.get(roleName));

                roleRepository.save(roleToCreate);
            });
        });
    }
}
