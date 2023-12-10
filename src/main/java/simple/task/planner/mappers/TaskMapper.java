package simple.task.planner.mappers;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import simple.task.planner.dto.TaskDTO;
import simple.task.planner.entities.Task;
import simple.task.planner.entities.TaskPriority;
import simple.task.planner.entities.TaskStatus;
import simple.task.planner.entities.User;

@Component
public class TaskMapper implements EntityAndDTOMapper<Task, TaskDTO>{
    private final UserDetailsService userDetailsService;

    public TaskMapper(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Task toEntity(TaskDTO taskDTO) {
        Task task = new Task();
        task.setId(taskDTO.getId());
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(TaskStatus.valueOf(taskDTO.getStatus()));
        task.setPriority(TaskPriority.valueOf(taskDTO.getPriority()));
        task.setExecutor((User)userDetailsService
                .loadUserByUsername(taskDTO.getExecutor()));
        task.setAuthor((User) userDetailsService
                .loadUserByUsername(taskDTO.getAuthor()));
        return task;
    }

    @Override
    public TaskDTO toDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setStatus(String.valueOf(task.getStatus()));
        taskDTO.setPriority(String.valueOf(task.getPriority()));
        taskDTO.setExecutor(task.getExecutor().getUsername());
        taskDTO.setAuthor(task.getAuthor().getUsername());
        return taskDTO;
    }
}
