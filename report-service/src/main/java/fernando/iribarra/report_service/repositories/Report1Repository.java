package fernando.iribarra.report_service.repositories;
import fernando.iribarra.report_service.entities.Report1Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Report1Repository extends JpaRepository<Report1Entity, Long> {

    @Query(value = "SELECT * FROM report1 WHERE operation_type = :operationType AND vehicle_type = :vehicleType AND EXTRACT('MONTH' FROM date_of_report) = :month", nativeQuery = true)
    public Report1Entity existsByTypeAndMonth(@Param("operationType") Long operationType, @Param("vehicleType") String vehicleType, @Param("month") int month);

}
