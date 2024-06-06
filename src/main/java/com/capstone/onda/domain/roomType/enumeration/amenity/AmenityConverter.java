package com.capstone.onda.domain.roomType.enumeration.amenity;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

@Converter
@Slf4j
public class AmenityConverter implements AttributeConverter<AmenityOption, String> {

    @Override
    public String convertToDatabaseColumn(AmenityOption amenityOption) {
        if (amenityOption == null) return null;
        return amenityOption.getViewName();
    }

    @Override
    public AmenityOption convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            return AmenityOption.fromCode(dbData);
        } catch (IllegalArgumentException e) {
            log.error("failure to convert cause unexpected code [{}]", dbData, e);
            throw e;
        }
    }
}
