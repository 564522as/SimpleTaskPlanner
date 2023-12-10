package simple.task.planner.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import simple.task.planner.dto.ErrorBody;
import simple.task.planner.utils.*;

@RestControllerAdvice
@Tag(name = "Контроллер обработки ошибок",
        description = "Возвращает пользователю описание появляющихся ошибок")
public class ExceptionHandlingController {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpEntity<ErrorBody> handleCommentExceptions(InvalidCommentException e) {
        ErrorBody errorBody = new ErrorBody(e.getMessage(), System.currentTimeMillis());
        return new HttpEntity<>(errorBody);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpEntity<ErrorBody> handleTaskExceptions(InvalidTaskException e) {
        ErrorBody errorBody = new ErrorBody(e.getMessage(), System.currentTimeMillis());
        return new HttpEntity<>(errorBody);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpEntity<ErrorBody> handleUserExceptions(UsernameNotFoundException e) {
        ErrorBody errorBody = new ErrorBody(e.getMessage(), System.currentTimeMillis());
        return new HttpEntity<>(errorBody);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpEntity<ErrorBody> handleCommentExceptions(CommentNotExistException e) {
        ErrorBody errorBody = new ErrorBody(e.getMessage(), System.currentTimeMillis());
        return new HttpEntity<>(errorBody);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpEntity<ErrorBody> handleTaskExceptions(TaskNotExistException e) {
        ErrorBody errorBody = new ErrorBody(e.getMessage(), System.currentTimeMillis());
        return new HttpEntity<>(errorBody);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpEntity<ErrorBody> handleAccessExceptions(DeniedAccessToObjectException e) {
        ErrorBody errorBody = new ErrorBody(e.getMessage(), System.currentTimeMillis());
        return new HttpEntity<>(errorBody);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpEntity<ErrorBody> handleAuthExceptions(AuthenticationCredentialsNotFoundException e) {
        ErrorBody errorBody = new ErrorBody(e.getMessage(), System.currentTimeMillis());
        return new HttpEntity<>(errorBody);
    }
}
