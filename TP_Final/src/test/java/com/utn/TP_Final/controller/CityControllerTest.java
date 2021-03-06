package com.utn.TP_Final.controller;

import com.utn.TP_Final.exceptions.ValidationException;
import com.utn.TP_Final.model.City;
import com.utn.TP_Final.service.CityService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class CityControllerTest {

    @InjectMocks
    CityController cityController;

    @Mock
    CityService cityService;

    @Mock
    HttpServletRequest request;

    @Before
    public void setUp()
    {
        initMocks(this);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    public void addCityTest() throws ValidationException
    {
        City city = new City(1, "Mar del Plata", "223", null);
        when(cityService.addCity(city)).thenReturn(city);
        ResponseEntity<City> cityResult = cityController.addCity(city);
        assertEquals(HttpStatus.CREATED, cityResult.getStatusCode());
    }

    @Test
    public void deleteCityOk() throws ValidationException
    {
        City city = new City(1, "Mar del Plata", "223", null);
        when(cityService.deleteCity(1)).thenReturn(city);
        ResponseEntity<City> cityResult = cityController.deleteCity(1);
        assertEquals(HttpStatus.OK, cityResult.getStatusCode());
    }

    @Test
    public void getAllTest()
    {
        List<City> cities = new ArrayList<City>();
        City city1 = new City(1, "Mar del Plata", "223", null);
        City city2 = new City(2, "Buenos Aires", "11", null);
        cities.add(city1);
        cities.add(city2);

        when(cityService.getAll(null)).thenReturn(cities);

        ResponseEntity<List<City>> cityList = cityController.getAll(null);
        assertEquals(HttpStatus.OK, cityList.getStatusCode());
        verify(cityService, times(1)).getAll(null);
    }

    @Test
    public void getAllEmptyTest()
    {
        List<City> cities = new ArrayList<City>();
        when(cityService.getAll(null)).thenReturn(cities);
        ResponseEntity<List<City>> citiesResult = cityController.getAll(null);
        assertEquals(HttpStatus.OK, citiesResult.getStatusCode());
    }

    @Test
    public void getByIdOk() throws ValidationException
    {
        List<City> cities = new ArrayList<City>();
        City city1 = new City(1, "Mar del Plata", "223", null);
        City city2 = new City(2, "Buenos Aires", "11", null);
        cities.add(city1);
        cities.add(city2);

        Optional<City> cityOptional = Optional.ofNullable(cities.get(0));

        when(cityService.getById(1)).thenReturn(cityOptional);

        ResponseEntity<Optional<City>> cityResult = cityController.getById(1);

        assertEquals(HttpStatus.OK, cityResult.getStatusCode());
        verify(cityService, times(1)).getById(1);
    }

    @Test
    public void getByPrefixOk() throws ValidationException
    {
        List<City> cities = new ArrayList<City>();
        City city1 = new City(1, "Mar del Plata", "223", null);
        City city2 = new City(2, "Buenos Aires", "11", null);
        cities.add(city1);
        cities.add(city2);

        when(cityService.getByPrefix("223")).thenReturn(cities.get(0));

        ResponseEntity<City> cityResult = cityController.getByPrefix("223");
        assertEquals(HttpStatus.OK, cityResult.getStatusCode());
        verify(cityService, times(1)).getByPrefix("223");
    }
}
