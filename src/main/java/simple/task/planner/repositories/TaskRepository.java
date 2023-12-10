package simple.task.planner.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import simple.task.planner.entities.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Query("SELECT t FROM Task t WHERE t.author.id = :id")
    List<Task> findByAuthor(int id);
    @Query("SELECT t FROM Task t WHERE t.executor.id = :id")
    List<Task> findByExecutor(int id);
    @Query("SELECT t FROM Task t WHERE t.author.email = :email")
    List<Task> findByAuthor(String email);
    @Query("SELECT t FROM Task t WHERE t.executor.email = :email")
    List<Task> findByExecutor(String email);

    Page<Task> findAll(@NonNull Pageable pageable);
    @Query("SELECT t FROM Task t WHERE t.author.id = :id")
    Page<Task> findByAuthor(int id, Pageable pageable);
    @Query("SELECT t FROM Task t WHERE t.executor.id = :id")
    Page<Task> findByExecutor(int id, Pageable pageable);
    @Query("SELECT t FROM Task t WHERE t.author.email = :email")
    Page<Task> findByAuthor(String email, Pageable pageable);
    @Query("SELECT t FROM Task t WHERE t.executor.email = :email")
    Page<Task> findByExecutor(String email, Pageable pageable);

}
