package fernando.iribarra.vehicle_service.repositories;

import fernando.iribarra.vehicle_service.entities.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {

    @Query(value = "SELECT * FROM vehicles WHERE brand = :brand", nativeQuery = true)
    public List<VehicleEntity> findAllByBrand(@Param("brand") String brand);

    @Query(value = "SELECT * FROM vehicles WHERE type = :type", nativeQuery = true)
    public List<VehicleEntity> findAllByType(@Param("type") String brand);

    @Query(value = "SELECT DISTINCT type FROM vehicles", nativeQuery = true)
    public List<String> findAllVehicleTypes();

}
