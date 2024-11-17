package banquemisr.challenge05.services;

import banquemisr.challenge05.models.TaskStatus;
import banquemisr.challenge05.models.email.EmailDetails;
import banquemisr.challenge05.repository.TaskRepository;
import banquemisr.challenge05.defines.Defines;
import banquemisr.challenge05.exceptions.NoSuchTaskExistsException;
import banquemisr.challenge05.exceptions.TaskAlreadyExistsException;
import banquemisr.challenge05.models.Task;
import banquemisr.challenge05.services.email.EmailService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    private EmailService emailService;

    @Value("${spring.mail.Recipient}")
    private String recipient;


    public List<Task> getAllTasks() {

        return taskRepository.findAll();

    }

    public Task getTaskById(long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> {
            return new NoSuchTaskExistsException(Defines.ERROR_TASK_NOT_FOND);
        });
        return task;
    }

    public void deleteTask(long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public Task saveTask(Task task) {
        Task taskDB = taskRepository.findById(task.getId()).orElse(null);
        if (taskDB != null) {
            throw new TaskAlreadyExistsException(Defines.ERROR_TASK_ALREADY_FOND);
        }
        Task save = taskRepository.save(task);
        return save;

    }

    @Override
    public Task updateTask(Task task) {
        taskRepository.findById(task.getId()).orElseThrow(() -> {
            // throw exception -> task not found for update
            throw new NoSuchTaskExistsException(Defines.ERROR_TASK_NOT_FOND);

        });
        Task save = taskRepository.save(task);
        return save;

    }

    // ---------- paging & filtering & sorting ( Hateous )
    public Page<Task> findByTitleLikeAndDescriptionLikeAndStatusIsAndDueDateEquals(String taskTitle, String taskDescription, TaskStatus Status, Date dueDate, int page, int size, List<String> sortList, String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortOrder)));
        // Apply the filter for taskTitle and taskDescription

        return taskRepository.findByTitleLikeAndDescriptionLikeAndStatusLikeAndDueDateEquals(taskTitle, taskDescription, Status == null ? "" : Status.name(), dueDate, pageable);
    }

    private List<Sort.Order> createSortOrder(List<String> sortList, String sortDirection) {
        List<Sort.Order> sorts = new ArrayList<>();
        Sort.Direction direction;
        for (String sort : sortList) {
            if (sortDirection != null) {
                direction = Sort.Direction.fromString(sortDirection);
            } else {
                direction = Sort.Direction.DESC;
            }
            sorts.add(new Sort.Order(direction, sort));
        }
        return sorts;
    }

    //    ************ perform Upcoming Tasks **********
    public String performUpcomingTasks() throws ParseException {

        // get all tasks that due date < 24 h and status = todo , sorted by priority
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date tomorrowDate = new Date();
        tomorrowDate = DateUtils.addDays(tomorrowDate, 1);
        tomorrowDate = format.parse(format.format(tomorrowDate));
        Date currentDate = new Date();
        currentDate = format.parse(format.format(currentDate));

        List<Task> tasksList = taskRepository.findByStatusEqualsAndDueDateBetweenOrderByPriorityAsc(TaskStatus.TODO, currentDate, tomorrowDate);
        // send mail for upcoming tasks deadlines
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setSubject("UpComing Tasks !!");
        emailDetails.setRecipient(recipient);
        String htmlContent = "<h1>This are Upcoming Tasks "+tasksList.size()+"</h1>" +
                "<p>"+tasksList.stream().map(task -> task.getTitle()).toList()+"</p>";
        emailDetails.setMsgBody(htmlContent);
        // send mail
        String sendSimpleMail = emailService.sendSimpleMail(emailDetails);

        return sendSimpleMail;
    }

}
