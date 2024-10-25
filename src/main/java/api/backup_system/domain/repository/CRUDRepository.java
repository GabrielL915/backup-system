package api.backup_system.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CRUDRepository<T, ID> extends JpaRepository<T, ID> {
}
