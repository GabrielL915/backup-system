package api.backup_system.domain.repository;

import api.backup_system.domain.entities.File;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends CRUDRepository<File, Long> {
}
