package com.capstone.onda.domain.roomType;

import lombok.Data;

@Data
public class EnumDTO {
    private String key;
    private String value;

    public EnumDTO(EnumManager enumManager) {
        this.key = enumManager.getKey();
        this.value = enumManager.getValue();
    }
}
