package fernando.iribarra.repair_list_service.services;
import fernando.iribarra.repair_list_service.entities.RepairListEntity;
import fernando.iribarra.repair_list_service.repositories.RepairListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RepairListService {
    @Autowired
    RepairListRepository repairListRepository;

    public RepairListEntity saveRepairList(RepairListEntity repairList) { return repairListRepository.save(repairList); }

    public RepairListEntity getRepairListById(Long id) { return repairListRepository.findById(id).get(); }

    public List<RepairListEntity> getAllRepairList() { return repairListRepository.findAll(); }

    public List<Map<String, String>> getAllOperationTypes() {
        List<Map<String, String>> opTypes = new ArrayList<>();
        List<Integer> foundTypes = new ArrayList<>();
        List<RepairListEntity> repairListEntityList = repairListRepository.findAll();
        for (RepairListEntity repairList: repairListEntityList) {
            if (!foundTypes.contains(repairList.getOperationType())) {
                foundTypes.add(repairList.getOperationType());
                Map<String, String> opType = new HashMap<>();
                opType.put("id", String.valueOf(repairList.getOperationType()));
                opType.put("name", repairList.getName());
                opTypes.add(opType);
            }
        }
        return opTypes;
    }

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
