package simple.task.planner.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import simple.task.planner.TestConfiguration;
import simple.task.planner.config.SecurityConfig;
import simple.task.planner.dto.TaskDTO;
import simple.task.planner.entities.User;
import simple.task.planner.security.JWTUtil;
import simple.task.planner.services.TaskService;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
@Import(value = {TestConfiguration.class, SecurityConfig.class})
public class TaskControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JWTUtil jwtUtil;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private TaskService taskService;
    @Autowired
    private ObjectMapper objectMapper;
    private String jwt;
    private TaskDTO sampleTask = new TaskDTO();

    @BeforeEach
    public void init(){
        User user = new User();
        user.setEmail("ter@f.ru");
        user.setPassword("ssss");
        jwt = jwtUtil.generateToken(user);
        Mockito.when(userDetailsService.loadUserByUsername("ter@f.ru")).thenReturn(user);

        sampleTask.setExecutor(user.getUsername());
        sampleTask.setPriority("HIGH");
        sampleTask.setStatus("IN_PROCESS");
        sampleTask.setTitle("some title");
        sampleTask.setDescription("some text");
        sampleTask.setAuthor(user.getUsername());
    }
    @Test
    void whenGetAllTasks_thanReturnListOfTasks() throws Exception {
        Mockito
                .when(taskService.getAll())
                .thenReturn(Arrays.asList(new TaskDTO(), new TaskDTO()));
        MvcResult result =
                mockMvc.perform(get("http://localhost:8080/tasks/getAll")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isAccepted())
                .andReturn();

        List taskDTOS = objectMapper
                .readValue(result.getResponse().getContentAsString(), List.class);
        Assertions.assertEquals(2, taskDTOS.size());
    }
    @Test
    void whenGetAllTasksWithPagination_thanReturnPageOFTasks() throws Exception {
        Mockito
                .when(taskService.getAll(1, 2))
                .thenReturn(Arrays.asList(new TaskDTO(), new TaskDTO()));
        MvcResult result =
                mockMvc.perform(get("http://localhost:8080/tasks/getAll?page=1&size=2")
                                .header("Authorization", "Bearer " + jwt))
                        .andExpect(status().isAccepted())
                        .andReturn();

        List taskDTOS = objectMapper
                .readValue(result.getResponse().getContentAsString(), List.class);
        Assertions.assertEquals(2, taskDTOS.size());
    }

    @Test
    void whenGetExecutableTasks_thenReturnCurrentUserTasks() throws Exception {
        Mockito
                .when(taskService.getExecutableTasks())
                .thenReturn(Arrays.asList(new TaskDTO(), new TaskDTO()));
        MvcResult result =
                mockMvc.perform(get("http://localhost:8080/tasks/getExecutableTasks")
                                .header("Authorization", "Bearer " + jwt))
                        .andExpect(status().isAccepted())
                        .andReturn();

        List taskDTOS = objectMapper
                .readValue(result.getResponse().getContentAsString(), List.class);
        Assertions.assertEquals(2, taskDTOS.size());
    }

    @Test
    void whenGetExecutableTasks_thenReturnCurrentUserTasksPage() throws Exception {
        Mockito
                .when(taskService.getExecutableTasks(1, 2))
                .thenReturn(Arrays.asList(new TaskDTO(), new TaskDTO()));
        MvcResult result =
                mockMvc.perform(get("http://localhost:8080/tasks/getExecutableTasks?page=1&size=2")
                                .header("Authorization", "Bearer " + jwt))
                        .andExpect(status().isAccepted())
                        .andReturn();

        List taskDTOS = objectMapper
                .readValue(result.getResponse().getContentAsString(), List.class);
        Assertions.assertEquals(2, taskDTOS.size());
    }

    @Test
    void whenGetRelatedTasks_thenReturnCurrentUserTasks() throws Exception {
        Mockito
                .when(taskService.getRelatedTasks())
                .thenReturn(Arrays.asList(new TaskDTO(), new TaskDTO()));
        MvcResult result =
                mockMvc.perform(get("http://localhost:8080/tasks/getRelatedTasks")
                                .header("Authorization", "Bearer " + jwt))
                        .andExpect(status().isAccepted())
                        .andReturn();

        List taskDTOS = objectMapper
                .readValue(result.getResponse().getContentAsString(), List.class);
        Assertions.assertEquals(2, taskDTOS.size());
    }

    @Test
    void whenGetRelatedTasks_thenReturnCurrentUserTasksPage() throws Exception {
        Mockito
                .when(taskService.getRelatedTasks(1, 2))
                .thenReturn(Arrays.asList(new TaskDTO(), new TaskDTO()));
        MvcResult result =
                mockMvc.perform(get("http://localhost:8080/tasks/getRelatedTasks?page=1&size=2")
                                .header("Authorization", "Bearer " + jwt))
                        .andExpect(status().isAccepted())
                        .andReturn();

        List taskDTOS = objectMapper
                .readValue(result.getResponse().getContentAsString(), List.class);
        Assertions.assertEquals(2, taskDTOS.size());
    }

    @Test
    void whenGetTasksByExecutor_thenReturnUserTasks() throws Exception {
        Mockito
                .when(taskService.getByExecutor("ter@f.ru"))
                .thenReturn(Arrays.asList(new TaskDTO(), new TaskDTO()));
        MvcResult result =
                mockMvc.perform(get("http://localhost:8080/tasks/getByExecutor?executorEmail=ter@f.ru")
                                .header("Authorization", "Bearer " + jwt))
                        .andExpect(status().isAccepted())
                        .andReturn();

        List taskDTOS = objectMapper
                .readValue(result.getResponse().getContentAsString(), List.class);
        Assertions.assertEquals(2, taskDTOS.size());
    }

    @Test
    void whenGetTasksByExecutor_thenReturnUserTasksPage() throws Exception {
        Mockito
                .when(taskService.getByExecutor("ter@f.ru",1, 2))
                .thenReturn(Arrays.asList(new TaskDTO(), new TaskDTO()));
        MvcResult result =
                mockMvc.perform(get("http://localhost:8080/tasks/getByExecutor?executorEmail=ter@f.ru&page=1&size=2")
                                .header("Authorization", "Bearer " + jwt))
                        .andExpect(status().isAccepted())
                        .andReturn();

        List taskDTOS = objectMapper
                .readValue(result.getResponse().getContentAsString(), List.class);
        Assertions.assertEquals(2, taskDTOS.size());
    }

    @Test
    void whenGetTasksByAuthor_thenReturnUserTasks() throws Exception {
        Mockito
                .when(taskService.getByAuthor("ter@f.ru"))
                .thenReturn(Arrays.asList(new TaskDTO(), new TaskDTO()));
        MvcResult result =
                mockMvc.perform(get("http://localhost:8080/tasks/getByAuthor?authorEmail=ter@f.ru")
                                .header("Authorization", "Bearer " + jwt))
                        .andExpect(status().isAccepted())
                        .andReturn();

        List taskDTOS = objectMapper
                .readValue(result.getResponse().getContentAsString(), List.class);
        Assertions.assertEquals(2, taskDTOS.size());
    }

    @Test
    void whenGetTasksByAuthor_thenReturnUserTasksPage() throws Exception {
        Mockito
                .when(taskService.getByAuthor("ter@f.ru",1, 2))
                .thenReturn(Arrays.asList(new TaskDTO(), new TaskDTO()));
        MvcResult result =
                mockMvc.perform(get("http://localhost:8080/tasks/getByAuthor?authorEmail=ter@f.ru&page=1&size=2")
                                .header("Authorization", "Bearer " + jwt))
                        .andExpect(status().isAccepted())
                        .andReturn();

        List taskDTOS = objectMapper
                .readValue(result.getResponse().getContentAsString(), List.class);
        Assertions.assertEquals(2, taskDTOS.size());
    }

    @Test
    void whenSaveTask_thanReturnCreatedStatus() throws Exception {
        TaskDTO taskDTO = new TaskDTO();
        mockMvc.perform(post("http://localhost:8080/tasks/add")
                        .content(objectMapper.writeValueAsString(sampleTask))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isCreated());
    }

    @Test
    void whenUpdateTask_thanReturnOkStatus() throws Exception {
        TaskDTO taskDTO = new TaskDTO();
        mockMvc.perform(patch("http://localhost:8080/tasks/update")
                        .content(objectMapper.writeValueAsString(sampleTask))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk());
    }

    @Test
    void whenDeleteTask_thanReturnOkStatus() throws Exception {
        mockMvc.perform(delete("http://localhost:8080/tasks/delete?id=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk());
    }

    @Test
    void whenChangeStatusTask_thanReturnOkStatus() throws Exception {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(1);
        taskDTO.setStatus("IN_WAITING");
        mockMvc.perform(patch("http://localhost:8080/tasks/changeStatus")
                        .content(objectMapper.writeValueAsString(taskDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk());
    }

    @Test
    void whenSetExecutorTask_thanReturnOkStatus() throws Exception {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(1);
        taskDTO.setExecutor("ter@f.ru");
        mockMvc.perform(patch("http://localhost:8080/tasks/setExecutor")
                        .content(objectMapper.writeValueAsString(taskDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk());
    }

}
