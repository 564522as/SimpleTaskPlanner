package simple.task.planner.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import simple.task.planner.dto.TaskDTO;
import simple.task.planner.entities.Task;
import simple.task.planner.entities.TaskStatus;
import simple.task.planner.entities.User;
import simple.task.planner.mappers.EntityAndDTOMapper;
import simple.task.planner.repositories.TaskRepository;
import simple.task.planner.utils.DeniedAccessToObjectException;
import simple.task.planner.utils.TaskNotExistException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserDetailsService userDetailsService;
    private final EntityAndDTOMapper<Task, TaskDTO> taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, UserDetailsService userDetailsService,
                           EntityAndDTOMapper<Task, TaskDTO> taskMapper) {
        this.taskRepository = taskRepository;
        this.userDetailsService = userDetailsService;
        this.taskMapper = taskMapper;
    }

    @Override
    public void save(TaskDTO taskDTO) {
        taskDTO.setAuthor(getCurrentUser().getUsername());
        Task task = taskMapper.toEntity(taskDTO);
        this.taskRepository.save(task);
    }

    @Override
    public void update(TaskDTO taskDTO) throws Exception {
        Optional<Task> taskOptional = this.taskRepository.findById(taskDTO.getId());
        if (taskOptional.isPresent()) {
            String userName = taskOptional.get().getAuthor().getUsername();
            String currentUserName = getCurrentUser().getUsername();
            if (!userName.equals(currentUserName)) {
                throw new DeniedAccessToObjectException("You can't update this task");
            }
        } else {
            throw new TaskNotExistException("Task with this id not exist");
        }
        this.taskRepository.save(taskOptional.get());
    }

    @Override
    public List<TaskDTO> getAll() {
        return tasksToDTOs(this.taskRepository.findAll());
    }

    @Override
    public List<TaskDTO> getAll(int page, int size) {
        List<Task> tasks = this.taskRepository.findAll(
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "status"))).getContent();
        return tasksToDTOs(tasks);
    }

    @Override
    public TaskDTO getById(int id) {
        Optional<Task> optionalTask = this.taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            return taskMapper.toDTO(optionalTask.get());
        } else {
            throw new TaskNotExistException("Task with this id not exist");
        }
    }

    @Override
    public void deleteById(int id) {
        Optional<Task> optionalTask = this.taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            String taskAuthor = optionalTask.get().getAuthor().getUsername();
            String currentUser = getCurrentUser().getUsername();
            if (!taskAuthor.equals(currentUser)) {
                throw new DeniedAccessToObjectException("Task has another author");
            }
        } else {
            throw new TaskNotExistException("Task with this id not exist");
        }
    }

    @Override
    public List<TaskDTO> getRelatedTasks() {
        return tasksToDTOs(taskRepository.findByAuthor(getCurrentUser().getId()));
    }

    @Override
    public List<TaskDTO> getExecutableTasks() {
        return tasksToDTOs(taskRepository.findByAuthor(getCurrentUser().getId()));
    }

    @Override
    public List<TaskDTO> getByExecutor(String executorEmail) {
        return tasksToDTOs(taskRepository.findByExecutor(executorEmail));
    }

    @Override
    public List<TaskDTO> getByAuthor(String authorEmail) {
        return tasksToDTOs(taskRepository.findByAuthor(authorEmail));
    }

    @Override
    public List<TaskDTO> getRelatedTasks(int page, int size) {
        int currentUserId = getCurrentUser().getId();
        List<Task> tasks = this.taskRepository.findByAuthor(currentUserId,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "status"))).getContent();
        return tasksToDTOs(tasks);
    }

    @Override
    public List<TaskDTO> getExecutableTasks(int page, int size) {
        int currentUserId = getCurrentUser().getId();
        List<Task> tasks = this.taskRepository.findByExecutor(currentUserId,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "status"))).getContent();
        return tasksToDTOs(tasks);
    }

    @Override
    public List<TaskDTO> getByExecutor(String executorEmail, int page, int size) {
        List<Task> tasks = this.taskRepository.findByExecutor(executorEmail,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "status"))).getContent();
        return tasksToDTOs(tasks);
    }

    @Override
    public List<TaskDTO> getByAuthor(String authorEmail, int page, int size) {
        List<Task> tasks = this.taskRepository.findByAuthor(authorEmail,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "status"))).getContent();
        return tasksToDTOs(tasks);
    }

    @Override
    public void setStatus(int taskId, String status) throws Exception {
        Optional<Task> task = this.taskRepository.findById(taskId);
        if (task.isPresent()) {
            int currentUserId = getCurrentUser().getId();

            if (task.get().getExecutor().getId() == currentUserId) {
                task.get().setStatus(TaskStatus.valueOf(status));
            } else {
                throw new DeniedAccessToObjectException("You're not executor of this task");
            }
        } else {
            throw new TaskNotExistException("Task with this id not exist");
        }
    }

    @Override
    public void setExecutor(int taskId, String executorEmail) {
        Optional<Task> task = this.taskRepository.findById(taskId);
        User user = (User)this.userDetailsService.loadUserByUsername(executorEmail);

        if (task.isPresent()) {
            task.get().setExecutor(user);
            taskRepository.save(task.get());
        } else {
            throw new TaskNotExistException("Task with this id not present");
        }
    }

    public static User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return  ((User)auth.getPrincipal());
    }

    public List<TaskDTO> tasksToDTOs(List<Task> tasks) {
        List<TaskDTO> taskDTOS = new ArrayList<>();
        for (Task task: tasks) {
            taskDTOS.add(taskMapper.toDTO(task));
        }

        return taskDTOS;
    }
}
