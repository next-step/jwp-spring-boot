package myblog.audit.domain;

import org.springframework.data.repository.CrudRepository;

public interface AuditRepository extends CrudRepository<Audit, Long> {
}
