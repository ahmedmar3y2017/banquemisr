package banquemisr.challenge05.config;

import banquemisr.challenge05.controllers.TaskCustomController;
import banquemisr.challenge05.dtos.Hateoas.TaskHateoasModel;
import banquemisr.challenge05.models.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class TaskModelAssembler extends RepresentationModelAssemblerSupport<Task, TaskHateoasModel> {
    public TaskModelAssembler() {
        super(TaskCustomController.class, TaskHateoasModel.class);
    }

    @Override
    public TaskHateoasModel toModel(Task entity) {
        TaskHateoasModel model = new TaskHateoasModel();
        // Both CustomerModel and Customer have the same property names. So copy the values from the Entity to the Model
        BeanUtils.copyProperties(entity, model);
        return model;
    }
}
