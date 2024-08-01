package fernando.iribarra.report_service.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "report2")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report2Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    private Long operationType;
    private String month;
    private Long count;
    private Long amount;
}
