package com.capstone.onda.domain.roomType.enumeration.service;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

@Converter
@Slf4j
public class ServiceConverter implements AttributeConverter<ServiceOption, String> {

    @Override
    public String convertToDatabaseColumn(ServiceOption serviceOption) {
        if (serviceOption == null) return null;
        return serviceOption.getViewName();
    }

    @Override
    public ServiceOption convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            return ServiceOption.fromCode(dbData);
        } catch (IllegalArgumentException e) {
            log.error("failure to convert cause unexpected code [{}]", dbData, e);
            throw e;
        }
    }
}
