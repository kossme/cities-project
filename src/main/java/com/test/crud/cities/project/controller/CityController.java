package com.test.crud.cities.project.controller;

import com.test.crud.cities.project.model.CityDto;
import com.test.crud.cities.project.service.CitiesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(path = "/cities", produces = MediaType.APPLICATION_JSON_VALUE)
public class CityController {

    private final CitiesService citiesService;

    @GetMapping
    public ResponseEntity<List<CityDto>> getAll(
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String searchText)
    {
        log.info("Request get all cities pageNumber={}, pageSize={}, searchText={}", pageNumber, pageSize, searchText);
        return ResponseEntity.ok(citiesService.getAll(pageNumber, pageSize, searchText));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityDto> getOneById(@PathVariable Long id) {
        log.info("Request get city by ID={}", id);
        return ResponseEntity.ok(citiesService.getOneById(id));
    }

    @PutMapping
    public ResponseEntity<CityDto> editById(@RequestBody @Valid CityDto cityDto) {
        log.info("Request edit city by ID={}", cityDto.getId());
        return ResponseEntity.ok(citiesService.editById(cityDto));
    }
}
