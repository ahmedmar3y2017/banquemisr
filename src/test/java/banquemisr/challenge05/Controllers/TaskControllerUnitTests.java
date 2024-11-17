package banquemisr.challenge05.Controllers;

import banquemisr.challenge05.controllers.TaskController;
import banquemisr.challenge05.models.Task;
import banquemisr.challenge05.models.TaskStatus;
import banquemisr.challenge05.services.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.willDoNothing;

@WebMvcTest(TaskController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class TaskControllerUnitTests {

    @MockBean
    TaskService taskService;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;
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

    //@WithMockUser(value = "ahmed.marey@asset.com.eg")
    @Test
    @Order(1)
    public void saveTaskTest() throws Exception {
        // precondition
        given(taskService.saveTask(any(Task.class))).willReturn(task);

        // api action
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)));

        // verify
        response.andDo(MockMvcResultHandlers.print()).
                andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title",
                        is(task.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description",
                        is(task.getDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.priority",
                        is(task.getPriority())));
    }


    @Test
    @Order(2)
    public void getTaskTest() throws Exception {
        // precondition
        given(taskService.getTaskById(task.getId())).willReturn(task);

        // api action
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/task/{taskId}", task.getId())
                .contentType(MediaType.APPLICATION_JSON));

        // verify
        response.andDo(MockMvcResultHandlers.print()).
                andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title",
                        is(task.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description",
                        is(task.getDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.priority",
                        is(task.getPriority())));


    }

    @Test
    @Order(3)
    public void getAllTaskTest() throws Exception {
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        // precondition
        given(taskService.getAllTasks()).willReturn(tasks);

        // api action
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/task")
                .contentType(MediaType.APPLICATION_JSON));

        // verify
        response.andDo(MockMvcResultHandlers.print()).
                andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",
                        is(tasks.size())));


    }


    @Test
    @Order(4)
    public void updateTaskTest() throws Exception {
        // precondition
        given(taskService.updateTask(any(Task.class))).willReturn(task);
        task.setTitle("Test Task2 Updated");
        task.setDescription("Test Task2 Updated");
        // api action
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)));

        // verify
        response.andDo(MockMvcResultHandlers.print()).
                andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title",
                        is(task.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description",
                        is(task.getDescription())));
    }

    @Test
    @Order(5)
    public void deleteTaskTest() throws Exception {
        // precondition
        willDoNothing().given(taskService).deleteTask(task.getId());

        // action
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/task/{id}", task.getId()));

        // then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}
