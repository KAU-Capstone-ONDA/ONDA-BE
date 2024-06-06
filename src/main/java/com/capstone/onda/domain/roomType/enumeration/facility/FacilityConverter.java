package com.capstone.onda.domain.roomType.enumeration.facility;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

@Converter
@Slf4j
public class FacilityConverter implements AttributeConverter<FacilityOption, String> {

    @Override
    public String convertToDatabaseColumn(FacilityOption facilityOption) {
        if (facilityOption == null) return null;
        return facilityOption.getViewName();
    }

    @Override
    public FacilityOption convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            return FacilityOption.fromCode(dbData);
        } catch (IllegalArgumentException e) {
            log.error("failure to convert cause unexpected code [{}]", dbData, e);
            throw e;
        }
    }
}

