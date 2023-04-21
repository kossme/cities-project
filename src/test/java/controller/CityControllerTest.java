package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.cities.project.controller.CityController;
import com.cities.project.model.city.CityDto;
import com.cities.project.service.CitiesService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class CityControllerTest {

    MockMvc mockMvc;

    @InjectMocks
    private CityController cityController;

    @Mock
    private CitiesService citiesService;

    private CityDto cityDto;

    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        cityDto = new CityDto();
        cityDto.setId(1L);
        cityDto.setName("Test City");
        cityDto.setPictureUrl("http://example.com/test.jpg");

        this.mockMvc = MockMvcBuilders.standaloneSetup(new CityController(citiesService)).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetAll() {
        when(citiesService.getAll(null, null, null)).thenReturn(Collections.singletonList(cityDto));

        ResponseEntity<List<CityDto>> response = cityController.getAll(null, null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(cityDto, response.getBody().get(0));
        verify(citiesService, times(1)).getAll(null, null, null);
    }

    @Test
    public void testGetOneById() throws Exception {
        when(citiesService.getOneById(1L)).thenReturn(cityDto);

        ResponseEntity<CityDto> response = cityController.getOneById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cityDto, response.getBody());
        verify(citiesService, times(1)).getOneById(1L);

        mockMvc.perform(get("/cities/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testEditById() {
        when(citiesService.editById(cityDto)).thenReturn(cityDto);

        ResponseEntity<CityDto> response = cityController.editById(cityDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cityDto, response.getBody());
        verify(citiesService, times(1)).editById(cityDto);
    }


    @Test
    public void testGetOneById_() throws Exception {
        when(citiesService.getOneById(1L)).thenReturn(cityDto);

        mockMvc.perform(get("/cities/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetAll_() throws Exception {
        mockMvc.perform(get("/cities"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testEditByIdWhanPassedCorrectData() throws Exception {
        mockMvc.perform(put("/cities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cityDto))
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testEditByIdWhenPassedIncorrectData() throws Exception {
        cityDto.setName(null);
        mockMvc.perform(put("/cities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cityDto))
                )
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

}