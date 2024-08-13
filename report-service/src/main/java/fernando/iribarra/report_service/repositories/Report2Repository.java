package fernando.iribarra.report_service.repositories;
import fernando.iribarra.report_service.entities.Report1Entity;
import fernando.iribarra.report_service.entities.Report2Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Report2Repository extends JpaRepository<Report2Entity, Long>  {

    @Query(value = "SELECT * FROM report2 WHERE operation_type = :operationType AND EXTRACT('YEAR' FROM date_of_report) = :year AND EXTRACT('MONTH' FROM date_of_report) = :month", nativeQuery = true)
    public Report2Entity existsByTypeAndYearAndMonth(@Param("operationType") Long operationType, @Param("year") int year, @Param("month") int month);

}
