package simple.task.planner.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
@Schema(description = "Сущность комментариев")
public class CommentDTO {
    @Schema(accessMode = Schema.AccessMode.AUTO)
    private Integer id;
    @NotBlank(message = "Comment must have a text")
    @Size(min = 2, max = 200, message = "Text of comment must have size between 2 and 200")
    @Schema(description = "Текст комментария")
    private String text;
    @NotNull(message = "Comment must have related task")
    @Schema(description = "Идентификатор комментируемой задачи")
    private Integer task;
    @NotNull(message = "Author of author must be pointed")
    @Email
    @Schema(description = "Email автора комментария")
    private String author;


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

    public int getTask() {
        return task;
    }

    public void setTask(int task) {
        this.task = task;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
