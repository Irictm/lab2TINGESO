package fernando.iribarra.repair_service.services;

import fernando.iribarra.repair_service.entities.OperationEntity;
import fernando.iribarra.repair_service.entities.RepairEntity;
import fernando.iribarra.repair_service.models.VehicleEntity;
import fernando.iribarra.repair_service.repositories.RepairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RepairService {
    @Autowired
    RepairRepository repairRepository;

    @Autowired
    RestTemplate restTemplate;
    //@Autowired
    //VehicleService vehicleService;
    //@Autowired
    //BonusService bonusService;
    @Autowired
    OperationService operationService;

    public RepairEntity saveRepair(RepairEntity repair) {
        return repairRepository.save(repair);
    }

    public RepairEntity getRepairById(Long id) { return repairRepository.findById(id).get();}

    public List<RepairEntity> getAllRepairs() { return repairRepository.findAll(); }

    public List<Long> getAllRepairsWithOperationType(int typeOp) {
        OperationEntity opTemp = new OperationEntity(1L, "", typeOp, LocalDateTime.now(), 1L, 1L);
        List<RepairEntity> repairs = repairRepository.findRepairsWithOperation(typeOp);
        List<VehicleEntity> vehicles = new ArrayList<>();
        List<Long> values = new ArrayList<>();
        List<String> seenTypes = new ArrayList<>();
        Long seenNumber = 0L;
        Long cost = 0L;
        for (RepairEntity repair : repairs) {
            VehicleEntity vehicle = restTemplate.getForObject("http://vehicle-service/api/v1/vehicle/" + repair.getId_vehicle(), VehicleEntity.class);
            //VehicleEntity vehicle = vehicleService.getVehicleById(repair.getId_vehicle());
            if (!(seenTypes.contains(vehicle.getType()))) {
                seenNumber += 1L;
                seenTypes.add(vehicle.getType());
                cost += operationService.calculateBaseCost(opTemp, vehicle.getMotorType());
            }
        }
        values.add(seenNumber);
        values.add(cost);
        return values;
    }

    public RepairEntity updateRepair(RepairEntity repair) { return repairRepository.save(repair);}

    public RepairEntity calculateTotalCost(RepairEntity repair) {
        long totalCost = 0L;
        float IVA = 0.19f;

        VehicleEntity vehicle = restTemplate.getForObject("http://vehicle-service/api/v1/vehicle/" + repair.getId_vehicle(), VehicleEntity.class);
        //VehicleEntity vehicle = vehicleService.getVehicleById(repair.getId_vehicle());
        if (vehicle == null) {
            System.out.print("ERROR, Vehiculo asociado a reparacion no existe.");
            return repair;
        }

        long baseCost = operationService.calculateTotalRepairBaseCost(repair.getId(), vehicle.getMotorType());

        totalCost += baseCost;
        totalCost += mileageRecharge(vehicle, baseCost) + antiquityRecharge(vehicle, baseCost) + delayRecharge(repair, baseCost);
        totalCost -= repairNumberDiscount(vehicle, baseCost) + attentionDayDiscount(repair, baseCost);
        if (repair.getTotalAmount() > 0L) {
            totalCost -= bonusDiscount(vehicle, false);
        }
        else {
            totalCost -= bonusDiscount(vehicle, true);
        }
        totalCost += Math.round(totalCost * IVA);

        if (totalCost < 0L){ totalCost = 0L;}

        repair.setTotalAmount(totalCost);
        saveRepair(repair);
        return repair;
    }


    public Long mileageRecharge(VehicleEntity vehicle, long baseCost) {
        Map<String, List<Double>> mileageCosts = new HashMap<>();
        mileageCosts.put("Sedan",       List.of(0d, 0.03d, 0.07d, 0.12d, 0.2d));
        mileageCosts.put("Hatchback",   List.of(0d, 0.03d, 0.07d, 0.12d, 0.2d));
        mileageCosts.put("SUV",         List.of(0d, 0.05d, 0.09d, 0.12d, 0.2d));
        mileageCosts.put("Pickup",      List.of(0d, 0.05d, 0.09d, 0.12d, 0.2d));
        mileageCosts.put("Furgoneta",   List.of(0d, 0.05d, 0.09d, 0.12d, 0.2d));

        long mileageCost = 0L;

        long mileage = vehicle.getMileage();
        String vehicleType = vehicle.getType();

        if (0L <= mileage && mileage <= 5_000L) {
            mileageCost = Math.round(mileageCosts.get(vehicleType).get(0) * baseCost);
        } else if (5_001L <= mileage && mileage <= 12_000L) {
            mileageCost = Math.round(mileageCosts.get(vehicleType).get(1) * baseCost);
        } else if (12_001L <= mileage && mileage <= 25_000L) {
            mileageCost = Math.round(mileageCosts.get(vehicleType).get(2) * baseCost);
        } else if (25_001L <= mileage && mileage <= 40_000L) {
            mileageCost = Math.round(mileageCosts.get(vehicleType).get(3) * baseCost);
        } else if (40_001L <= mileage) {
            mileageCost = Math.round(mileageCosts.get(vehicleType).get(4) * baseCost);
        }

        return mileageCost;
    }

    public Long antiquityRecharge(VehicleEntity vehicle, long baseCost) {
        Map<String, List<Double>> antiquityCosts = new HashMap<>();
        antiquityCosts.put("Sedan",       List.of(0d, 0.05d, 0.09d, 0.15d));
        antiquityCosts.put("Hatchback",   List.of(0d, 0.05d, 0.09d, 0.15d));
        antiquityCosts.put("SUV",         List.of(0d, 0.07d, 0.11d, 0.2d));
        antiquityCosts.put("Pickup",      List.of(0d, 0.07d, 0.11d, 0.2d));
        antiquityCosts.put("Furgoneta",   List.of(0d, 0.07d, 0.11d, 0.2d));

        long antiquityCost = 0L;

        String vehicleType = vehicle.getType();
        long antiquity = Math.abs(ChronoUnit.YEARS.between(LocalDate.now(), vehicle.getFabricationDate()));

        if (0L <= antiquity && antiquity <= 5L) {
            antiquityCost = Math.round(antiquityCosts.get(vehicleType).get(0) * baseCost);
        } else if (6L <= antiquity && antiquity <= 10L) {
            antiquityCost = Math.round(antiquityCosts.get(vehicleType).get(1) * baseCost);
        } else if (11L <= antiquity && antiquity <= 15L) {
            antiquityCost = Math.round(antiquityCosts.get(vehicleType).get(2) * baseCost);
        } else if (16L <= antiquity) {
            antiquityCost = Math.round(antiquityCosts.get(vehicleType).get(3) * baseCost);
        }

        return antiquityCost;
    }

    public Long delayRecharge(RepairEntity repair, long baseCost) {
        double rechargePerDay = 0.05d;
        long delay = Math.abs(ChronoUnit.DAYS.between(repair.getDateOfPickUp(), repair.getDateOfRelease()));
        long delayCost = Math.round(delay * rechargePerDay * baseCost);

        return delayCost;
    }

    public Long repairNumberDiscount(VehicleEntity vehicle, long baseCost) {
        Map<String, List<Double>> antiquityCosts = new HashMap<>();
        antiquityCosts.put("Gasolina",      List.of(0.05d, 0.1d, 0.15d, 0.2d));
        antiquityCosts.put("Diesel",        List.of(0.07d, 0.12d, 0.17d, 0.22d));
        antiquityCosts.put("Hibrido",       List.of(0.1d, 0.15d, 0.2d, 0.25d));
        antiquityCosts.put("Electrico",     List.of(0.08d, 0.13d, 0.18d, 0.23d));

        int recentRepairNumber = 0;
        List<RepairEntity> vehicleRepairs = repairRepository.findVehicleRepairs(vehicle.getId());
        for (RepairEntity repair: vehicleRepairs) {
            long monthsBetween = Math.abs(ChronoUnit.MONTHS.between(repair.getDateOfPickUp(), LocalDateTime.now()));
            if (monthsBetween <= 12) {
                recentRepairNumber += 1;
            }
        }

        long repairNumberCost = 0;
        String motorType = vehicle.getMotorType();

        if (0L <= recentRepairNumber && recentRepairNumber <= 2L) {
            repairNumberCost = Math.round(antiquityCosts.get(motorType).get(0) * baseCost);
        } else if (3L <= recentRepairNumber && recentRepairNumber <= 5L) {
            repairNumberCost = Math.round(antiquityCosts.get(motorType).get(1) * baseCost);
        } else if (6L <= recentRepairNumber && recentRepairNumber <= 9L) {
            repairNumberCost = Math.round(antiquityCosts.get(motorType).get(2) * baseCost);
        } else if (10L <= recentRepairNumber) {
            repairNumberCost = Math.round(antiquityCosts.get(motorType).get(3) * baseCost);
        }

        return repairNumberCost;
    }

    public Long attentionDayDiscount(RepairEntity repair, long baseCost) {
        double attentionDayPonderer = 0.1;
        long attentionDayCost = 0;
        LocalDateTime admissionDate = repair.getDateOfAdmission();
        if (admissionDate.getDayOfWeek().toString().equals("MONDAY") ||
                admissionDate.getDayOfWeek().toString().equals("THURSDAY")){
            if (9 <= admissionDate.getHour() && admissionDate.getHour() < 12) {
                attentionDayCost = Math.round(baseCost * attentionDayPonderer);
            }
        }

        return attentionDayCost;
    }

    public Long bonusDiscount(VehicleEntity vehicle, boolean consume) {
        String brand = vehicle.getBrand();
        Long bonusCost = restTemplate.getForObject("http://bonus-service/api/v1/bonus/consumeBonus/" + brand + "/" + consume, Long.class);
        //Long bonusCost = bonusService.consumeBonus(brand, consume);

        return bonusCost;
    }

    public boolean deleteRepairsWithVehicle(Long id_vehicle) throws Exception {
        try {
            List<RepairEntity> repairs = repairRepository.findVehicleRepairs(id_vehicle);
            for (RepairEntity repair: repairs) {
                deleteRepair(repair.getId());
                operationService.deleteOperationsWithRepair(repair.getId());
            }
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public Long getAvgRepairTimeOfBrand(String brand) {
        Long average = 0L;
        Long i = 0L;
        List<VehicleEntity> vehicles = restTemplate.getForObject("http://vehicle-service/api/v1/vehicle/brand/" + brand, List.class);
        List<RepairEntity> repairs = new ArrayList<>();
        for (VehicleEntity vehicle: vehicles) {
            repairs.addAll(repairRepository.findAllByVehicleId(vehicle.getId()));
        }
        for (RepairEntity repair: repairs) {
            Long release = repair.getDateOfRelease().toEpochSecond(ZoneOffset.UTC);
            Long admission = repair.getDateOfAdmission().toEpochSecond(ZoneOffset.UTC);
            average += release - admission;
            i += 1L;
        }
        if (i == 0L) { i = 1L;}
        average = average/i;
        return average;
    }

    public boolean deleteRepair(Long id) throws Exception {
        try {
            repairRepository.deleteById(id);
            operationService.deleteOperationsWithRepair(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
