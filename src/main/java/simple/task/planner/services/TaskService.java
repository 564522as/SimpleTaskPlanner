package simple.task.planner.services;

import simple.task.planner.dto.TaskDTO;

import java.util.List;

public interface TaskService {
    void save(TaskDTO task);

    void update(TaskDTO taskDTO) throws Exception;

    List<TaskDTO> getAll();
    List<TaskDTO> getAll(int page, int size);
    TaskDTO getById(int id);
    void deleteById(int id);
    List<TaskDTO> getRelatedTasks();
    List<TaskDTO> getExecutableTasks();
    List<TaskDTO> getByExecutor(String executorEmail);
    List<TaskDTO> getByAuthor(String authorEmail);
    List<TaskDTO> getRelatedTasks(int page, int size);
    List<TaskDTO> getExecutableTasks(int page, int size);
    List<TaskDTO> getByExecutor(String executorEmail, int page, int size);
    List<TaskDTO> getByAuthor(String authorEmail, int page, int size);
    void setStatus(int taskId, String status) throws Exception;
    void setExecutor(int taskId, String executorEmail);

}
