package com.example.rentapartment.service.impl;

import com.example.rentapartment.constant.ConstantProject;
import com.example.rentapartment.dto.AddressDto;
import com.example.rentapartment.dto.ApartmentDto;
import com.example.rentapartment.entity.*;
import com.example.rentapartment.exception_application.NotFoundBodyResponseException;
import com.example.rentapartment.exception_application.NotFoundInformation;
import com.example.rentapartment.integration_api.RestTemplateManagerService;
import com.example.rentapartment.map.CityMap;
import com.example.rentapartment.mapper.FullMapper;
import com.example.rentapartment.mapper.RegistrationMapper;

import com.example.rentapartment.repository.*;
import com.example.rentapartment.response_object.ResponseObjectList;
import com.example.rentapartment.security_model.ValideUserSession;
import com.example.rentapartment.service.ClientRegistrationService;
import com.example.rentapartment.service.IntegrationManager;
import com.example.rentapartment.service.RentApartmentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentApartmentServiceImpl implements RentApartmentService {

    private final AddressRepository addressRepository;

    private final ApartmentRepository apartmentRepository;

    private final RestTemplateManagerService restTemplateManagerService;

    private final FullMapper fullMapper;

    private final RegistrationMapper registrationMapper;

    private final RaitingRepository raitingRepository;

    private final ClientRepository clientRepository;

    private final ValideUserSession valideUserSession;

    private final IntegrationManager integrationManager;

    private final ClientRegistrationService clientRegistrationService;

    private final BookingHistoryRepository bookingHistoryRepository;


    /**
     * Метод сохраняет в БД новую квартиру
     * где registrationFlatForm-модель, с которой приходят данные о новой квартире
     * return-возвращает запись об успешном сохранении
     */
//    @Override
//    public String saveToBase(RegistrationFlatFormModel registrationFlatFormModel) {
//        apartmentRepository.save(registrationMapper.getApartmentEntity(registrationFlatFormModel));
//        Long lastId = apartmentRepository.getLastId();
//        AddressEntity addressEntity = registrationMapper.getAddressEntity(registrationFlatFormModel);
//        ApartmentEntity apartmentEntity = apartmentRepository.findById(lastId).get();
//        addressEntity.setApartmentEntity(apartmentEntity);
//        addressRepository.save(addressEntity);
//
//        return "Запись осуществлена";
//    }

    /**
     * Метод возвращает список адресов по названию улицы
     * где street-накзвание улицы
     * return-список доступных адресов по названию улицы
     */
    @Override
    public List<AddressDto> getFullInformationByStreet(String street) {
        List<AddressEntity> entityList = addressRepository.getAddressEntitiesListOnTheStreet(street);
        List<AddressDto> collect = entityList.stream()
                .map(o -> fullMapper.getFullFieldsAddressToDto(o))
                .collect(Collectors.toList());

        return collect;
    }

    /**
     * Метод возвращает список адресов по названию города
     * где city-название города
     * return-список доступных адресов по названию города
     */
    @Override
    public List<AddressDto> getFullInformationByCity(String city) {
        List<AddressEntity> entityList = addressRepository.getAddressEntitiesListOnTheCity(city);
        List<AddressDto> collect = entityList.stream()
                .map(o -> fullMapper.getFullFieldsAddressToDto(o))
                .collect(Collectors.toList());
        return collect;
    }

    /**
     * Метод считает средний рейтинг по квартире во время запроса по локации
     * где latitude-широта
     * longitude- долгота
     * return-список доступных адресов по локации
     */
    @Override
    public ResponseObjectList getInfoByCity(String latitude, String longitude) {
        String cityResult;
        String bodyResponse;
        List<AddressEntity> addressEntities;

        try {
            bodyResponse = restTemplateManagerService.searchCity(latitude, longitude);
        } catch (Exception e) {
            throw new RuntimeException("Ресурс временно недоступен");
        }
        if (bodyResponse != null) {

            try {
                cityResult = getParseInformation(bodyResponse);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            List<AddressDto> collect = new ArrayList<>();

            if (cityResult != null) {
                String descriptionCity = CityMap.getDescription(cityResult);
                addressEntities = addressRepository.getAddressEntitiesListOnTheCity(descriptionCity);


                for (AddressEntity a : addressEntities) {

                    ApartmentDto apartmentDto = fullMapper.apartmentEntityToApartmentDto(a.getApartmentEntity());
                    AddressDto addressDto = fullMapper.addressEntityToAddressDto(a);

//                    List<RaitingEntity> result = raitingRepository.findRaitingEntitiesByApartment_Id(a.getApartmentEntity().getId());
//                    Integer averageRaiting = getAverageValueRaiting(result);
//                    apartmentDto.setRating(averageRaiting);

                    addressDto.setApartmentDto(apartmentDto);
                    collect.add(addressDto);

                }
                return new ResponseObjectList("Лист доступных апартаментов", collect);
            }
            throw new NotFoundInformation();
        }
        throw new NotFoundBodyResponseException();

    }

    /**
     * Метод считает средний рейтинг квартиры
     */
    private Integer getAverageValueRaiting(List<RaitingEntity> result) {
        int sum = 0;
        for (RaitingEntity a : result) {
            sum = sum + a.getRating();
        }
        int averageRaiting = sum / result.size();

        return (averageRaiting);

    }

    /**
     * Метод получения подробной информации по городу
     */
    private String getParseInformation(String body) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(body);
        String cityResult = jsonNode.at(ConstantProject.SEARCH_CITY_FROM_TREE).asText();
        return cityResult;
    }

    /**
     * Метод возвращает список адресов по цене и количеству комнат
     * где price-цена
     * numberOfRooms-количество комнат
     */
    @Override
    public List<AddressDto> getApartmentEntitiesByPriceAndNumberOfRooms(String price, String numberOfRooms) {
        List<ApartmentEntity> apartmentEntityList = apartmentRepository.getApartmentEntitiesByPriceAndNumberOfRooms(price,
                numberOfRooms);

        List<AddressDto> resultList = new ArrayList<>();
        for (ApartmentEntity apartmentEntity : apartmentEntityList) {
            AddressDto fullFieldsAddressToDto = fullMapper.getFullFieldsAddressToDto(apartmentEntity.getAddressEntity());
            resultList.add(fullFieldsAddressToDto);
        }

        return resultList;


    }

    /**
     * Метод возвращает адрес по id
     */
    @Override
    public AddressDto getApartmentById(Long id) {

        AddressEntity addressEntity = addressRepository.findById(id).get();
        ClientEntity clientEntity = clientRepository.getClientEntityByEmail(clientRegistrationService
                .encode(valideUserSession.getEmail()));
//        IntegrationModelToMailSendler integrationModel = fullMapper.prepareIntegrationModelMailSenderFromEntity(addressEntity,
//                addressEntity.getApartmentEntity(),
//                clientEntity);
//
//        integrationManager.throwInfoOnRentProduct(in);
        return fullMapper.addressEntityToAddressDto(addressEntity);
    }

    /**
     * Метод считает общую стоимость квартиры по количеству дней
     * где addressDto-выбранная квартира
     * days-количество дней
     */
    public Integer getTotalAmount(AddressDto addressDto, int days) {


        String price = addressDto.getApartmentDto().getPrice();
        int i = Integer.parseInt(price);

        int sum = i * days;

        return sum;

    }

    private BookingHistoryEntity prepareBookingEntity(ApartmentEntity apartmentEntity,
                                                      ClientEntity clientEntity,
                                                      LocalDate start,
                                                      LocalDate end,LocalDate dateBookingRegistration) {
        BookingHistoryEntity bookingHistoryEntity = new BookingHistoryEntity();
        bookingHistoryEntity.setApartmentId(apartmentEntity);
        bookingHistoryEntity.setClientId(clientEntity);
        bookingHistoryEntity.setDateStart(start);
        bookingHistoryEntity.setDateEnd(end);
        bookingHistoryEntity.setDateRegistrationBooking(dateBookingRegistration);
        return bookingHistoryEntity;
    }

    @Override
    public AddressDto getApartmentByIdAndTotalAmount(Long id, LocalDate start, LocalDate end) {
        AddressEntity addressEntity = addressRepository.findById(id).get();
        ClientEntity clientEntity = clientRepository.getClientEntityByEmail(clientRegistrationService
                .encode(valideUserSession.getEmail()));

        clientEntity.setCountOfGrocery(increment(clientEntity.getCountOfGrocery()));
        addressEntity.getApartmentEntity().setAvailability(false);
        addressEntity.getApartmentEntity().setCurrentTenant(clientEntity);

        clientRepository.save(clientEntity);
        addressRepository.save(addressEntity);
        AddressDto addressDto = fullMapper.addressEntityToAddressDto(addressEntity);
        LocalDate dateBookingRegistration=LocalDate.now();

        bookingHistoryRepository.save(prepareBookingEntity(addressEntity.getApartmentEntity(),
                clientEntity,
                start,
                end,
                dateBookingRegistration));
        Long lastIdInBookingHistory = bookingHistoryRepository.getLastId();

        integrationManager.throwInfoOnRentProduct(lastIdInBookingHistory);


//        if(clientEntity.getCountOfGrocery()>=3){
//            getTotalAmountDiscount10(addressDto,7);
//        } else if (clientEntity.getParentCity()!= addressEntity.getCity()) {
//            getTotalAmountDiscount15(addressDto,7);
//
//        }
//        else {
//            getTotalAmount(addressDto,7);
//        }
        return addressDto;

    }

    public Integer getTotalAmountDiscount10(AddressDto addressDto, int days) {


        String price = addressDto.getApartmentDto().getPrice();
        int i = Integer.parseInt(price);

        int sum = i * days;
        int sumDiscount = sum - (sum * 10) / 100; /** сумма со скидкой 10%*/

        return sumDiscount;

    }

    public Integer getTotalAmountDiscount15(AddressDto addressDto, int days) {


        String price = addressDto.getApartmentDto().getPrice();
        int i = Integer.parseInt(price);

        int sum = i * days;
        int sumDiscount = sum - (sum * 15) / 100; /** сумма со скидкой 15%*/

        return sumDiscount;

    }

    public Integer increment(int countOfGrocery) {
        return countOfGrocery + 1;
    }


}






