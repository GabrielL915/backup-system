package api.backup_system.services.mappers;

public interface CRUDMapper<T, D> {

    D toDTO(T entity);

    T toEntity(D dto);
}
