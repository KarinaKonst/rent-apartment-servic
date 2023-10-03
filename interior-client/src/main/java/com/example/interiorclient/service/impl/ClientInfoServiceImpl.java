//package com.example.interiorclient.service.impl;
//
//import com.example.interiorclient.dto.AddressDto;
//import com.example.interiorclient.dto.ClientDto;
//import com.example.interiorclient.entity.ProductEntity;
//import com.example.interiorclient.repository.PromotionProductRepository;
//import com.example.interiorclient.service.EmailSenderService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//
//public class ClientInfoServiceImpl {
//
//    @Autowired
//    private EmailSenderService emailSenderService;
//    @Autowired
//    private PromotionProductRepository promotionProductRepository;
//
//
//    /**
//     * Метод рассылки предложения по городу
//     */
//
//
//    public void presentProductByCity() {
//        ProductEntity productEntity = promotionProductRepository.findById(2L).get();
//        emailSenderService.sendEmail(productEntity.getNameProduct(), productEntity.getDescription(), "sciline10@yandex.ru");/**Тут в получателе выбираем email клиента сессии*/
//
//
//    }
//
//    /**
//     * Метод рассылки предложения по количеству сделок
//     */
//
//    public void presentProductByCountOfGrocery(ClientDto clientDto) {
//        ProductEntity productEntity = promotionProductRepository.findById(1L).get();
//        emailSenderService.sendEmail(productEntity.getNameProduct(), productEntity.getDescription(), "sciline10@yandex.ru");
//
//    }
//
//    /**
//     * Метод проверки полей для рассылки предложений
//     */
//    public void checkingForProductDistribution(ClientDto clientInfo, AddressDto addressDto) {
//
//        if (!clientInfo.getParentCity().equals(addressDto.getCity())) {
//            presentProductByCity();
//        }
//
//        if (clientInfo.getCountOfGrocery() >= 3) {
//            presentProductByCountOfGrocery(clientInfo);
//        }
//
//    }
//}
