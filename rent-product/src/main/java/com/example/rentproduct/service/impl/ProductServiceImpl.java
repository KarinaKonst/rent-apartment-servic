package com.example.rentproduct.service.impl;

import com.example.rentproduct.dto.AddressDto;
import com.example.rentproduct.dto.ApartmentDto;
import com.example.rentproduct.dto.ClientDto;
import com.example.rentproduct.entity.BookingHistoryEntity;
import com.example.rentproduct.entity.ProductEntity;
import com.example.rentproduct.exception.NotFoundBookingHistoryException;
import com.example.rentproduct.mapper.SendlerMapper;
import com.example.rentproduct.repository.BookingHistoryRepository;
import com.example.rentproduct.repository.PromotionProductRepository;
import com.example.rentproduct.service.EmailSenderService;
import com.example.rentproduct.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private SendlerMapper sendlerMapper;
    @Autowired
    private PromotionProductRepository promotionProductRepository;
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private BookingHistoryRepository bookingHistoryRepository;


    @Override
    public void throwInfoToProductService(Long id) {
        BookingHistoryEntity history = bookingHistoryRepository.findById(id).orElseThrow(() -> new NotFoundBookingHistoryException());


        ApartmentDto apartment = sendlerMapper.getApartmentEntityToApartmentDto(history.getApartmentEntity());
        ClientDto clientDto = sendlerMapper.getClientEntityToClientDto(history.getClientEntity());
        Long idDiscount = checkingForProductDistribution(clientDto, apartment.getAddressDto(), history);

        history.setProductEntity(promotionProductRepository.findById(idDiscount).get());
        bookingHistoryRepository.save(history);
        ProductEntity productEntity = promotionProductRepository.findById(idDiscount).get();
        emailSenderService.sendEmail(productEntity.getNameProduct(), productEntity.getDescription(), "sciline10@yandex.ru");


    }



    /**
     * Метод проверки полей для рассылки предложений
     */
    @Override
    public Long checkingForProductDistribution(ClientDto clientDto, AddressDto addressDto, BookingHistoryEntity history) {
        List<ProductEntity> listDiscount = promotionProductRepository.findAll()
                .stream()
                .sorted((p1, p2) -> Integer.compare(p2.getDiscount(), p1.getDiscount()))
                .collect(Collectors.toList());
        if (listDiscount.isEmpty()) {
            return null;
        }
        for (ProductEntity p : listDiscount) {
            if (p.getId() == 1L) {
                if (clientDto.getCountOfGrocery() >= 3) {
                    return 1L;
                }
            }
            if (p.getId() == 2L) {
                if (!clientDto.getParentCity().equals(addressDto.getCity())) {
                    return 2L;
                }


            }
//            if (p.getId() == 3L) {
//                if ((clientDto.getBirthday().getDayOfMonth()==LocalDate.now().getDayOfMonth())) {
//                    return 3L;
//
//
//                }
//            }
            if (p.getId() == 4L) {
                if (clientDto.getCountOfGrocery() == 1) {
                    return 4L;


                }
            }

            if (p.getId() == 5L) {
                if (addressDto.getCity().equals("Санкт-Петербург")) {
                    return 5L;


                }
            }
            if (p.getId() == 6L) {
                if (ChronoUnit.DAYS.between(history.getDateStart(), history.getDateEnd()) > 7) {
                    return 6L;
                }
            }
        }
        return null;
    }
}






