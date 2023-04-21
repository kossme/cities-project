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

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = CitiesProjectApplication.class)
@TestPropertySource(locations = "classpath:application-test.yml")
@ActiveProfiles("test")
public class SpringBootJpaAndSearchIntegrationTest {

    @Autowired
    private CitiesRepository citiesRepository;
    @Autowired
    private CitiesService citiesService;

    private final String cityName1 = "Aaaa";
    private final String cityName2 = "Bbbb";
    private final String cityName3 = "Cccc";
    private final String cityName4 = "Dddd";
    private final String cityName5 = "Aabb";


    @Before
    public void addTestDataToRepository() {
        List<City> citiesList = List.of(
                new City(null, cityName1, "TestUrl"),
                new City(null, cityName2, "TestUrl"),
                new City(null, cityName3, "TestUrl"),
                new City(null, cityName4, "TestUrl"),
                new City(null, cityName5, "TestUrl")
        );
        citiesRepository.deleteAll();
        citiesRepository.saveAll(citiesList);
    }

    @Test
    public void cityService_SearchWithAnotherValueShoulReturnEmptyTest() {
        var cities = citiesService.getAll(null, null, cityName1 + "Another");
        assert (cities.isEmpty());
    }

    @Test
    public void cityService_SearchWithRightValueCaseSensitiveShoulReturnExactResult() {
        var cities = citiesService.getAll(null, null, cityName1);
        assert(cities.size() == 1);
        assertEquals(cities.get(0).getName(), cityName1);
    }

    @Test
    public void cityService_SearchWithRightValueButWrongCaseShoulReturnEmptyResult() {
        var cities = citiesService.getAll(null, null, cityName1.toLowerCase());
        assert (cities.isEmpty());
    }

    @Test
    public void cityService_SearchWithMultipleResultsTest() {
        var cities = citiesService.getAll(null, null, cityName5.substring(0, 2));
        assert(cities.size() == 2);

        List<String> namesFromSearch = cities.stream().map(CityDto::getName).collect(Collectors.toList());
        assert(namesFromSearch.contains(cityName1) && namesFromSearch.contains(cityName5));
    }

}
