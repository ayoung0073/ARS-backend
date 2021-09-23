package com.may.ars.mapper;

import com.may.ars.domain.guest.GuestBook;
import com.may.ars.dto.guest.GuestRequestDto;
import com.may.ars.dto.guest.GuestResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GuestMapper {

    @Mapping(target = "id", ignore = true)
    GuestBook toEntity(GuestRequestDto requestDto);

    GuestResponseDto toDto(GuestBook guestBook);

}
