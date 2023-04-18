package com.test.crud.cities.project.model.mapper;

import com.test.crud.cities.project.model.City;
import com.test.crud.cities.project.model.CityDto;
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
