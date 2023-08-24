package com.example.rentproduct.mapper;

import com.example.rentproduct.dto.AddressDto;
import com.example.rentproduct.dto.ApartmentDto;
import com.example.rentproduct.dto.ClientDto;
import com.example.rentproduct.entity.ApartmentEntity;
import com.example.rentproduct.entity.ClientEntity;
import com.example.rentproduct.model.IntegrationModelToMailSendler;
import org.mapstruct.Mapper;




@Mapper(componentModel="spring")
public interface SendlerMapper {

//    public AddressDto getAddressDto(IntegrationModelToMailSendler integrationModelToMailSendler) ;
//    public ClientDto getClientDto(IntegrationModelToMailSendler integrationModelToMailSendler);
//    public Long getIdLastBookingHistory(IntegrationModelToMailSendler integrationModelToMailSendler);
    public ApartmentDto getApartmentEntityToApartmentDto(ApartmentEntity apartmentEntity);
    public ClientDto getClientEntityToClientDto(ClientEntity clientEntity);

}
