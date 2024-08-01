package fernando.iribarra.report_service.services;

import fernando.iribarra.report_service.entities.Report2Entity;
import fernando.iribarra.report_service.repositories.Report2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Report2Service {
    @Autowired
    Report2Repository report2Repository;

    public Report2Entity saveReport2(Report2Entity report2) { return report2Repository.save(report2); }

    public Report2Entity getReport2ById(Long id) { return report2Repository.findById(id).get(); }

    public List<Report2Entity> getAllReport2() { return report2Repository.findAll(); }

    public Report2Entity updateReport2(Report2Entity report2) { return report2Repository.save(report2); }

    public boolean deleteReport2(Long id) throws Exception {
        try {
            report2Repository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}