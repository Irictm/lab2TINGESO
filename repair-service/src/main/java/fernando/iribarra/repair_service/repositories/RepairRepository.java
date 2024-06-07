package fernando.iribarra.repair_service.repositories;

import fernando.iribarra.repair_service.entities.RepairEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepairRepository extends JpaRepository<RepairEntity, Long> {

    @Query(value = "SELECT * FROM repairs WHERE id_vehicle = :id_vehicle", nativeQuery = true)
    public List<RepairEntity> findVehicleRepairs(@Param("id_vehicle") Long id_vehicle);

    @Query(value = "SELECT * FROM repairs WHERE id_vehicle = :id_vehicle", nativeQuery = true)
    public List<RepairEntity> findAllByVehicleId(@Param("id_vehicle") Long id_vehicle);

    @Query(value = "SELECT r.id as id, r.date_of_admission as date_of_admission, r.total_amount as total_amount," +
            " r.date_of_release as date_of_release, r.date_of_pick_up as date_of_pick_up, r.id_vehicle as id_vehicle" +
            " FROM repairs r, operations o WHERE r.id = o.id_repair AND o.type = :typeOp", nativeQuery = true)
    public List<RepairEntity> findRepairsWithOperation(@Param("typeOp") int typeOp);

}
