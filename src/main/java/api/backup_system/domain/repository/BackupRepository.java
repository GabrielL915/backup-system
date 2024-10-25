package api.backup_system.domain.repository;

import api.backup_system.domain.entities.Backup;
import org.springframework.stereotype.Repository;

@Repository
public interface BackupRepository extends CRUDRepository<Backup, Long> {
}
