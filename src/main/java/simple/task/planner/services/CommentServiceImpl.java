package simple.task.planner.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import simple.task.planner.dto.CommentDTO;
import simple.task.planner.entities.Comment;
import simple.task.planner.entities.User;
import simple.task.planner.mappers.CommentMapper;
import simple.task.planner.repositories.CommentRepository;
import simple.task.planner.utils.CommentNotExistException;
import simple.task.planner.utils.DeniedAccessToObjectException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserDetailsService userDetailsService;

    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper, UserDetailsService userDetailsService) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public List<CommentDTO> getByTask(int taskId) {
        List<CommentDTO> commentDTOS = new ArrayList<>();
        for(Comment comment: commentRepository.findByTask(taskId)) {
            commentDTOS.add(commentMapper.toDTO(comment));
        }
        return commentDTOS;
    }

    @Override
    public List<CommentDTO> getByAuthor(String authorEmail) {
        List<CommentDTO> commentDTOS = new ArrayList<>();
        User author = (User) userDetailsService.loadUserByUsername(authorEmail);
        for(Comment comment: commentRepository.findByAuthor(author.getId())) {
            commentDTOS.add(commentMapper.toDTO(comment));
        }
        return commentDTOS;
    }

    @Override
    public void add(CommentDTO commentDTO) {
        commentDTO.setAuthor(TaskServiceImpl.getCurrentUser().getUsername());
        this.commentRepository.save(commentMapper.toEntity(commentDTO));
    }

    @Override
    public void delete(int commentId) {
        Optional<Comment> comment = this.commentRepository.findById(commentId);
        if (comment.isPresent()) {
            String author = comment.get().getAuthor().getUsername();
            String currentUser = TaskServiceImpl.getCurrentUser().getUsername();
            if(!author.equals(currentUser)) {
                throw new DeniedAccessToObjectException("Comment has another author");
            }
        } else {
            throw new CommentNotExistException("Comment with this id not exist");
        }
        this.commentRepository.deleteById(commentId);
    }

    @Override
    public void update(CommentDTO commentDTO) throws Exception {
        User currentUser = TaskServiceImpl.getCurrentUser();
        Optional<Comment> originalComment = commentRepository.findById(commentDTO.getId());

        if (originalComment.isPresent()) {
            String originalCommentAuthor = originalComment.get().getAuthor().getUsername();
            commentDTO.setTask(originalComment.get().getTask().getId());
            commentDTO.setAuthor(originalCommentAuthor);
            if (!currentUser.getUsername().equals(originalCommentAuthor)) {
                throw new DeniedAccessToObjectException("You can't update this comment");
            }
        } else {
            throw new CommentNotExistException("Comment with this id not exist");
        }

        commentRepository.save(commentMapper.toEntity(commentDTO));
    }
}
