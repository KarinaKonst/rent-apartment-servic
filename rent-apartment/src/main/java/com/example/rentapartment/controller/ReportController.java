package com.example.rentapartment.controller;

import com.example.rentapartment.service.RentApartmentService;
import com.example.rentapartment.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.example.rentapartment.constant.ConstantProject.GET_REPORT;

@RestController
public class ReportController {

    @Autowired
    private ReportService reportService;

    /**
     * Метод для получения отчета
     */
    @GetMapping(GET_REPORT)
    public String getReportTemplate(@RequestParam String year,
                                    @RequestParam Integer month){
        return reportService.getReport(year,month);
    }

}
