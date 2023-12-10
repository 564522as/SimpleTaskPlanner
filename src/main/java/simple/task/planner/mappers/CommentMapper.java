package simple.task.planner.mappers;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import simple.task.planner.dto.CommentDTO;
import simple.task.planner.entities.Comment;
import simple.task.planner.entities.User;
import simple.task.planner.services.TaskService;
@Component
public class CommentMapper implements EntityAndDTOMapper<Comment, CommentDTO> {
    private final UserDetailsService userDetailsService;
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public CommentMapper(UserDetailsService userDetailsService, TaskService taskService, TaskMapper taskMapper) {
        this.userDetailsService = userDetailsService;
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @Override
    public Comment toEntity(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setText(commentDTO.getText());
        comment.setTask(taskMapper
                .toEntity(taskService.getById(commentDTO.getTask())));
        comment.setAuthor((User) userDetailsService
                .loadUserByUsername(commentDTO.getAuthor()));
        return comment;
    }

    @Override
    public CommentDTO toDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setText(comment.getText());
        commentDTO.setId(comment.getId());
        commentDTO.setTask(comment.getTask().getId());
        commentDTO.setAuthor(comment.getAuthor().getUsername());
        return commentDTO;
    }
}
