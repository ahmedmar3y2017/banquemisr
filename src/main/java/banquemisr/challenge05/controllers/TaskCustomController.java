package banquemisr.challenge05.controllers;

import banquemisr.challenge05.config.TaskModelAssembler;
import banquemisr.challenge05.dtos.Hateoas.TaskHateoasModel;
import banquemisr.challenge05.models.Task;
import banquemisr.challenge05.models.TaskStatus;
import banquemisr.challenge05.services.TaskService;
import banquemisr.challenge05.swagger.CustomTaskApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/hateoas")
public class TaskCustomController implements CustomTaskApi {


    //    *********** for paging hateoas
    @Autowired
    private TaskModelAssembler taskModelAssembler;

    @Autowired
    private PagedResourcesAssembler<Task> pagedResourcesAssembler;


    @Autowired
    TaskService taskService;
    // -------------- Pagination & sorting ------------ (hateoas)
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

    ) {
        Page<Task> byTitleLikeAndDescriptionLikeAndStatusIsAndDueDateEquals = taskService.findByTitleLikeAndDescriptionLikeAndStatusIsAndDueDateEquals(
                taskTitle, taskDescription, taskStatus, dueDate, page, size, sortList, sortOrder.toString()
        );
        PagedModel<TaskHateoasModel> pagedResourcesAssemblerModel = pagedResourcesAssembler.toModel(byTitleLikeAndDescriptionLikeAndStatusIsAndDueDateEquals, taskModelAssembler);

        return new ResponseEntity<>(pagedResourcesAssemblerModel,
                HttpStatus.OK);

    }

}
