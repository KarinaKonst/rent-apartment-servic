package com.example.interiorclient.service.impl;

import com.example.interiorclient.dto.AddressDto;
import com.example.interiorclient.dto.ClientDto;
import com.example.interiorclient.entity.BookingHistoryEntity;
import com.example.interiorclient.entity.ProductEntity;
import com.example.interiorclient.exception.NotFoundBookingHistoryException;
import com.example.interiorclient.mapper.SendlerMapper;
import com.example.interiorclient.repository.BookingHistoryRepository;
import com.example.interiorclient.repository.PromotionProductRepository;
import com.example.interiorclient.service.EmailSenderService;
import com.example.interiorclient.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final SendlerMapper sendlerMapper;

    private final PromotionProductRepository promotionProductRepository;

    private final EmailSenderService emailSenderService;

    private final BookingHistoryRepository bookingHistoryRepository;


    @Override
    public void throwInfoToProductService(Long id) {
        BookingHistoryEntity history = bookingHistoryRepository.findById(id).orElseThrow(() -> new NotFoundBookingHistoryException());


        ClientDto clientDto = sendlerMapper.getClientEntityToClientDto(history.getClientEntity());
        AddressDto addressDto = sendlerMapper.getAddressEntityToAddressDto(history.getApartmentEntity().getAddressEntity());
        Long idDiscount = checkingForProductDistribution(clientDto, addressDto, history);

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
            if (p.getId() == 3L) {
                if ((clientDto.getBirthday().getDayOfMonth() == LocalDate.now().getDayOfMonth())
                        && (clientDto.getBirthday().getMonth() == LocalDate.now().getMonth())) {
                    return 3L;


                }
            }
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






