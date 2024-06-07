package fernando.iribarra.repair_list_service.services;
import fernando.iribarra.repair_list_service.entities.RepairListEntity;
import fernando.iribarra.repair_list_service.repositories.RepairListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepairListService {
    @Autowired
    RepairListRepository repairListRepository;

    public RepairListEntity saveRepairList(RepairListEntity repairList) { return repairListRepository.save(repairList); }

    public RepairListEntity getRepairListById(Long id) { return repairListRepository.findById(id).get(); }

    public List<RepairListEntity> getAllRepairList() { return repairListRepository.findAll(); }

    public Long getBaseCost(int type, String motor) { return repairListRepository.getBaseCost(type, motor); }

    public RepairListEntity updateRepairList(RepairListEntity repairList) { return repairListRepository.save(repairList); }

    public boolean deleteRepairList(Long id) throws Exception {
        try {
            repairListRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
