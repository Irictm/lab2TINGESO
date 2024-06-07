package fernando.iribarra.repair_list_service.repositories;

import fernando.iribarra.repair_list_service.entities.RepairListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepairListRepository extends JpaRepository<RepairListEntity, Long> {

    @Query(value = "SELECT * FROM repair_list WHERE type = :type AND motorType = :motor", nativeQuery = true)
    public Long getBaseCost(@Param("type") int type, @Param("motor") String motor);

}
