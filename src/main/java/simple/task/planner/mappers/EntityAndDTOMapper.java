package simple.task.planner.mappers;

public interface EntityAndDTOMapper<D, T> {
    D toEntity(T t);
    T toDTO(D d);
}
