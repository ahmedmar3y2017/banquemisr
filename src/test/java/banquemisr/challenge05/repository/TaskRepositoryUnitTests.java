package banquemisr.challenge05.repository;


import banquemisr.challenge05.models.Task;
import banquemisr.challenge05.models.TaskStatus;
import org.apache.commons.lang3.time.DateUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskRepositoryUnitTests {

    @Autowired
    TaskRepository taskRepository;

    @Test
    @DisplayName("Test 1:Save Task Test")
    @Order(1)
    @Rollback(value = false)
    public void saveTaskTest() {

        Task task = new Task();
        task.setTitle("Test Task1");
        task.setDescription("Test Desc1");
        task.setStatus(TaskStatus.TODO);
        task.setPriority(1);
        task.setDueDate(new Timestamp(new Date().getTime()));

        Task save = taskRepository.save(task);

        Assertions.assertThat(save.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getTaskTest() {

        Optional<Task> taskRepositoryById = taskRepository.findById(1L);
        Assertions.assertThat(taskRepositoryById.isPresent()).isTrue();
    }

    @Test
    @Order(3)
    public void getAllTaskTest() {

        List<Task> all = taskRepository.findAll();
        Assertions.assertThat(all).isNotNull();
        Assertions.assertThat(all.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateTaskTest() {

        Optional<Task> taskRepositoryById = taskRepository.findById(1L);
        Task task = taskRepositoryById.get();
        task.setTitle("Test Task2");
        Task save = taskRepository.save(task);
        Assertions.assertThat(save.getTitle()).isEqualTo("Test Task2");
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteTaskTest() {
        taskRepository.deleteById(1L);
        Optional<Task> TaskOptional = taskRepository.findById(1L);

        Assertions.assertThat(TaskOptional.isEmpty()).isTrue();
    }


    @Test
    @Order(6)
    @Rollback(value = false)
    public void findByTitleLikeAndDescriptionLikeAndStatusLikeAndDueDateEquals() {

        for (int i = 1; i <= 20; i++) {
            Task task = new Task("Task " + i, "Task Desc " + i, TaskStatus.randomDirection(), i, new Date());

            taskRepository.save(task);

        }
        // sort by priority desc
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Arrays.asList(Sort.Order.desc("priority"))));

        Page<Task> byTitleLikeAndDescriptionLikeAndStatusLikeAndDueDateEquals = taskRepository.findByTitleLikeAndDescriptionLikeAndStatusLikeAndDueDateEquals(
                "task", "", "", new Date()
                , pageable
        );
        // delete all
        taskRepository.deleteAll();
        // check filtering
        Assertions.assertThat(byTitleLikeAndDescriptionLikeAndStatusLikeAndDueDateEquals.get().findAny().get().getTitle()).contains("Task");
        // check paging
        Assertions.assertThat(byTitleLikeAndDescriptionLikeAndStatusLikeAndDueDateEquals.stream().count()).isEqualTo(10);
        // check sorting
        Assertions.assertThat(byTitleLikeAndDescriptionLikeAndStatusLikeAndDueDateEquals.stream().findFirst().get().getPriority()).isEqualTo(20);
    }

    @Test
    @Order(7)
    @Rollback(value = false)
    public void findByStatusEqualsAndDueDateBetweenOrderByPriorityAsc() {
        for (int i = 1; i <= 20; i++) {
            Task task = new Task("Task " + i, "Task Desc " + i, TaskStatus.randomDirection(), i, new Date());

            taskRepository.save(task);

        }
        // get all tasks that due date < 24 h and status = todo , sorted by priority
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date tomorrowDate = new Date();
        tomorrowDate = DateUtils.addDays(tomorrowDate, 1);
        Date currentDate = new Date();
        try {
            tomorrowDate = format.parse(format.format(tomorrowDate));
            currentDate = format.parse(format.format(currentDate));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        List<Task> tasksList = taskRepository.findByStatusEqualsAndDueDateBetweenOrderByPriorityAsc(TaskStatus.TODO, currentDate, tomorrowDate);
        taskRepository.deleteAll();

        Assertions.assertThat(tasksList.size()).isGreaterThan(0);

    }


}
