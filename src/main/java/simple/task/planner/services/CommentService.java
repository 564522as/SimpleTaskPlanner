package simple.task.planner.services;

import simple.task.planner.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    List<CommentDTO> getByTask(int taskId);
    List<CommentDTO> getByAuthor(String authorEmail);
    void add(CommentDTO commentDTO);
    void delete(int commentId);
    void update(CommentDTO commentDTO) throws Exception;

}
