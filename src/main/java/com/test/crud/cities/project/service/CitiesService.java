package com.test.crud.cities.project.service;

import com.test.crud.cities.project.model.City;
import com.test.crud.cities.project.model.CityDto;
import com.test.crud.cities.project.model.mapper.CityDtoMapper;
import com.test.crud.cities.project.repository.CitiesRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
@Slf4j
public class CitiesService {

    private final CitiesRepository citiesRepository;
    private final CityDtoMapper cityDtoMapper;

    private final int DEFAULT_PAGE_SIZE = 10;

    public List<CityDto> getAll(Integer pageNumber, Integer pageSize, String searchText) {
        Page<City> pagedCities = getAllEntities(pageNumber, pageSize, searchText);
        List<City> cities = pagedCities.get().collect(Collectors.toList());
        return cityDtoMapper.map(cities);
    }

    public Page<City> getAllEntities(Integer pageNumber, Integer pageSize, String searchText) {
        pageSize = ofNullable(pageSize).orElse(DEFAULT_PAGE_SIZE);
        pageNumber = pageNumber == null ? 0 : pageNumber;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id").ascending());
        Specification<City> specification =
                createSpecification("%" + ofNullable(searchText).orElse("") + "%");
        var s = citiesRepository.findAll(specification, pageable);
        return s;
    }

    public CityDto getOneById(Long id) {
        return cityDtoMapper.map(findOneById(id));
    }

    @Transactional
    public CityDto editById(CityDto cityDto) {
        City city = findOneById(cityDto.getId());
        city.setName(cityDto.getName());
        city.setPictureUrl(cityDto.getPictureUrl());
        return cityDtoMapper.map(city);
    }

    private City findOneById(Long id) {
        return citiesRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Запись с id: %S не найдена в базе данных", id)));
    }

    private Specification<City> createSpecification(String search) {
        return (root, query, cb) -> cb.like(root.get("name"), search);
    }
}
