package com.capstone.onda.domain.roomType.enumeration.service;

import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.reflect.TypeToken;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.util.List;

@Converter
@Slf4j
public class ServiceConverter implements AttributeConverter<List<ServiceOption>, String> {

    private static final Gson gson = new Gson();

    @Override
    public String convertToDatabaseColumn(List<ServiceOption> serviceOptions) {
        if (serviceOptions == null) return null;
        return gson.toJson(serviceOptions);
    }

    @Override
    public List<ServiceOption> convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            Type listType = new TypeToken<List<ServiceOption>>() {}.getType();
            return gson.fromJson(dbData, listType);
        } catch (IllegalArgumentException e) {
            log.error("failure to convert cause unexpected code [{}]", dbData, e);
            throw e;
        }
    }
}
