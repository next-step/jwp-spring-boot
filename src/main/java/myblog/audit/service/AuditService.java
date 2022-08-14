package myblog.audit.service;

import myblog.audit.domain.Audit;
import myblog.audit.domain.AuditRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuditService {
    private AuditRepository auditRepository;

    public AuditService(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void auditEvent(Audit audit) {
        auditRepository.save(audit);
    }
}
