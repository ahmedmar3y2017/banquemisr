package banquemisr.challenge05.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "TASK")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Task {

    @Id
    @Column(name = "TASK_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    @Column(name = "TITLE", nullable = false, unique = true, length = 50)
    private String title;

    @NonNull
    @Column(name = "DESCRIPTION", nullable = false, length = 100)
    private String description;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 64)
    private TaskStatus status;
    @NonNull
    @Column(name = "PRIORITY", nullable = false, length = 20)
    private int priority;
    @NonNull
    @Column(name = "DUE_DATE", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")

    @Temporal(TemporalType.DATE)
    private Date dueDate;

}
