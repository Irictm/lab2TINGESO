package fernando.iribarra.report_service.controllers;

import fernando.iribarra.report_service.entities.Report1Entity;
import fernando.iribarra.report_service.services.Report1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/report1")
public class Report1Controller {
    @Autowired
    Report1Service report1Service;

    @GetMapping("/{id}")
    public ResponseEntity<Report1Entity> getReport1ById(@PathVariable Long id) {
        Report1Entity report1 = report1Service.getReport1ById(id);
        return ResponseEntity.ok(report1);
    }

    @GetMapping("/")
    public ResponseEntity<List<Report1Entity>> getAllReport1() {
        List<Report1Entity> report1es = report1Service.getAllReport1();
        return ResponseEntity.ok(report1es);
    }

    @GetMapping("/reportforallcartypes/{optype}")
    public ResponseEntity<List<Long>> getFinalReport1ForAllCarTypes(@PathVariable Long optype) {
        List<Long> finalReport1 = report1Service.getFinalReportForAllCarTypes(optype);
        return ResponseEntity.ok(finalReport1);
    }

    @PostMapping("/")
    public ResponseEntity<Report1Entity> saveReport1(@RequestBody Report1Entity report1) {
        Report1Entity newReport1 = report1Service.saveReport1(report1);
        return ResponseEntity.ok(newReport1);
    }

    @PutMapping("/")
    public ResponseEntity<Report1Entity> updateReport1(@RequestBody Report1Entity report1) {
        Report1Entity updatedReport1 = report1Service.updateReport1(report1);
        return ResponseEntity.ok(updatedReport1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteReport1(@PathVariable Long id) throws Exception {
        var isDeleted = report1Service.deleteReport1(id);
        return ResponseEntity.noContent().build();
    }
}
