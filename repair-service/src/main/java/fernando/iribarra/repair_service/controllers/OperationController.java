package fernando.iribarra.repair_service.controllers;

import fernando.iribarra.repair_service.entities.OperationEntity;
import fernando.iribarra.repair_service.services.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/operation")
public class OperationController {
    @Autowired
    OperationService operationService;

    @GetMapping("/repair/{id_repair}")
    public ResponseEntity<List<OperationEntity>> getOperationsByRepair(@PathVariable Long id_repair) {
        List<OperationEntity> operations = operationService.getOperationsByRepair(id_repair);
        return ResponseEntity.ok(operations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperationEntity> getOperationById(@PathVariable Long id) {
        OperationEntity operation = operationService.getOperationById(id);
        return ResponseEntity.ok(operation);
    }

    @PostMapping("/")
    public ResponseEntity<OperationEntity> saveOperation(@RequestBody OperationEntity operation) {
        OperationEntity newOperation = operationService.saveOperation(operation);
        return ResponseEntity.ok(newOperation);
    }

    @PutMapping("/")
    public ResponseEntity<OperationEntity> updateOperation(@RequestBody OperationEntity operation) {
        OperationEntity updatedOperation = operationService.updateOperation(operation);
        return ResponseEntity.ok(updatedOperation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteOperation(@PathVariable Long id) throws Exception {
        var isDeleted = operationService.deleteOperation(id);
        return ResponseEntity.noContent().build();
    }
}
