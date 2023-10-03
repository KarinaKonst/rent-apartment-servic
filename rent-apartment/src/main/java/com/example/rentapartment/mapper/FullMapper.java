package com.example.rentapartment.mapper;


import com.example.rentapartment.dto.*;
import com.example.rentapartment.entity.AddressEntity;
import com.example.rentapartment.entity.ApartmentEntity;
import com.example.rentapartment.entity.ClientEntity;
import com.example.rentapartment.model.ApartmentRegistration;
import com.example.rentapartment.security_model.UserRegistrationInfo;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

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

//public IntegrationModelToMailSendler prepareIntegrationModelMailSenderFromEntity(AddressEntity addressEntity,
//                                                                                 ApartmentEntity apartmentEntity,
//                                                                                 ClientEntity clientEntity);
//
//public IntegrationModelToMailSendler prepareIntegrationModelMailSenderFromBookingHistory(Long id);


}

