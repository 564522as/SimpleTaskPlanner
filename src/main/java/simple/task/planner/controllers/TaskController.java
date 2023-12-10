package simple.task.planner.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import simple.task.planner.dto.TaskDTO;
import simple.task.planner.services.TaskService;
import simple.task.planner.utils.InvalidTaskException;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Контроллер для задач",
        description = "Позволяет управлять задачами")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Получение списка задач",
            description = "Возвращает все имеющиеся задачи, чтобы вернуть только часть нужно указать страницу и ее размер")
    public HttpEntity<List<TaskDTO>> getTasksWithPagination(
            @RequestParam(required = false) @Parameter(description = "Номер страницы") @Min(1) Integer page,
            @RequestParam(required = false) @Parameter(description = "Размер одной страницы")@Min(1) @Max(15) Integer size
    ) {
        if (page != null && size != null) {
            return new HttpEntity<>(this.taskService.getAll(page, size));
        }
        return new HttpEntity<>(this.taskService.getAll());
    }

    @GetMapping("/getExecutableTasks")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Получение списка исполняемых задач текущего пользователя",
                description = "Позволяет получить список исполняемых задач, чтобы вернуть часть нужно указать страницу и ее номер")
    public HttpEntity<List<TaskDTO>> getExecutableTasksWithPagination(
            @RequestParam(required = false) @Parameter(description = "Номер страницы") @Min(1) Integer page,
            @RequestParam(required = false) @Parameter(description = "Размер одной страницы")@Min(1) @Max(15) Integer size
    ) {
        if (page != null && size != null) {
            return new HttpEntity<>(this.taskService.getExecutableTasks(page, size));
        }
        return new HttpEntity<>(this.taskService.getExecutableTasks());
    }

    @GetMapping("/getRelatedTasks")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Получение списка задач созданных текущим пользователем",
            description = "Позволяет получить список задач, чтобы вернуть часть нужно указать страницу и ее номер")
    public HttpEntity<List<TaskDTO>> getRelatedTasksWithPagination(
            @RequestParam(required = false) @Parameter(description = "Номер страницы") @Min(1) Integer page,
            @RequestParam(required = false) @Parameter(description = "Размер одной страницы")@Min(1) @Max(15) Integer size
    ) {
        if (page != null && size != null) {
            return new HttpEntity<>(this.taskService.getRelatedTasks(page, size));
        }
        return new HttpEntity<>(this.taskService.getRelatedTasks());
    }

    @GetMapping("/getByExecutor")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Получение списка задач созданных пользователем",
            description = "Позволяет получить список задач указав email пользователя, чтобы вернуть часть нужно указать страницу и ее номер")
    public HttpEntity<List<TaskDTO>> getByExecutorWithPagination(
            @RequestParam @Parameter(description = "Email исполнителя задач") String executorEmail,
            @RequestParam(required = false) @Parameter(description = "Номер страницы") @Min(1) Integer page,
            @RequestParam(required = false) @Parameter(description = "Размер одной страницы")@Min(1) @Max(15) Integer size
            ) {
        if (page != null && size != null) {
            return new HttpEntity<>(this.taskService.getByExecutor(executorEmail, page, size));
        }
        return new HttpEntity<>(this.taskService.getByExecutor(executorEmail));
    }

    @GetMapping("/getByAuthor")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Получение списка задач созданных пользователем",
            description = "Позволяет получить список задач пользователя по его email, чтобы вернуть часть нужно указать страницу и ее номер")
    public HttpEntity<List<TaskDTO>> getByAuthorWithPagination(
            @RequestParam @Parameter(description = "Email автора задач") String authorEmail,
            @RequestParam(required = false) @Parameter(description = "Номер страницы") @Min(1) Integer page,
            @RequestParam(required = false) @Parameter(description = "Размер одной страницы")@Min(1) @Max(15) Integer size
    ) {
        if (page != null && size != null) {
            return new HttpEntity<>(this.taskService.getByAuthor(authorEmail, page, size));
        }
        return new HttpEntity<>(this.taskService.getByAuthor(authorEmail));
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Сохранение задачи",
            description = "Сохранение отправленной задачи")
    public HttpEntity<String> saveTask(@RequestBody @Valid TaskDTO task,
                                       BindingResult bindingResult) {
        checkBindingResultOnErrors(bindingResult);
        this.taskService.save(task);
        return new HttpEntity<>("Task was saved");
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Удаление задачи по его id",
            description = "Позволяет удалять задачу по его id")
    public HttpEntity<String> deleteTask(
            @RequestParam(name = "id") @Parameter(description = "Идентификатор удаляемой задачи") Integer id) {
        this.taskService.deleteById(id);
        return new HttpEntity<>("Task was deleted");
    }

    @PatchMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Обновление задачи",
            description = "При указании id имеющейся задачи и новых данных сохраняет их")
    public HttpEntity<String> patchTask(@RequestBody @Valid TaskDTO task,
                                        BindingResult bindingResult) throws Exception {
        checkBindingResultOnErrors(bindingResult);
        this.taskService.update(task);
        return new HttpEntity<>("Task was updated");
    }

    @PatchMapping("/changeStatus")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Изменение статуса задачи",
            description = "Чтобы изменить статус задачи, необходимо быть ее исполнителем и указать новый статус и id задачи")
    public HttpEntity<String> changeStatus(@RequestBody TaskDTO task) throws Exception {
        this.taskService.setStatus(task.getId(), task.getStatus());
        return new HttpEntity<>("Status was updated");
    }

    @PatchMapping("/setExecutor")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Изменение исполнителя задачи",
            description = "Чтобы поменять исполнителя задачи нужно быть ее автором и указать id задачи и email исполнителя")
    public HttpEntity<String> setExecutor(@RequestBody TaskDTO task) {
        this.taskService.setExecutor(task.getId(), task.getExecutor());
        return new HttpEntity<>("Executor was updated");
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
            throw new InvalidTaskException(message.toString());
        }
    }
}
