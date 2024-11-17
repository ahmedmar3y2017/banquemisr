package banquemisr.challenge05.dtos.Hateoas;

import banquemisr.challenge05.models.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskHateoasModel extends RepresentationModel<TaskHateoasModel> {

    private long id;

    private String title;

    private String description;

    private TaskStatus status;
    private int priority;

    private Date dueDate;

}
