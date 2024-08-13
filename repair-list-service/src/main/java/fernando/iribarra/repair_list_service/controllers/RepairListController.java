package fernando.iribarra.repair_list_service.controllers;
import fernando.iribarra.repair_list_service.entities.RepairListEntity;
import fernando.iribarra.repair_list_service.services.RepairListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/repairList")
public class RepairListController {
    @Autowired
    RepairListService repairListService;

    @GetMapping("/{id}")
    public ResponseEntity<RepairListEntity> getRepairListById(@PathVariable Long id) {
        RepairListEntity repairList = repairListService.getRepairListById(id);
        return ResponseEntity.ok(repairList);
    }

    @GetMapping("/")
    public ResponseEntity<List<RepairListEntity>> getRepairListById() {
        List<RepairListEntity> repairLists = repairListService.getAllRepairList();
        return ResponseEntity.ok(repairLists);
    }

    @GetMapping("/{opType}/{motor}")
    public ResponseEntity<Long> getBaseCost(@PathVariable int opType, @PathVariable String motor) {
        Long value = repairListService.getBaseCost(opType, motor);
        return ResponseEntity.ok(value);
    }

    @GetMapping("/alloptypes")
    public ResponseEntity<List<Map<String, String>>> getAllOperationTypes() {
        List<Map<String, String>> opTypes = repairListService.getAllOperationTypes();
        return ResponseEntity.ok(opTypes);
    }

    @PostMapping("/")
    public ResponseEntity<RepairListEntity> saveRepairList(@RequestBody RepairListEntity repairList) {
        RepairListEntity newRepairList = repairListService.saveRepairList(repairList);
        return ResponseEntity.ok(newRepairList);
    }

    @PutMapping("/")
    public ResponseEntity<RepairListEntity> updateRepairList(@RequestBody RepairListEntity repairList) {
        RepairListEntity updatedRepairList = repairListService.updateRepairList(repairList);
        return ResponseEntity.ok(updatedRepairList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteRepairList(@PathVariable Long id) throws Exception {
        var isDeleted = repairListService.deleteRepairList(id);
        return ResponseEntity.noContent().build();
    }
}