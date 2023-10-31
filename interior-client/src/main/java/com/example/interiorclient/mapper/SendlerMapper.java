package com.example.interiorclient.mapper;

import com.example.interiorclient.dto.AddressDto;
import com.example.interiorclient.dto.ApartmentDto;
import com.example.interiorclient.dto.ClientDto;
import com.example.interiorclient.entity.AddressEntity;
import com.example.interiorclient.entity.ApartmentEntity;
import com.example.interiorclient.entity.ClientEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface SendlerMapper {


    public ClientDto getClientEntityToClientDto(ClientEntity clientEntity);
    public AddressDto getAddressEntityToAddressDto(AddressEntity addressEntity);

}
