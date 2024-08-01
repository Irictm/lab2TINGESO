package fernando.iribarra.repair_service.controllers;

import fernando.iribarra.repair_service.entities.RepairEntity;
import fernando.iribarra.repair_service.models.VehicleEntity;
import fernando.iribarra.repair_service.services.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/repair")
public class RepairController {
    @Autowired
    RepairService repairService;

    @GetMapping("/{id}")
    public ResponseEntity<RepairEntity> getRepairById(@PathVariable Long id) {
        RepairEntity repair = repairService.getRepairById(id);
        return ResponseEntity.ok(repair);
    }

    @GetMapping("/avgtime/{brand}")
    public ResponseEntity<Long> getAvgRepairTimeOfBrand(@PathVariable String brand) {
        Long avgtime = repairService.getAvgRepairTimeOfBrand(brand);
        return ResponseEntity.ok(avgtime);
    }

    @GetMapping("/")
    public ResponseEntity<List<RepairEntity>> getAllRepairs() {
        List<RepairEntity> repairs = repairService.getAllRepairs();
        return ResponseEntity.ok(repairs);
    }

    @GetMapping("/repairAndVehicleData/{id}")
    public ResponseEntity<Map<String, String>> getRepairAndVehicleData(@PathVariable Long id) {
        Map<String, String> values = repairService.getRepairAndVehicleData(id);
        return ResponseEntity.ok(values);
    }

    @GetMapping("/operationtype/{typeOp}")
    public ResponseEntity<List<Long>> getAllRepairsWithOpType(@PathVariable int typeOp) {
        List<Long> values = repairService.getAllRepairsWithOperationType(typeOp);
        return ResponseEntity.ok(values);
    }

    @GetMapping("/repairCountAndValue/{vehicleType}/{typeOp}")
    public ResponseEntity<List<Long>> getRepairCountAndValue(@PathVariable Long typeOp, @PathVariable String vehicleType) {
        List<Long> values = repairService.getRepairCountAndValue(vehicleType, typeOp);
        return ResponseEntity.ok(values);
    }

    @PostMapping("/repNumbDisc/{baseCost}")
    public ResponseEntity<Long> getRepairNumberDiscount(@PathVariable Long baseCost, @RequestBody VehicleEntity vehicle) {
        Long value = repairService.repairNumberDiscount(vehicle, baseCost);
        return ResponseEntity.ok(value);
    }

    @PostMapping("/bonusDisc/{consume}")
    public ResponseEntity<Long> getBonusDiscount(@PathVariable boolean consume, @RequestBody VehicleEntity vehicle) {
        Long value = repairService.bonusDiscount(vehicle, consume);
        return ResponseEntity.ok(value);
    }

    @PostMapping("/antiquityRech/{baseCost}")
    public ResponseEntity<Long> getAntiquityRecharge(@PathVariable Long baseCost, @RequestBody VehicleEntity vehicle) {
        Long value = repairService.antiquityRecharge(vehicle, baseCost);
        return ResponseEntity.ok(value);
    }

    @PostMapping("/mileageRech/{baseCost}")
    public ResponseEntity<Long> getMileageRecharge(@PathVariable Long baseCost, @RequestBody VehicleEntity vehicle) {
        Long value = repairService.mileageRecharge(vehicle, baseCost);
        return ResponseEntity.ok(value);
    }

    @GetMapping("/{id}/calculate")
    public ResponseEntity<RepairEntity> calculateRepairTotalAmount(@PathVariable Long id) {
        RepairEntity repair = repairService.getRepairById(id);
        repair = repairService.calculateTotalCost(repair);
        return ResponseEntity.ok(repair);
    }

    @PostMapping("/")
    public ResponseEntity<RepairEntity> saveRepair(@RequestBody RepairEntity repair) {
        RepairEntity newRepair = repairService.saveRepair(repair);
        return ResponseEntity.ok(newRepair);
    }

    @PutMapping("/")
    public ResponseEntity<RepairEntity> updateRepair(@RequestBody RepairEntity repair) {
        RepairEntity updatedRepair = repairService.updateRepair(repair);
        return ResponseEntity.ok(updatedRepair);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteRepair(@PathVariable Long id) throws Exception {
        var isDeleted = repairService.deleteRepair(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/withVehicle/{id_vehicle}")
    public ResponseEntity<Boolean> deleteRepairsWithVehicle(@PathVariable Long id_vehicle) throws Exception {
        var isDeleted = repairService.deleteRepairsWithVehicle(id_vehicle);
        return ResponseEntity.noContent().build();
    }
}
