package simple.task.planner.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "Tasks")
public class Task {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "title")
    @NotBlank(message = "Task must have a title")
    @Size(min = 5, max = 20, message = "Size of title must be between 5 and 20")
    private String title;
    @Column(name = "description")
    @NotBlank(message = "Task must have a description")
    @Size(min = 2, max = 300, message = "Size of description must be between 2 and 300")
    private String description;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Task must have a status")
    private TaskStatus status;
    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Task must have a priority")
    private TaskPriority priority;
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @NotNull(message = "Author of task must be pointed")
    private User author;
    @ManyToOne
    @JoinColumn(name = "executor_id", referencedColumnName = "id")
    @NotNull(message = "Executor of task must be pointed")
    private User executor;

    @OneToMany(mappedBy = "task", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    public java.lang.String getTitle() {
        return title;
    }

    public void setTitle(java.lang.String title) {
        this.title = title;
    }

    public java.lang.String getDescription() {
        return description;
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getExecutor() {
        return executor;
    }

    public void setExecutor(User executor) {
        this.executor = executor;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }
}
