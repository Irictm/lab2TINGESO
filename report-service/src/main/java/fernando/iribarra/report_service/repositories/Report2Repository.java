package fernando.iribarra.report_service.repositories;
import fernando.iribarra.report_service.entities.Report2Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Report2Repository extends JpaRepository<Report2Entity, Long>  {
}
