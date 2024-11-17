package banquemisr.challenge05.swagger;

import banquemisr.challenge05.models.Task;
import banquemisr.challenge05.models.TaskStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Tag(name = "Tasks", description = "the Task Apis")
// JWT Swagger Auth
@SecurityRequirement(name = "Authorization")

public interface TaskApi {

    @Operation(summary = "Fetch all Tasks", description = "fetches all Tasks entities and their data from data source")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation")})
    ResponseEntity<List<Task>> getAllTasks();

    @Operation(summary = "Fetch Task By Id", description = "fetch Task By Id entities and their data from data source")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation"), @ApiResponse(responseCode = "404", description = "throws NoSuchTaskExistsException -> When Task Not found ")})

    public ResponseEntity<Task> getTaskById(@PathVariable("taskId") long taskId);

    @Operation(summary = "Delete Task By Id", description = "Delete Task By Id ")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation")})

    public void deleteTask(@PathVariable("taskId") int taskId);

    @Operation(summary = "Add New Task", description = "Add New")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation"), @ApiResponse(responseCode = "409", description = "throws TaskAlreadyExistsException -> When Task already exists ")

    })
    public ResponseEntity<Task> saveTask(@RequestBody Task task);

    @Operation(summary = "Update New Task", description = "Update New")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation"), @ApiResponse(responseCode = "404", description = "throws NoSuchTaskExistsException -> When Task Not found ")

    })
    public ResponseEntity<Task> updateTask(@RequestBody Task task);

    @Operation(summary = "Advanced Search (filtering , paginating , sorting)", description = "Advanced Search when fetching Tasks  {filtering by (title , desc , status , dueDate)} , {paging by (pageNum , size)} , {sorting list &sorting direction}")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation")})
    public ResponseEntity<Page<Task>> findByTitleLikeAndDescriptionLikeAndStatusIsAndDueDateEquals(@RequestParam(defaultValue = "") String taskTitle, @RequestParam(defaultValue = "") String taskDescription, @RequestParam(defaultValue = "") TaskStatus taskStatus, @RequestParam(defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dueDate, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "30") int size, @RequestParam(defaultValue = "") List<String> sortList, @RequestParam(defaultValue = "DESC") Sort.Direction sortOrder

    );
}
