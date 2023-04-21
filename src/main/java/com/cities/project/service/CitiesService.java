package com.cities.project.service;

import com.cities.project.model.city.City;
import com.cities.project.model.city.CityDto;
import com.cities.project.model.city.mapper.CityDtoMapper;
import com.cities.project.repository.CitiesRepository;
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

    public static final int DEFAULT_PAGE_SIZE = 10;

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
        return citiesRepository.findAll(specification, pageable);
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
                String.format("Record with id: %S not found in database", id)));
    }

    private Specification<City> createSpecification(String search) {
        return (root, query, cb) -> cb.like(root.get("name"), search);
    }
}
