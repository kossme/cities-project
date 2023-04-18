package com.test.crud.cities.project.repository;

import com.test.crud.cities.project.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CitiesRepository extends JpaRepository<City, Long> , JpaSpecificationExecutor<City> {


}
