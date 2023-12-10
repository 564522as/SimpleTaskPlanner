package simple.task.planner.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import simple.task.planner.dto.CommentDTO;
import simple.task.planner.dto.TaskDTO;
import simple.task.planner.entities.User;

import java.util.List;

@SpringBootTest
public class CommentServiceTests {
    @Autowired
    private CommentService commentService;
    @Autowired
    private TaskService taskService;
    private TaskDTO sampleTask;
    @BeforeEach
    void init() {
        sampleTask = taskService.getByAuthor("ter@f.ru").get(0);
        User user = new User();
        user.setEmail("ter@f.ru");
        UsernamePasswordAuthenticationToken upat =
                new UsernamePasswordAuthenticationToken(user, "ssss");
        SecurityContextHolder.getContext().setAuthentication(upat);
    }
    @Test
    void getByTaskTest() {
        List<CommentDTO> commentDTOS =
                commentService.getByTask(sampleTask.getId());
        Assertions.assertNotNull(commentDTOS);
    }

    @Test
    void getByAuthorTest() {
        List<CommentDTO> commentDTOS = commentService.getByAuthor("ter@f.ru");
        Assertions.assertNotNull(commentDTOS);
    }

    @Test
    void addTest() {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setTask(sampleTask.getId());
        commentDTO.setAuthor("ter@f.ru");
        commentDTO.setText("some text");
        Assertions.assertDoesNotThrow(() -> commentService.add(commentDTO));
    }

    @Test
    void updateTest() {
        CommentDTO comment = commentService.getByAuthor("ter@f.ru").get(0);
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setAuthor("ter@f.ru");
        commentDTO.setText("updated");
        Assertions.assertDoesNotThrow(() -> commentService.update(commentDTO));
    }

    @Test
    void deleteTest() {
        CommentDTO comment = commentService.getByAuthor("ter@f.ru").get(0);
        Assertions.assertDoesNotThrow(() -> commentService.delete(comment.getId()));
    }
}
