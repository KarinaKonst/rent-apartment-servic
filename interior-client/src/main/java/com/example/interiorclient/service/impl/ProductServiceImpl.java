package com.example.interiorclient.service.impl;

import com.example.interiorclient.dto.AddressDto;
import com.example.interiorclient.dto.ClientDto;
import com.example.interiorclient.entity.BookingHistoryEntity;
import com.example.interiorclient.entity.ProductEntity;
import com.example.interiorclient.exception.NotFoundBookingHistoryException;
import com.example.interiorclient.mapper.SendlerMapper;
import com.example.interiorclient.repository.BookingHistoryRepository;
import com.example.interiorclient.repository.PromotionProductRepository;
import com.example.interiorclient.service.ConsumerService;
import com.example.interiorclient.service.EmailSenderService;
import com.example.interiorclient.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);


    private final SendlerMapper sendlerMapper;

    private final PromotionProductRepository promotionProductRepository;

    private final EmailSenderService emailSenderService;

    private final BookingHistoryRepository bookingHistoryRepository;
    private final ConsumerService consumerService;


    @Override
    public void throwInfoToProductService(Long id) {
        logger.info("interior-client : throwInfoToProductService -> started");
        sendMessage(id);
    }

    public void sendMessage(Long id) {
        BookingHistoryEntity history = bookingHistoryRepository.findById(id).orElseThrow(() -> new NotFoundBookingHistoryException());

        ClientDto clientDto = sendlerMapper.getClientEntityToClientDto(history.getClientEntity());
        AddressDto addressDto = sendlerMapper.getAddressEntityToAddressDto(history.getApartmentEntity().getAddressEntity());
        Long idDiscount = checkingForProductDistribution(clientDto, addressDto, history);

        history.setProductEntity(promotionProductRepository.findById(idDiscount).get());
        bookingHistoryRepository.save(history);
        ProductEntity productEntity = promotionProductRepository.findById(idDiscount).get();
        emailSenderService.sendEmail(productEntity.getNameProduct(), productEntity.getDescription(), "sciline10@yandex.ru");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException("Ошибка во время рассылки. Попробуйте еще раз.");
        }
        emailSenderService.sendEmail("Подтверждение брони", sendEmailMessage(history, addressDto, clientDto), "sciline10@yandex.ru");

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

    public String sendEmailMessage(BookingHistoryEntity history, AddressDto address, ClientDto client) {

        String s = "Уважаемый(ая), " + history.getClientEntity().getFirstName() +
                " \n\nВаше бронирование по адресу: " + history.getApartmentEntity().getAddressEntity().getCity() +
                ", " + history.getApartmentEntity().getAddressEntity().getStreet() +
                ", д." + history.getApartmentEntity().getAddressEntity().getNumberHouse() +
                ", кв." + history.getApartmentEntity().getAddressEntity().getNumberApartment() +
                " \nбыло подтверждено с " + history.getDateStart() + " до " + history.getDateEnd() +
                " \nЦена апартаментов за сутки: " + history.getApartmentEntity().getPrice() + " рублей" +
                " \nОбщая сумма проживания: " + totalAmount(history, address, client) + " рублей" +
                ".\n\n Приятного отдыха!";
        return s;

    }

    public long totalAmount(BookingHistoryEntity history, AddressDto address, ClientDto client) {
        long daysBetween = ChronoUnit.DAYS.between(history.getDateStart(), history.getDateEnd());
        Long idDiscont = checkingForProductDistribution(client, address, history);
        ProductEntity productEntity = promotionProductRepository.findById(idDiscont).get();
        Integer discount = productEntity.getDiscount();
        String price = history.getApartmentEntity().getPrice();
        int pr = Integer.parseInt(price);
        long totalAmount = pr * daysBetween;
        long amountDiscount = totalAmount - (totalAmount * discount / 100);
        return amountDiscount;
    }


}






