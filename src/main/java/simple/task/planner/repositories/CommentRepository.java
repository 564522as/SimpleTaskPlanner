package simple.task.planner.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import simple.task.planner.entities.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query("SELECT c FROM Comment c WHERE c.task.id = :taskId")
    List<Comment> findByTask(int taskId);

    @Query("SELECT c FROM Comment c WHERE c.author.id = :authorId")
    List<Comment> findByAuthor(int authorId);
}
