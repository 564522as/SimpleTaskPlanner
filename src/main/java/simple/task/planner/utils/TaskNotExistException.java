package simple.task.planner.utils;

public class TaskNotExistException extends RuntimeException{
    public TaskNotExistException(String message) {
        super(message);
    }
}
