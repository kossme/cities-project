package com.cities.project.model.city.mapper;

import com.cities.project.model.city.City;
import com.cities.project.model.city.CityDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        builder = @Builder(disableBuilder = true),
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CityDtoMapper {

    CityDto map(City city);

    List<CityDto> map(List<City> city);


}
