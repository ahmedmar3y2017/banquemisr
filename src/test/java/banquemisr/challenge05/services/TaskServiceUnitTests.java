package banquemisr.challenge05.services;


import banquemisr.challenge05.models.Task;
import banquemisr.challenge05.models.TaskStatus;
import banquemisr.challenge05.models.email.EmailDetails;
import banquemisr.challenge05.repository.TaskRepository;
import banquemisr.challenge05.services.email.EmailService;
import org.apache.commons.lang3.time.DateUtils;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.BDDAssumptions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskServiceUnitTests {

    @Mock
    TaskRepository taskRepository;
    @Mock
    EmailService emailService;
    @InjectMocks
    TaskServiceImpl taskService;

    Task task;

    @BeforeEach
    public void setup() {
        task = new Task();
        task.setId(1L);
        task.setTitle("Test Task1");
        task.setDescription("Test Desc1");
        task.setStatus(TaskStatus.TODO);
        task.setPriority(1);
        task.setDueDate(new Timestamp(new Date().getTime()));

    }

    @Test
    @Order(1)
    public void saveTaskTest() throws Exception {

        BDDMockito.given(taskRepository.save(task)).willReturn(task);

        Task saveTask = taskService.saveTask(task);

        Assertions.assertThat(saveTask).isNotNull();

    }

    @Test
    @Order(2)
    public void getTaskByIdTest() throws Exception {

        given(taskRepository.findById(task.getId())).willReturn(Optional.ofNullable(task));

        Task taskById = taskService.getTaskById(task.getId());

        Assertions.assertThat(taskById).isNotNull();

    }

    @Test
    @Order(3)
    public void getAllTasksTest() throws Exception {

        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        given(taskRepository.findAll()).willReturn(tasks);

        List<Task> allTasks = taskService.getAllTasks();

        Assertions.assertThat(allTasks.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    public void updateTaskTest() throws Exception {

        BDDMockito.given(taskRepository.findById(task.getId())).willReturn(Optional.ofNullable(task));
        BDDMockito.given(taskRepository.save(task)).willReturn(task);
        task.setTitle("Test Task2 Updated");
        task.setDescription("Test Task2 Updated");

        Task saveTask = taskService.updateTask(task);

        Assertions.assertThat(saveTask.getTitle()).isEqualTo(task.getTitle());
        Assertions.assertThat(saveTask.getDescription()).isEqualTo(task.getDescription());

    }

    @Test
    @Order(5)
    public void deleteTaskTest() {

        // precondition
        willDoNothing().given(taskRepository).deleteById(task.getId());

        taskService.deleteTask(task.getId());

        verify(taskRepository, times(1)).deleteById(task.getId());

    }

    @Test
    @Order(6)
    public void findByTitleLikeAndDescriptionLikeAndStatusIsAndDueDateEquals() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Arrays.asList(Sort.Order.desc("priority"))));

        // precondition
        List<Task> tasks = new ArrayList<>();
        for (int i = 10; i > 0; i--) {
            Task task = new Task("Task " + i, "Task Desc " + i, TaskStatus.randomDirection(), i, new Date());
            tasks.add(task);
        }
        PageImpl<Task> taskPage = new PageImpl<>(tasks.subList(0, 5), pageable, tasks.size());
        Date date = new Date();
        given(taskRepository.findByTitleLikeAndDescriptionLikeAndStatusLikeAndDueDateEquals("Task", "", "", date, pageable)).willReturn(taskPage);

        Page<Task> byTitleLikeAndDescriptionLikeAndStatusIsAndDueDateEquals = taskService.findByTitleLikeAndDescriptionLikeAndStatusIsAndDueDateEquals("Task", "", null, date, 0, 5, Arrays.asList("priority"), "Desc");

        // check filtering
        Assertions.assertThat(byTitleLikeAndDescriptionLikeAndStatusIsAndDueDateEquals.get().findAny().get().getTitle()).contains("Task");
        // check paging
        Assertions.assertThat(byTitleLikeAndDescriptionLikeAndStatusIsAndDueDateEquals.stream().count()).isEqualTo(5);
        // check sorting
        Assertions.assertThat(byTitleLikeAndDescriptionLikeAndStatusIsAndDueDateEquals.stream().findFirst().get().getPriority()).isEqualTo(10);

    }

}