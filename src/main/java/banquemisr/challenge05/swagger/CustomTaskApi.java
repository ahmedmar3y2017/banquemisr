package banquemisr.challenge05.swagger;

import banquemisr.challenge05.dtos.Hateoas.TaskHateoasModel;
import banquemisr.challenge05.models.Task;
import banquemisr.challenge05.models.TaskStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Tag(name = "CustomTaskApi", description = "the CustomTaskApi")
// JWT Swagger Auth
@SecurityRequirement(name = "Authorization")

public interface CustomTaskApi {

    @Operation(
            summary = "Advanced Search with Hateoas response format (filtering , paginating , sorting)",
            description = "Advanced Search when fetching Tasks  {filtering by (title , desc , status , dueDate)} , {paging by (pageNum , size)} , {sorting list &sorting direction}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    }
    )

    public ResponseEntity<PagedModel<TaskHateoasModel>> fetchTasksAsFilteredList(@RequestParam(defaultValue = "") String taskTitle,
                                                                                 @RequestParam(defaultValue = "") String taskDescription,
                                                                                 @RequestParam(defaultValue = "") TaskStatus taskStatus,
                                                                                 @RequestParam(defaultValue = "")
                                                                                 @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                                                 Date dueDate,
                                                                                 @RequestParam(defaultValue = "0") int page,
                                                                                 @RequestParam(defaultValue = "30") int size,
                                                                                 @RequestParam(defaultValue = "") List<String> sortList,
                                                                                 @RequestParam(defaultValue = "DESC") Sort.Direction sortOrder

    );
}
