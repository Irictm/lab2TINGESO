package fernando.iribarra.repair_list_service.repositories;

import fernando.iribarra.repair_list_service.entities.RepairListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RepairListRepository extends JpaRepository<RepairListEntity, Long> {

    @Query(value = "SELECT price FROM repair_list WHERE operation_type = :type AND motor_type = :motor", nativeQuery = true)
    public Long getBaseCost(@Param("type") int type, @Param("motor") String motor);

}
