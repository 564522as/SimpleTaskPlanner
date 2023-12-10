package simple.task.planner.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import simple.task.planner.dto.TaskDTO;
import simple.task.planner.entities.User;

@SpringBootTest
public class TaskServiceTests {
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserDetailsService userDetailsService;
    private User sampleUser;
    private TaskDTO sampleTask;

    @BeforeEach
    void init() {
        sampleUser = (User)userDetailsService.loadUserByUsername("ter@f.ru");
        UsernamePasswordAuthenticationToken upat =
                new UsernamePasswordAuthenticationToken(sampleUser, sampleUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(upat);
        sampleTask = taskService.getByAuthor(sampleUser.getUsername()).get(0);
    }

    @Test
    void saveTest() {
        sampleTask.setId(null);
        Assertions.assertDoesNotThrow(() -> this.taskService.save(sampleTask));
    }

    @Test
    void updateTest() {
        Assertions.assertDoesNotThrow(() -> this.taskService.update(sampleTask));
    }

    @Test
    void getAllTest() {
        Assertions.assertDoesNotThrow(() -> this.taskService.getAll());
        Assertions.assertNotNull(this.taskService.getAll());
    }

    @Test
    void getAllWithPaginationTest() {
        Assertions.assertDoesNotThrow(() -> this.taskService.getAll(1, 2));
        Assertions.assertNotNull(this.taskService.getAll(1, 2));
        Assertions.assertEquals(2, this.taskService.getAll(1, 2).size());
    }

    @Test
    void getByIdTest() {
        Assertions.assertNotNull(this.taskService.getById(sampleTask.getId()));
    }

    @Test
    void deleteByIdTest() {
        Assertions.assertDoesNotThrow(() -> this.taskService.deleteById(sampleTask.getId()));
    }

    @Test
    void getRelatedTasksTest() {
        Assertions.assertDoesNotThrow(() -> this.taskService.getRelatedTasks());
        Assertions.assertNotNull(this.taskService.getAll());
    }

    @Test
    void getRelatedTasksWithPaginationTest() {
        Assertions.assertDoesNotThrow(() -> this.taskService.getRelatedTasks(1, 2));
        Assertions.assertNotNull(this.taskService.getRelatedTasks(1, 2));
        Assertions.assertEquals(2, this.taskService.getRelatedTasks(1, 2).size());
    }

    @Test
    void getExecutableTasksTest() {
        Assertions.assertDoesNotThrow(() -> this.taskService.getExecutableTasks());
        Assertions.assertNotNull(this.taskService.getRelatedTasks());
    }

    @Test
    void getExecutableTasksWithPaginationTest() {
        taskService.save(sampleTask);
        taskService.save(sampleTask);
        Assertions.assertDoesNotThrow(() -> this.taskService.getExecutableTasks(1, 2));
        Assertions.assertNotNull(this.taskService.getExecutableTasks(1, 2));
        Assertions.assertEquals(2, this.taskService.getExecutableTasks(1, 2).size());
    }

    @Test
    void getByAuthorTasksTest() {
        Assertions.assertDoesNotThrow(() -> this.taskService.getByAuthor(sampleTask.getAuthor()));
        Assertions.assertNotNull(this.taskService.getByAuthor(sampleTask.getAuthor()));
    }

    @Test
    void getByAuthorTasksWithPaginationTest() {
        Assertions.assertDoesNotThrow(() -> this.taskService.getByAuthor(sampleTask.getAuthor(), 1, 2));
        Assertions.assertNotNull(this.taskService.getByAuthor(sampleTask.getAuthor(), 1, 2));
        Assertions.assertEquals(2, this.taskService.getByAuthor(sampleTask.getAuthor(), 1, 2).size());
    }

    @Test
    void getByExecutorTasksTest() {
        Assertions.assertDoesNotThrow(() -> this.taskService.getByExecutor(sampleTask.getAuthor()));
        Assertions.assertNotNull(this.taskService.getByExecutor(sampleTask.getAuthor()));
    }

    @Test
    void getByExecutorTasksWithPaginationTest() {
        Assertions.assertDoesNotThrow(() -> this.taskService.getByExecutor(sampleTask.getAuthor(), 1, 2));
        Assertions.assertNotNull(this.taskService.getByExecutor(sampleTask.getAuthor(), 1, 2));
        Assertions.assertEquals(2, this.taskService.getByExecutor(sampleTask.getAuthor(), 1, 2).size());
    }

    @Test
    void setExecutorTest() {
        Assertions.assertDoesNotThrow(() -> this.taskService.setExecutor(sampleTask.getId(), sampleUser.getUsername()));
    }

    @Test
    void setStatusTest() {
        this.taskService.setExecutor(sampleTask.getId(), sampleUser.getUsername());
        Assertions.assertDoesNotThrow(() -> this.taskService.setStatus(sampleTask.getId(), "IN_PROCESS"));
    }

}
