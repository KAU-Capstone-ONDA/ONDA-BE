package com.capstone.onda.domain.roomType.enumeration.attraction;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

@Converter
@Slf4j
public class AttractionConverter implements AttributeConverter<AttractionOption, String> {

    @Override
    public String convertToDatabaseColumn(AttractionOption attractionOption) {
        if (attractionOption == null) return null;
        return attractionOption.getViewName();
    }

    @Override
    public AttractionOption convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            return AttractionOption.fromCode(dbData);
        } catch (IllegalArgumentException e) {
            log.error("failure to convert cause unexpected code [{}]", dbData, e);
            throw e;
        }
    }
}

