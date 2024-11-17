package banquemisr.challenge05.services;

import banquemisr.challenge05.models.Task;
import banquemisr.challenge05.models.TaskStatus;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface TaskService {

    public List<Task> getAllTasks();

    public Task getTaskById(long taskId);

    public void deleteTask(long taskId);

    public Task saveTask(Task task);

    public Task updateTask(Task task);

    public Page<Task> findByTitleLikeAndDescriptionLikeAndStatusIsAndDueDateEquals(String taskTitle, String taskDescription, TaskStatus Status, Date dueDate, int page, int size, List<String> sortList, String sortOrder);

    public String performUpcomingTasks() throws ParseException;
}
