package simple.task.planner.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import simple.task.planner.dto.CommentDTO;
import simple.task.planner.services.CommentService;
import simple.task.planner.utils.InvalidCommentException;

import java.util.List;

@RestController
@RequestMapping("/comments")
@Tag(name = "Контроллер для комментариев",
        description = "Позволяет получать, удалять, добавлять и менять комментарии")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @GetMapping("/getByTask")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Получение списка комментариев",
        description = "Передавая id задачи можно получить все его комментарии")
    public HttpEntity<List<CommentDTO>> getByTask(
            @RequestParam @Parameter(description = "Идентификатор задачи") int taskId) {
        return new HttpEntity<>(commentService.getByTask(taskId));
    }

    @GetMapping("/getByAuthor")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Получение списка комментариев",
            description = "Передавая email автора можно получить все его комментарии")
    public HttpEntity<List<CommentDTO>> getByAuthor(
            @RequestParam @Parameter(description = "Email автора комментариев")String authorEmail) {
        return new HttpEntity<>(commentService.getByAuthor(authorEmail));
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавление комментария",
            description = "Добавляет комментарий к задаче если указать его текст и id задачи ")
    public HttpEntity<String> addComment(@RequestBody @Valid CommentDTO commentDTO,
                                         BindingResult bindingResult) {
        checkBindingResultOnErrors(bindingResult);
        commentService.add(commentDTO);
        return new HttpEntity<>("Comment was added");
    }

    @PatchMapping("/update")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Обновление комментария",
            description = "Нужно передать id существующего комментария и обновленный текст")
    public HttpEntity<String> updateComment(@RequestBody @Valid CommentDTO commentDTO,
                                            BindingResult bindingResult) throws Exception {
        checkBindingResultOnErrors(bindingResult);
        commentService.update(commentDTO);
        return new HttpEntity<>("Comment was updated");
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Удаление комментария",
            description = "Удаляет комментарий с переданным id")
    public HttpEntity<String> deleteComment(
            @RequestParam @Parameter(description = "Идентификатор комментария") int commentId) {
        commentService.delete(commentId);
        return new HttpEntity<>("Comment was deleted");
    }

    public void checkBindingResultOnErrors(BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            StringBuilder message = new StringBuilder();
            message.append("message: ");
            for (FieldError error: bindingResult.getFieldErrors()) {
                message.append(error.getField());
                message.append(" - ");
                message.append(error.getDefaultMessage());
                message.append("; ");
            }
            throw new InvalidCommentException(message.toString());
        }
    }
}
