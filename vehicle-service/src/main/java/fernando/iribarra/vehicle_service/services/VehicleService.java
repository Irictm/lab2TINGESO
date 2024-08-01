package fernando.iribarra.vehicle_service.services;

import fernando.iribarra.vehicle_service.entities.VehicleEntity;
import fernando.iribarra.vehicle_service.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleService {
    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    RestTemplate restTemplate;

    public VehicleEntity saveVehicle(VehicleEntity vehicle) { return vehicleRepository.save(vehicle);}

    public VehicleEntity getVehicleById(Long id) { return vehicleRepository.findById(id).get(); }

    public List<Long> getFormulaValues(Long id) {
        VehicleEntity vehicle = getVehicleById(id);
        List<Long> values = new ArrayList<>();
        HttpEntity<VehicleEntity> body = new HttpEntity<VehicleEntity>(vehicle);
        values.add(restTemplate.postForObject("http://repair-service/api/v1/repair/repNumbDisc/100", body, Long.class));
        values.add(restTemplate.postForObject("http://repair-service/api/v1/repair/bonusDisc/false", body, Long.class));
        values.add(restTemplate.postForObject("http://repair-service/api/v1/repair/antiquityRech/100", body, Long.class));
        values.add(restTemplate.postForObject("http://repair-service/api/v1/repair/mileageRech/100", body, Long.class));
        //values.add(repairService.repairNumberDiscount(vehicle, 100));
        //values.add(repairService.bonusDiscount(vehicle,false));
        //values.add(repairService.antiquityRecharge(vehicle, 100));
        //values.add(repairService.mileageRecharge(vehicle, 100));
        return values;
    }

    public List<VehicleEntity> getAllVehicles() { return vehicleRepository.findAll(); }

    public List<String> getAllVehicleTypes() {
        List<String> types = vehicleRepository.findAllVehicleTypes();
        if (!types.contains("Sedan")) {
            types.add("Sedan");
        }
        if (!types.contains("Hatchback")) {
            types.add("Hatchback");
        }
        if (!types.contains("SUV")) {
            types.add("SUV");
        }
        if (!types.contains("Pickup")) {
            types.add("Pickup");
        }
        if (!types.contains("Furgoneta")) {
            types.add("Furgoneta");
        }
        return types;
    }

    public List<VehicleEntity> getVehiclesByBrand(String brand) { return vehicleRepository.findAllByBrand(brand); }

    public List<VehicleEntity> getVehiclesByType(String type) { return vehicleRepository.findAllByType(type); }

    public VehicleEntity updateVehicle(VehicleEntity vehicle) {return vehicleRepository.save(vehicle);}

    public boolean deleteVehicle(Long id) throws Exception {
        try {
            vehicleRepository.deleteById(id);
            restTemplate.delete("http://repair-service/api/v1/repair/withVehicle/" + id);
            //repairService.deleteRepairsWithVehicle(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
