package fernando.iribarra.vehicle_service.services;

import fernando.iribarra.vehicle_service.entities.VehicleEntity;
import fernando.iribarra.vehicle_service.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
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
        values.add(restTemplate.getForObject("http://repair-service/api/v1/repair/repNumbDisc/100", Long.class, body));
        values.add(restTemplate.getForObject("http://repair-service/api/v1/repair/bonusDisc/false", Long.class, body));
        values.add(restTemplate.getForObject("http://repair-service/api/v1/repair/antiquityRech/100", Long.class, body));
        values.add(restTemplate.getForObject("http://repair-service/api/v1/repair/mileageRech/100", Long.class, body));
        //values.add(repairService.repairNumberDiscount(vehicle, 100));
        //values.add(repairService.bonusDiscount(vehicle,false));
        //values.add(repairService.antiquityRecharge(vehicle, 100));
        //values.add(repairService.mileageRecharge(vehicle, 100));
        return values;
    }

    public List<VehicleEntity> getAllVehicles() { return vehicleRepository.findAll(); }

    public List<VehicleEntity> getVehiclesByBrand(String brand) { return vehicleRepository.findAllByBrand(brand); }

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
