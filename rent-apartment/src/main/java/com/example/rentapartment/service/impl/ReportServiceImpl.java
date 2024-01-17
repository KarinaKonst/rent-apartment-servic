package com.example.rentapartment.service.impl;

import com.example.rentapartment.entity.ApartmentEntity;
import com.example.rentapartment.entity.BookingHistoryEntity;
import com.example.rentapartment.entity.TotalRatingEntity;
import com.example.rentapartment.repository.BookingHistoryRepository;
import com.example.rentapartment.service.ReportService;
import jakarta.transaction.Transactional;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.rentapartment.constant.ConstantProject.FILE_REPORT_TEMPLATE;
import static java.util.Objects.isNull;

@Service

public class ReportServiceImpl implements ReportService {

    @Autowired
    private BookingHistoryRepository bookingHistoryRepository;

    @Override
    public String getReport(String year, Integer month) {
        LocalDate date = getDate(year, month);
        prepareInfoForReport(date);
        return "Отчет успешно выгружен";
    }

    private List<BookingHistoryEntity> prepareInfoForReport(LocalDate dateBooking) {
        List<BookingHistoryEntity> bookingHistoryEntitiesByDateStart = bookingHistoryRepository.getBookingHistoryEntitiesByDateStartIsAfter(dateBooking);

        List<BookingHistoryEntity> reportCollection = bookingHistoryEntitiesByDateStart.stream()
                .filter(a -> a.getDateStart().getMonth().equals(dateBooking.getMonth()))
                .collect(Collectors.toList());

        File file = new File(FILE_REPORT_TEMPLATE);
        try (FileInputStream fileInputStream = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            int rowNumber = 1;
            for (BookingHistoryEntity bh : reportCollection) {
                Row row = sheet.createRow(rowNumber++);
                row.createCell(0).setCellValue(bh.getDateStart().toString());
                row.createCell(1).setCellValue(ChronoUnit.DAYS.between(bh.getDateStart(), bh.getDateEnd()));
                row.createCell(2).setCellValue(ChronoUnit.DAYS.between(bh.getDateStart(), bh.getDateEnd()) * Integer.parseInt(bh.getApartmentId().getPrice()));
                row.createCell(3).setCellValue(bh.getProductId().getDiscount() + " %");
                row.createCell(4).setCellValue("г." + bh.getApartmentId().getAddressEntity().getCity() +
                        ", " + bh.getApartmentId().getAddressEntity().getStreet() +
                        ", д." + bh.getApartmentId().getAddressEntity().getNumberHouse() +
                        ", кв." + bh.getApartmentId().getAddressEntity().getNumberApartment());
                row.createCell(5).setCellValue(bh.getApartmentId().getPrice() + " рублей");
                TotalRatingEntity rating = bh.getApartmentId().getTotalRating();
                if(!isNull(rating)){
                    Integer totalRating = rating.getTotalRating();
                    if(StringUtils.isNotEmpty(String.valueOf(totalRating))){
                        row.createCell(6).setCellValue(totalRating);
                    }
                    else {
                        row.createCell(6).setCellValue("Нет отзывов о квартире");
                    }
                }

                row.createCell(7).setCellValue(totalSum(reportCollection, bh.getApartmentId()));
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            workbook.write(fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();


        } catch (IOException e) {
            throw new RuntimeException("Ошибка выгрузки отчета");
        }

        return null;
    }

    private Integer totalSum(List<BookingHistoryEntity> bookingHistoryEntities, ApartmentEntity apartmentEntity) {
        int sum = 0;
        List<BookingHistoryEntity> collect = bookingHistoryEntities.stream()
                .filter(a -> a.getApartmentId().equals(apartmentEntity)).collect(Collectors.toList());
        for (BookingHistoryEntity a : collect) {
            int price = Integer.parseInt(a.getApartmentId().getPrice());
            Integer discount = a.getProductId().getDiscount();
            int totalPrice = price - (price * discount / 100);
            sum += totalPrice;
        }
        return sum;
    }

    private LocalDate getDate(String year, Integer month) {
        try {
            int yearDate = Integer.parseInt(year);
            return LocalDate.of(yearDate, month, 1);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Некорректный формат даты");
        }
    }

}

