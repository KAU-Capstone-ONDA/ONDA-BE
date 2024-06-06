package com.capstone.onda.domain.roomType.enumeration.roomType;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

@Converter
@Slf4j
public class RoomTypeCategoryConverter implements AttributeConverter<RoomTypeCategory, String> {

    @Override
    public String convertToDatabaseColumn(RoomTypeCategory roomTypeCategory) {
        if (roomTypeCategory == null) return null;
        return roomTypeCategory.getValue();
    }

    @Override
    public RoomTypeCategory convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            return RoomTypeCategory.fromCode(dbData);
        } catch (IllegalArgumentException e) {
            log.error("failure to convert cause unexpected code [{}]", dbData, e);
            throw e;
        }
    }
}
