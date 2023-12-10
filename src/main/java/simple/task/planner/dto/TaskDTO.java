package simple.task.planner.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
@Schema(description = "Сущность задачи")
public class TaskDTO {
    @Schema(accessMode = Schema.AccessMode.AUTO)
    private Integer id;
    @NotBlank(message = "Task must have a title")
    @Size(min = 5, max = 20, message = "Size of title must be between 5 and 20")
    @Schema(description = "Название задачи")
    private String title;
    @NotBlank(message = "Task must have a description")
    @Size(min = 5, message = "Size of description must be more than 5")
    @Schema(description = "Описание задачи")
    private String description;
    @NotBlank(message = "Task must have a status")
    @Schema(description = "Статус задачи, может быть IN_WAITING, IN_PROCESS, COMPLETED")
    private String status;
    @NotBlank(message = "Task must have a priority")
    @Schema(description = "Приоритет задачи, может быть LOW, MEDIUM, HIGH")
    private String priority;
    @Schema(description = "Email автора задачи")
    private String author;
    @NotBlank(message = "Executor of the comment must be pointed")
    @Email
    @Schema(description = "Email исполнителя задачи")
    private String executor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }
}
