package fernando.iribarra.vehicle_service.controllers;

import fernando.iribarra.vehicle_service.entities.VehicleEntity;
import fernando.iribarra.vehicle_service.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/vehicle")
public class VehicleController {
    @Autowired
    VehicleService vehicleService;

    @GetMapping("/{id}")
    public ResponseEntity<VehicleEntity> getVehicleById(@PathVariable Long id) {
        VehicleEntity vehicle = vehicleService.getVehicleById(id);
        return ResponseEntity.ok(vehicle);
    }

    @GetMapping("/")
    public ResponseEntity<List<VehicleEntity>> getAllVehicles() {
        List<VehicleEntity> vehicles = vehicleService.getAllVehicles();
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/vehicletypes")
    public ResponseEntity<List<String>> getAllVehicleTypes() {
        List<String> vehicleTypes = vehicleService.getAllVehicleTypes();
        return ResponseEntity.ok(vehicleTypes);
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<VehicleEntity>> getAllVehiclesWithBrand(@PathVariable String brand) {
        List<VehicleEntity> vehicles = vehicleService.getVehiclesByBrand(brand);
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<VehicleEntity>> getAllVehiclesWithType(@PathVariable String type) {
        List<VehicleEntity> vehicles = vehicleService.getVehiclesByType(type);
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/{id}/values")
    public ResponseEntity<List<Long>> getFormulaValues(@PathVariable Long id) {
        List<Long> values = vehicleService.getFormulaValues(id);
        return ResponseEntity.ok(values);
    }

    @PostMapping("/")
    public ResponseEntity<VehicleEntity> saveVehicle(@RequestBody VehicleEntity vehicle) {
        VehicleEntity newVehicle = vehicleService.saveVehicle(vehicle);
        return ResponseEntity.ok(newVehicle);
    }

    @PutMapping("/")
    public ResponseEntity<VehicleEntity> updateVehicle(@RequestBody VehicleEntity vehicle) {
        VehicleEntity updatedVehicle = vehicleService.updateVehicle(vehicle);
        return ResponseEntity.ok(updatedVehicle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteVehicle(@PathVariable Long id) throws Exception {
        var isDeleted = vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }
}
