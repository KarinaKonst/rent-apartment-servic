package com.example.rentapartment.mapper;


import com.example.rentapartment.dto.AddressDto;
import com.example.rentapartment.dto.ApartmentDto;
import com.example.rentapartment.dto.ClientDto;
import com.example.rentapartment.dto.InfoByCityDto;
import com.example.rentapartment.entity.AddressEntity;
import com.example.rentapartment.entity.ApartmentEntity;
import com.example.rentapartment.entity.BookingHistoryEntity;
import com.example.rentapartment.entity.ClientEntity;
import com.example.rentapartment.model.ApartmentRegistration;
import com.example.rentapartment.security_model.UserRegistrationInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface FullMapper {


    @Mapping(target = "rating", ignore = true)
    public ApartmentDto apartmentEntityToApartmentDto(ApartmentEntity apartmentEntity);

    public AddressDto addressEntityToAddressDto(AddressEntity addressEntity);

    public InfoByCityDto getInfoByCity(AddressEntity addressEntity);


    public default AddressDto getFullFieldsAddressToDto(AddressEntity addressEntity) {


        ApartmentDto apartmentDto = apartmentEntityToApartmentDto(addressEntity.getApartmentEntity());
        AddressDto addressDto = addressEntityToAddressDto(addressEntity.getApartmentEntity().getAddressEntity());
        addressDto.setApartmentDto(apartmentDto);


        return addressDto;


    }
    public ClientDto clientEntityToClientDto(ClientEntity clientEntity);
    @Mapping(target="countOfGrocery",constant = "0")
    public ClientEntity userRegistrationInfoToEntity(UserRegistrationInfo userRegistrationInfo);

    public AddressEntity apartmentRegistrationToAddressEntity(ApartmentRegistration apartmentRegistration);
    public ApartmentEntity apartmentRegistrationToApartmentEntity(ApartmentRegistration apartmentRegistration);

    public default BookingHistoryEntity prepareBookingEntity(ApartmentEntity apartmentEntity,
                                                      ClientEntity clientEntity,
                                                      LocalDate start,
                                                      LocalDate end, LocalDate dateBookingRegistration) {
        BookingHistoryEntity bookingHistoryEntity = new BookingHistoryEntity();
        bookingHistoryEntity.setApartmentId(apartmentEntity);
        bookingHistoryEntity.setClientId(clientEntity);
        bookingHistoryEntity.setDateStart(start);
        bookingHistoryEntity.setDateEnd(end);
        bookingHistoryEntity.setDateRegistrationBooking(dateBookingRegistration);
        return bookingHistoryEntity;
    }

}

