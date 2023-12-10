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
import simple.task.planner.dto.CommentDTO;
import simple.task.planner.entities.User;
import simple.task.planner.security.JWTUtil;
import simple.task.planner.services.CommentService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
@Import(value = {SecurityConfig.class, TestConfiguration.class})
public class CommentControllerTests {
    @MockBean
    private CommentService commentService;
    @MockBean
    private UserDetailsService userDetailsService;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private String jwt;
    @BeforeEach
    public void getJwt() {
        User user = new User();
        user.setEmail("ter@f.ru");
        user.setPassword("ssss");
        jwt = jwtUtil.generateToken(user);
        Mockito.when(userDetailsService.loadUserByUsername("ter@f.ru")).thenReturn(user);
    }

    @Test
    void whenGetByTask_thenReturnCommentList() throws Exception {
        CommentDTO commentDTO = new CommentDTO();
        CommentDTO commentDTO1 = new CommentDTO();
        commentDTO.setTask(1);
        commentDTO1.setTask(1);
        Mockito.when(commentService.getByTask(1))
                .thenReturn(Arrays.asList(commentDTO, commentDTO1));
        MvcResult result =
                mockMvc.perform(get("http://localhost:8080/comments/getByTask?taskId=1")
                                .header("Authorization", "Bearer " + jwt))
                        .andExpect(status().isAccepted())
                        .andReturn();

        List comments = objectMapper.readValue(result
                .getResponse().getContentAsString(), List.class);

        Assertions.assertEquals(2, comments.size());
    }

    @Test
    void whenGetByAuthor_thenReturnCommentList() throws Exception {
        CommentDTO commentDTO = new CommentDTO();
        CommentDTO commentDTO1 = new CommentDTO();
        commentDTO.setTask(1);
        commentDTO1.setTask(1);
        Mockito.when(commentService.getByAuthor("ter@f.ru"))
                .thenReturn(Arrays.asList(commentDTO, commentDTO1));
        MvcResult result =
                mockMvc.perform(get("http://localhost:8080/comments/getByAuthor?authorEmail=ter@f.ru")
                                .header("Authorization", "Bearer " + jwt))
                        .andExpect(status().isAccepted())
                        .andReturn();

        List comments = objectMapper.readValue(result
                .getResponse().getContentAsString(), List.class);

        Assertions.assertEquals(2, comments.size());
    }

    @Test
    void whenAddComment_thenReturnCreatedStatus() throws Exception {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setAuthor("ter@f.ru");
        commentDTO.setTask(1);
        commentDTO.setText("some text");
        mockMvc.perform(post("http://localhost:8080/comments/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwt)
                        .content(objectMapper.writeValueAsString(commentDTO)))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void whenUpdateComment_thenReturnCreatedStatus() throws Exception {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setAuthor("ter@f.ru");
        commentDTO.setTask(1);
        commentDTO.setText("some text");
        mockMvc.perform(patch("http://localhost:8080/comments/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwt)
                        .content(objectMapper.writeValueAsString(commentDTO)))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void whenDeleteComment_thenReturnAcceptedStatus() throws Exception {
        mockMvc.perform(delete("http://localhost:8080/comments/delete?commentId=1")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk())
                .andReturn();
    }
}
