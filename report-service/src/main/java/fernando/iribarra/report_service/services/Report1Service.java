package fernando.iribarra.report_service.services;

import fernando.iribarra.report_service.entities.Report1Entity;
import fernando.iribarra.report_service.models.VehicleEntity;
import fernando.iribarra.report_service.repositories.Report1Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class Report1Service {
    @Autowired
    Report1Repository report1Repository;

    @Autowired
    RestTemplate restTemplate;

    public Report1Entity saveReport1(Report1Entity report1) {
        Report1Entity foundReport = reportExists(report1);
        if (foundReport != null){
            return foundReport;
        }
        ParameterizedTypeReference<List<Long>> responseType = new ParameterizedTypeReference<List<Long>>() {};
        List<Long> values = restTemplate.exchange("http://repair-service/api/v1/repair/repairCountAndValue/" + report1.getVehicleType() + "/" + report1.getOperationType(), HttpMethod.GET, null, responseType).getBody();
        report1.setCount(values.get(0));
        report1.setAmount(values.get(1));
        return report1Repository.save(report1);
    }

    public Report1Entity reportExists(Report1Entity report1) {
        return report1Repository.existsByTypeAndMonth(report1.getOperationType(), report1.getVehicleType(), report1.getDateOfReport().getMonth().getValue());
    }

    public List<Long> getFinalReportForAllCarTypes(Long opType) {
        List<Long> values = new ArrayList<>();
        ParameterizedTypeReference<List<String>> responseType = new ParameterizedTypeReference<List<String>>() {};
        List<String> carTypes = restTemplate.exchange("http://vehicle-service/api/v1/vehicle/vehicletypes", HttpMethod.GET, null, responseType).getBody();
        for (String carType: carTypes) {
            Report1Entity report = new Report1Entity(null, opType, carType, 0L, 0L, LocalDate.now());
            report = saveReport1(report);
            values.add(report.getCount());
            values.add(report.getAmount());
        }
        return values;
    }

    public Report1Entity getReport1ById(Long id) { return report1Repository.findById(id).get(); }

    public List<Report1Entity> getAllReport1() { return report1Repository.findAll(); }

    public Report1Entity updateReport1(Report1Entity report1) { return report1Repository.save(report1); }

    public boolean deleteReport1(Long id) throws Exception {
        try {
            report1Repository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}