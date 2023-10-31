package com.example.rentapartment.service.impl;

import com.example.rentapartment.dto.AddressDto;
import com.example.rentapartment.dto.ApartmentDto;
import com.example.rentapartment.dto.Components;
import com.example.rentapartment.dto.GeacoderResponseDto;
import com.example.rentapartment.entity.*;
import com.example.rentapartment.exception.NotFoundInformation;
import com.example.rentapartment.integration_api.RestTemplateManagerService;
import com.example.rentapartment.map.CityMap;
import com.example.rentapartment.mapper.FullMapper;
import com.example.rentapartment.repository.*;
import com.example.rentapartment.response_object.ResponseObjectList;
import com.example.rentapartment.service.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class RentApartmentServiceImpl implements RentApartmentService {
    private static final Logger logger = LoggerFactory.getLogger(RentApartmentServiceImpl.class);

    private final AddressRepository addressRepository;
    private final ApartmentRepository apartmentRepository;
    private final RestTemplateManagerService restTemplateManagerService;
    private final FullMapper fullMapper;
    private final RaitingRepository raitingRepository;
    private final ClientRepository clientRepository;
    private final ValidateUserToken validateUserToken;
    private final IntegrationManager integrationManager;
    private final BookingHistoryRepository bookingHistoryRepository;
    private final ProducerService producerService;


    /**
     * Метод возвращает список адресов по названию улицы
     * где street-накзвание улицы
     * return-список доступных адресов по названию улицы
     */
    @Override
    public ResponseObjectList getFullInformationByStreet(String street) {
        List<AddressEntity> entityList = addressRepository.getAddressEntitiesListOnTheStreet(street);
        if (!entityList.isEmpty()) {
            List<AddressDto> collect = new ArrayList<>();
            for (AddressEntity a : entityList) {

                ApartmentDto apartmentDto = fullMapper.apartmentEntityToApartmentDto(a.getApartmentEntity());
                AddressDto addressDto = fullMapper.addressEntityToAddressDto(a);

                List<RaitingEntity> raitingEntitiesByApartmentId = raitingRepository.findRaitingEntitiesByApartment_Id(a.getApartmentEntity().getId());

                if (!raitingEntitiesByApartmentId.isEmpty()) {
                    Integer averageRaiting = getAverageValueRaiting(raitingEntitiesByApartmentId);
                    apartmentDto.setRating(averageRaiting);

                } else {
                    apartmentDto.setRating(0);
                }
                addressDto.setApartmentDto(apartmentDto);
                collect.add(addressDto);
            }

            return new ResponseObjectList("Результат поиска", collect);
        }
        throw new NotFoundInformation();

    }

    /**
     * Метод считает средний рейтинг по квартире во время запроса по локации
     * где latitude-широта
     * longitude- долгота
     * return-список доступных адресов по локации
     */
    @Override
    public ResponseObjectList getInfoByCity(String latitude, String longitude) {

        GeacoderResponseDto bodyResponse;
        List<AddressEntity> addressEntities;
        List<RaitingEntity> resultRating;

        try {
            bodyResponse = restTemplateManagerService.searchCity(latitude, longitude);
        } catch (Exception e) {
            throw new RuntimeException("Ресурс временно недоступен");
        }
        if (bodyResponse != null) {

            List<AddressDto> collect = new ArrayList<>();

            String city = cityValue(bodyResponse);

            String descriptionCity = CityMap.getDescription(city);
            addressEntities = addressRepository.getAddressEntitiesListOnTheCity(descriptionCity);

            for (AddressEntity a : addressEntities) {

                ApartmentDto apartmentDto = fullMapper.apartmentEntityToApartmentDto(a.getApartmentEntity());
                AddressDto addressDto = fullMapper.addressEntityToAddressDto(a);

                resultRating = raitingRepository.findRaitingEntitiesByApartment_Id(a.getApartmentEntity().getId());

                if (!resultRating.isEmpty()) {
                    Integer averageRaiting = getAverageValueRaiting(resultRating);
                    apartmentDto.setRating(averageRaiting);

                } else {
                    apartmentDto.setRating(0);
                }
                addressDto.setApartmentDto(apartmentDto);

                collect.add(addressDto);
            }
            return new ResponseObjectList("Лист доступных апартаментов", collect);
        }
        throw new NotFoundInformation();
    }


    private String cityValue(GeacoderResponseDto bodyResponse) {
        Components components = bodyResponse.getResultList().get(0).getComponents();
        if (!isNull(components.getCity())) {
            return components.getCity();
        }
        return components.getTown();
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
     * Метод возвращает список адресов по цене и количеству комнат
     * где price-цена
     * numberOfRooms-количество комнат
     */
    @Override
    public ResponseObjectList getApartmentEntitiesByPriceAndNumberOfRooms(String price, String numberOfRooms) {
        List<ApartmentEntity> apartmentEntityList = apartmentRepository.getApartmentEntitiesByPriceAndNumberOfRooms(price,
                numberOfRooms);
        if (!apartmentEntityList.isEmpty()) {
            List<AddressDto> collect = new ArrayList<>();
            for (ApartmentEntity a : apartmentEntityList) {

                ApartmentDto apartmentDto = fullMapper.apartmentEntityToApartmentDto(a.getAddressEntity().getApartmentEntity());
                AddressDto addressDto = fullMapper.addressEntityToAddressDto(a.getAddressEntity());

                List<RaitingEntity> raitingEntitiesByApartmentId = raitingRepository.findRaitingEntitiesByApartment_Id(a.getAddressEntity().getApartmentEntity().getId());

                if (!raitingEntitiesByApartmentId.isEmpty()) {
                    Integer averageRaiting = getAverageValueRaiting(raitingEntitiesByApartmentId);
                    apartmentDto.setRating(averageRaiting);
                } else {
                    apartmentDto.setRating(0);
                }
                addressDto.setApartmentDto(apartmentDto);

                collect.add(addressDto);
            }
            return new ResponseObjectList("Результат поиска", collect);
        }
        throw new NotFoundInformation();
    }

    /**
     * Метод возвращает адрес по id
     */
    @Override
    public AddressDto getApartmentById(Long id) {

        AddressEntity addressEntity = addressRepository.findById(id).get();
        List<RaitingEntity> raitingEntitiesByApartmentId = raitingRepository.findRaitingEntitiesByApartment_Id(addressEntity.getApartmentEntity().getId());
        AddressDto addressDto = fullMapper.addressEntityToAddressDto(addressEntity);
        ApartmentDto apartmentDto = fullMapper.apartmentEntityToApartmentDto(addressEntity.getApartmentEntity());

        if (!raitingEntitiesByApartmentId.isEmpty()) {
            Integer averageRaiting = getAverageValueRaiting(raitingEntitiesByApartmentId);
            apartmentDto.setRating(averageRaiting);
        } else {
            apartmentDto.setRating(0);
        }
        addressDto.setApartmentDto(apartmentDto);
        return addressDto;
    }


    @Override
    public AddressDto getApartmentByIdAndTotalAmount(Long id, String authToken, LocalDate start, LocalDate end) {

        logger.info("rent-apartment : getApartmentByIdAndTotalAmount -> started");


        AddressEntity addressEntity = addressRepository.findById(id).get();
        ClientEntity clientEntity = clientRepository.getClientEntityByEmail(validateUserToken.getEmailSession(authToken));

        clientEntity.setCountOfGrocery(increment(clientEntity.getCountOfGrocery()));
        addressEntity.getApartmentEntity().setAvailability(false);
        addressEntity.getApartmentEntity().setCurrentTenant(clientEntity);

        clientRepository.save(clientEntity);
        addressRepository.save(addressEntity);
        List<RaitingEntity> raitingEntitiesByApartmentId = raitingRepository.findRaitingEntitiesByApartment_Id(addressEntity.getApartmentEntity().getId());
        AddressDto addressDto = fullMapper.addressEntityToAddressDto(addressEntity);
        ApartmentDto apartmentDto = fullMapper.apartmentEntityToApartmentDto(addressEntity.getApartmentEntity());
        if (!raitingEntitiesByApartmentId.isEmpty()) {
            Integer averageRaiting = getAverageValueRaiting(raitingEntitiesByApartmentId);
            apartmentDto.setRating(averageRaiting);

        } else {
            apartmentDto.setRating(0);
        }
        addressDto.setApartmentDto(apartmentDto);
        LocalDate dateBookingRegistration = LocalDate.now();
        bookingHistoryRepository.save(fullMapper.prepareBookingEntity(addressEntity.getApartmentEntity(),
                clientEntity,
                start,
                end,
                dateBookingRegistration));
        Long lastIdInBookingHistory = bookingHistoryRepository.getLastId();
        try {
            integrationManager.throwInfoOnRentProduct(lastIdInBookingHistory);
        } catch (Exception e) {
            logger.error("rent-apartment : getApartmentByIdAndTotalAmount -> started");
            producerService.getProducerInfo(lastIdInBookingHistory.toString());
        }

        return addressDto;
    }

    public Integer increment(int countOfGrocery) {
        return countOfGrocery + 1;
    }

}






