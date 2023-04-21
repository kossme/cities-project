package service;

import com.cities.project.model.city.City;
import com.cities.project.model.city.CityDto;
import com.cities.project.model.city.mapper.CityDtoMapper;
import com.cities.project.repository.CitiesRepository;
import com.cities.project.service.CitiesService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CitiesServiceTest {

    @InjectMocks
    private CitiesService citiesService;

    @Mock
    private CitiesRepository citiesRepository;

    @Mock
    private CityDtoMapper cityDtoMapper;

    private City city;
    private CityDto cityDto;

    @Before
    public void setUp() {
        city = new City();
        city.setId(1L);
        city.setName("Test City");
        city.setPictureUrl("http://example.com/test.jpg");

        cityDto = new CityDto();
        cityDto.setId(1L);
        cityDto.setName("Test City");
        cityDto.setPictureUrl("http://example.com/test.jpg");
    }

    @Test
    public void testGetAll() {
        when(citiesRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl(Collections.singletonList(city)));
        when(cityDtoMapper.map(Collections.singletonList(city))).thenReturn(Collections.singletonList(cityDto));

        List<CityDto> result = citiesService.getAll(null, null, null);

        assertEquals(1, result.size());
        assert(cityDto.equals(result.get(0)));
        verify(citiesRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
        verify(cityDtoMapper, times(1)).map(Collections.singletonList(city));
    }

    @Test
    public void testGetOneById() {
        when(citiesRepository.findById(1L)).thenReturn(Optional.of(city));
        when(cityDtoMapper.map(city)).thenReturn(cityDto);

        CityDto result = citiesService.getOneById(1L);

        assertEquals(cityDto, result);
        verify(citiesRepository, times(1)).findById(1L);
        verify(cityDtoMapper, times(1)).map(city);
    }

    @Test
    public void testEditById() {
        when(citiesRepository.findById(1L)).thenReturn(Optional.of(city));
        when(cityDtoMapper.map(city)).thenReturn(cityDto);

        CityDto result = citiesService.editById(cityDto);

        assertEquals(cityDto, result);
        verify(citiesRepository, times(1)).findById(1L);
        verify(cityDtoMapper, times(1)).map(city);
    }
}