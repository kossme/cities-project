package integration;

import com.cities.project.CitiesProjectApplication;
import com.cities.project.model.city.City;
import com.cities.project.model.city.CityDto;
import com.cities.project.repository.CitiesRepository;
import com.cities.project.service.CitiesService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = CitiesProjectApplication.class)
@TestPropertySource(locations = "classpath:application-test.yml")
@ActiveProfiles("test")
public class SpringBootJpaAndPaginationIntegrationTest {

    @Autowired
    private CitiesRepository citiesRepository;
    @Autowired
    private CitiesService citiesService;

    private final int initialTestDataAmount = 50;

    @Before
    public void addTestDataToRepository() {
        citiesRepository.deleteAll();
        List<City> cities = new ArrayList<>();
        for (int i = 0; i < initialTestDataAmount; i++) {
            cities.add(new City(null, UUID.randomUUID().toString(), UUID.randomUUID().toString()));
        }
        citiesRepository.saveAll(cities);
    }

    @Test
    public void cityRepository_saveAndRetrieveAndDeleteEntityTest() {
        City city = new City(null,"TestCity", "TestUrl");
        citiesRepository.save(city);
        City cityFromDb = citiesRepository.findById(city.getId()).get();
        assertNotNull(cityFromDb);
        assertEquals(city.getName(), cityFromDb.getName());

        citiesRepository.deleteById(cityFromDb.getId());
        assert(citiesRepository.findById(city.getId()).isEmpty());
    }


    @Test(expected = EntityNotFoundException.class)
    public void cityService_ShouldThrowNotFoundExceptionIfASkFornotExistIdTest() {
        citiesService.getOneById(initialTestDataAmount + 1L);
    }

    @Test
    public void cityService_FindAllDefaultPaginationTest() {
        List<CityDto> cityDtos = citiesService.getAll(null, null, null);
        assertNotNull(cityDtos);
        assertEquals(cityDtos.size(), CitiesService.DEFAULT_PAGE_SIZE);
    }

    @Test
    public void cityService_FindAllWithCustomPaginationTest() {
        int pageSize = 30;
        List<CityDto> cityDtos = citiesService.getAll(null, pageSize, null);
        assertNotNull(cityDtos);
        assertEquals(cityDtos.size(), pageSize);
    }

    @Test
    public void cityService_whenFirstPageHasAllDataThenSecondIsEmptyTest() {
        int pageSize = initialTestDataAmount;
        List<CityDto> cityDtos = citiesService.getAll(0, pageSize, null);
        assertNotNull(cityDtos);
        assertEquals(cityDtos.size(), pageSize);

        List<CityDto> cityDtos1 = citiesService.getAll(1, pageSize, null);
        assert(cityDtos1.isEmpty());
    }

    @Test
    public void cityService_amountOfPagesTest() {
        int pageSize = 9;
        int amountOfPages;
        if (initialTestDataAmount % CitiesService.DEFAULT_PAGE_SIZE == 0) {
            amountOfPages =  initialTestDataAmount / CitiesService.DEFAULT_PAGE_SIZE;
        } else {
            amountOfPages =  initialTestDataAmount / CitiesService.DEFAULT_PAGE_SIZE + 1;
        }
        int lastPage = 0;
        for (int i = 0; i < amountOfPages; i++) {
            List<CityDto> cityDtos = citiesService.getAll(i, pageSize, null);
            assert(!cityDtos.isEmpty());
            lastPage++;
        }
        List<CityDto> cityDtos = citiesService.getAll(lastPage + 1, pageSize, null);
        assert(cityDtos.isEmpty());
    }
}
