package com.pewde.messagingservice.mapper;

import com.pewde.messagingservice.exception.HttpException;
import com.pewde.messagingservice.response.ExceptionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExceptionMapper {

    @Mapping(target = "message", expression = "java(exception.getMessage())")
    ExceptionResponse mapExceptionToExceptionResponse(HttpException exception);

}
