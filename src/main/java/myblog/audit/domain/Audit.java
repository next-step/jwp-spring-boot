package myblog.audit.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventType eventType;

    @Column(nullable = false)
    private LocalDateTime createdDateTime = LocalDateTime.now();

    public Audit() {
    }

    public Audit(EventType eventType) {
        this.eventType = eventType;
    }
}
