package fernando.iribarra.repair_list_service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "repairList")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepairListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    private Integer operationType;
    private String motorType;
    private Long price;
    private String name;
}
