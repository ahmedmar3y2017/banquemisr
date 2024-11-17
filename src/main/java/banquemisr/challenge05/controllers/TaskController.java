package banquemisr.challenge05.controllers;

import banquemisr.challenge05.config.TaskModelAssembler;
import banquemisr.challenge05.models.Task;
import banquemisr.challenge05.models.TaskStatus;
import banquemisr.challenge05.services.TaskService;
import banquemisr.challenge05.swagger.TaskApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController implements TaskApi {



    @Autowired
    TaskService taskService;

    @GetMapping("/task")
    public ResponseEntity<List<Task>> getAllTasks() {

        return new ResponseEntity<>(taskService.getAllTasks(),
                HttpStatus.OK);
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable("taskId") long taskId) {

        return new ResponseEntity<>(taskService.getTaskById(taskId),
                HttpStatus.OK);
    }

    @DeleteMapping("/task/{taskId}")
    public void deleteTask(@PathVariable("taskId") int taskId) {
        taskService.deleteTask(taskId);
    }


    @PostMapping("/task")
    public ResponseEntity<Task> saveTask(@RequestBody Task task) {
        Task saveTask = taskService.saveTask(task);

        return new ResponseEntity<>(saveTask,
                HttpStatus.CREATED);
    }

    //creating put mapping that updates the task detail
    @PutMapping("/task")
    public ResponseEntity<Task> updateTask(@RequestBody Task task) {
        Task taskUpdated = taskService.updateTask(task);
        return new ResponseEntity<>(taskUpdated,
                HttpStatus.OK);
    }


    // -------------- Pagination & sorting ------------

    /**
     *
     * @param taskTitle
     * @param taskDescription
     * @param taskStatus
     * @param dueDate
     * @param page
     * @param size
     * @param sortList
     * @param sortOrder
     * @return
     */
    @GetMapping("/v1/task")
    public ResponseEntity<Page<Task>> findByTitleLikeAndDescriptionLikeAndStatusIsAndDueDateEquals(@RequestParam(defaultValue = "") String taskTitle,
                                                               @RequestParam(defaultValue = "") String taskDescription,
                                                               @RequestParam(defaultValue = "") TaskStatus taskStatus,
                                                               @RequestParam(defaultValue = "")
                                                               @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                               Date dueDate,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "30") int size,
                                                               @RequestParam(defaultValue = "") List<String> sortList,
                                                               @RequestParam(defaultValue = "DESC") Sort.Direction sortOrder

    ) {
        Page<Task> byTitleLikeAndDescriptionLikeAndStatusIsAndDueDateEquals = taskService.findByTitleLikeAndDescriptionLikeAndStatusIsAndDueDateEquals(
                taskTitle, taskDescription, taskStatus, dueDate, page, size, sortList, sortOrder.toString()
        );

        return new ResponseEntity<>(byTitleLikeAndDescriptionLikeAndStatusIsAndDueDateEquals,
                HttpStatus.OK);

    }

}