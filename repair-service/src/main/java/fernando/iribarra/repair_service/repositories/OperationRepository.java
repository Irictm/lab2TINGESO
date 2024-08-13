package fernando.iribarra.repair_service.repositories;

import fernando.iribarra.repair_service.entities.OperationEntity;
import fernando.iribarra.repair_service.entities.RepairEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<OperationEntity, Long> {
    @Query(value="SELECT * FROM operations WHERE id_repair = :id_repair", nativeQuery = true)
    public List<OperationEntity> findOperationsByRepair(@Param("id_repair") Long id_repair);

    @Query(value="SELECT * FROM repair WHERE id = :id_repair", nativeQuery = true)
    public RepairEntity findOperationsRepair(@Param("id_repair") Long id_repair);

    @Modifying
    @Query(value="DELETE FROM operations WHERE id_repair = :id_repair", nativeQuery = true)
    public void deleteOperationsByRepair(@Param("id_repair") Long id_repair);
}
