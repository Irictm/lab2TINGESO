package fernando.iribarra.report_service.services;

import fernando.iribarra.report_service.entities.Report2Entity;
import fernando.iribarra.report_service.repositories.Report2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class Report2Service {
    @Autowired
    Report2Repository report2Repository;

    @Autowired
    RestTemplate restTemplate;

    public Report2Entity saveReport2(Report2Entity report2) {
        Report2Entity foundReport = reportExists(report2);
        if (foundReport != null){
            return foundReport;
        }
        ParameterizedTypeReference<List<Long>> responseType = new ParameterizedTypeReference<List<Long>>() {};
        List<Long> values = restTemplate.exchange("http://repair-service/api/v1/repair/repairCountAndValue/" + report2.getOperationType() + "/" + report2.getDateOfReport().getYear() + "/" + report2.getDateOfReport().getMonth().getValue(), HttpMethod.GET, null, responseType).getBody();
        report2.setCount(values.get(0));
        report2.setAmount(values.get(1));
        return report2Repository.save(report2);
    }

    public Report2Entity reportExists(Report2Entity report2) {
        return report2Repository.existsByTypeAndYearAndMonth(report2.getOperationType(), report2.getDateOfReport().getYear(),report2.getDateOfReport().getMonth().getValue());
    }

    public List<Long> getReportInfoLastTwoMonths(Long opType, int year, int month){
        List<Long> values = new ArrayList<>();
        LocalDate date = LocalDate.of(year, month, 1);
        Report2Entity report2 = new Report2Entity(null, opType, 0L, 0L, date);
        report2 = saveReport2(report2);
        Long count = report2.getCount();
        Long amount = report2.getAmount();

        LocalDate dateBefore = LocalDate.of(year, month, 1).minusMonths(1);
        Report2Entity report2Before = new Report2Entity(null, opType, 0L, 0L, dateBefore);
        report2Before = saveReport2(report2Before);
        Long countBefore = report2Before.getCount();
        Long amountBefore = report2Before.getAmount();

        LocalDate dateBeforeBefore = LocalDate.of(year, month, 1).minusMonths(2);
        Report2Entity report2BeforeBefore = new Report2Entity(null, opType, 0L, 0L, dateBeforeBefore);
        report2BeforeBefore = saveReport2(report2BeforeBefore);
        Long countBeforeBefore = report2BeforeBefore.getCount();
        Long amountBeforeBefore = report2BeforeBefore.getAmount();

        Long countVariance = 0L;
        Long amountVariance = 0L;
        if (count >= countBefore) {
            if (countBefore != 0L ) {countVariance = (long) Math.round(100F * Float.valueOf(count) / Float.valueOf(countBefore));}
            else {countVariance = null;}
        }
        else {
            if (count != 0L ) {countVariance = (long) Math.round(100F * Float.valueOf(countBefore) / Float.valueOf(count));}
            else {countVariance = null;}
        }
        if (amount >= amountBefore) {
            if (amountBefore != 0L ) {amountVariance = (long) Math.round(100F * Float.valueOf(amount) / Float.valueOf(amountBefore));}
            else {amountVariance = null;}
        }
        else {
            if (amount != 0L ) {amountVariance = (long) Math.round(100F * Float.valueOf(amountBefore) / Float.valueOf(amount));}
            else {amountVariance = null;}
        }

        Long countVarianceBefore = 0L;
        Long amountVarianceBefore = 0L;
        if (countBefore >= countBeforeBefore) {
            if (countBeforeBefore != 0L ) {countVarianceBefore = (long) Math.round(100F * Float.valueOf(countBefore) / Float.valueOf(countBeforeBefore));}
            else {countVarianceBefore = null;}
        }
        else {
            if (countBefore != 0L ) {countVarianceBefore = (long) Math.round(100F * Float.valueOf(countBeforeBefore) / Float.valueOf(countBefore));}
            else {countVarianceBefore = null;}
        }
        if (amountBefore >= amountBeforeBefore) {
            if (amountBeforeBefore != 0L ) {amountVarianceBefore = (long) Math.round(100F * Float.valueOf(amountBefore) / Float.valueOf(amountBeforeBefore));}
            else {amountVarianceBefore = null;}
        }
        else {
            if (amountBefore != 0L ) {amountVarianceBefore = (long) Math.round(100F * Float.valueOf(amountBeforeBefore) / Float.valueOf(amountBefore));}
            else {amountVarianceBefore = null;}
        }

        values.add(count);
        values.add(amount);

        values.add(countVariance);
        values.add(amountVariance);

        values.add(countBefore);
        values.add(amountBefore);

        values.add(countVarianceBefore);
        values.add(amountVarianceBefore);

        values.add(countBeforeBefore);
        values.add(amountBeforeBefore);

        return values;
    }

    public Report2Entity getReport2ById(Long id) { return report2Repository.findById(id).get(); }

    public List<Report2Entity> getAllReport2() { return report2Repository.findAll(); }

    public Report2Entity updateReport2(Report2Entity report2) { return report2Repository.save(report2); }

    public boolean deleteReport2(Long id) throws Exception {
        try {
            report2Repository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}