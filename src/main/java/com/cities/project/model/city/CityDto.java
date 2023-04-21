package com.cities.project.model.city;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NotNull
public class CityDto {

    @NotNull
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String pictureUrl;

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return id != null && obj instanceof CityDto && id.equals(((CityDto) obj).getId());
    }
}
