package com.capstone.onda.domain.roomType.enumeration.amenity;



import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.reflect.TypeToken;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.util.List;

@Converter
@Slf4j
public class AmenityConverter implements AttributeConverter<List<AmenityOption>, String> {

    private static final Gson gson = new Gson();

    @Override
    public String convertToDatabaseColumn(List<AmenityOption> amenityOptions) {
        if (amenityOptions == null) return null;
        return gson.toJson(amenityOptions);
    }

    @Override
    public List<AmenityOption> convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            Type listType = new TypeToken<List<AmenityOption>>() {}.getType();
            return gson.fromJson(dbData, listType);
        } catch (IllegalArgumentException e) {
            log.error("failure to convert cause unexpected code [{}]", dbData, e);
            throw e;
        }
    }
}

