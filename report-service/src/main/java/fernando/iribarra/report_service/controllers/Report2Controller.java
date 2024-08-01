package fernando.iribarra.report_service.controllers;

import fernando.iribarra.report_service.entities.Report2Entity;
import fernando.iribarra.report_service.services.Report2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/report2")
public class Report2Controller {
    @Autowired
    Report2Service report2Service;

    @GetMapping("/{id}")
    public ResponseEntity<Report2Entity> getReport2ById(@PathVariable Long id) {
        Report2Entity report2 = report2Service.getReport2ById(id);
        return ResponseEntity.ok(report2);
    }

    @GetMapping("/")
    public ResponseEntity<List<Report2Entity>> getAllReport2() {
        List<Report2Entity> report2es = report2Service.getAllReport2();
        return ResponseEntity.ok(report2es);
    }

    @PostMapping("/")
    public ResponseEntity<Report2Entity> saveReport2(@RequestBody Report2Entity report2) {
        Report2Entity newReport2 = report2Service.saveReport2(report2);
        return ResponseEntity.ok(newReport2);
    }

    @PutMapping("/")
    public ResponseEntity<Report2Entity> updateReport2(@RequestBody Report2Entity report2) {
        Report2Entity updatedReport2 = report2Service.updateReport2(report2);
        return ResponseEntity.ok(updatedReport2);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteReport2(@PathVariable Long id) throws Exception {
        var isDeleted = report2Service.deleteReport2(id);
        return ResponseEntity.noContent().build();
    }
}