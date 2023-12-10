package simple.task.planner.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "text")
    @NotBlank(message = "Text of comment must be filled in")
    private String text;
    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    @NotNull(message = "Must be pointed related task")
    private Task task;
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @NotNull(message = "Must be pointed author of comment")
    private User author;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
