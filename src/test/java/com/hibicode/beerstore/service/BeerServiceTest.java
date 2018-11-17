package com.hibicode.beerstore.service;

import com.hibicode.beerstore.model.Beer;
import com.hibicode.beerstore.model.BeerType;
import com.hibicode.beerstore.repository.Beers;
import com.hibicode.beerstore.service.exception.BeerAlreadyExistException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

public class BeerServiceTest {

    private BeerService beerService;
    @Mock
    private Beers beersMocked;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        beerService = new BeerService(beersMocked);
    }


    @Test(expected = BeerAlreadyExistException.class)
    public void should_deny_creation_of_beer_that_exists() {
        Beer beerInDataBase = new Beer();
        beerInDataBase.setId(10L);
        beerInDataBase.setName("Heineken");
        beerInDataBase.setVolume(new BigDecimal("355"));
        beerInDataBase.setType(BeerType.LAGER);

        when(beersMocked.findByNameAndType("Heineken", BeerType.LAGER)).thenReturn(Optional.of(beerInDataBase));

        Beer newBeer = new Beer();
        newBeer.setName("Heineken");
        newBeer.setType(BeerType.LAGER);
        newBeer.setVolume(new BigDecimal("350"));

        beerService.save(newBeer);
    }

    @Test
    public void should_create_new_beer() {
        Beer newBeer = new Beer();
        newBeer.setName("Heineken");
        newBeer.setType(BeerType.LAGER);
        newBeer.setVolume(new BigDecimal("350"));

        Beer newBeerInDataBaseSaved = new Beer();
        newBeerInDataBaseSaved.setId(10L);
        newBeerInDataBaseSaved.setName("Heineken");
        newBeerInDataBaseSaved.setType(BeerType.LAGER);
        newBeerInDataBaseSaved.setVolume(new BigDecimal("350"));

        when(beersMocked.save(newBeer)).thenReturn(newBeerInDataBaseSaved);

        Beer beerSaved = beerService.save(newBeer);

        assertThat(beerSaved.getId(), equalTo(10L));
        assertThat(beerSaved.getName(), equalTo("Heineken"));
        assertThat(beerSaved.getType(), equalTo(BeerType.LAGER));
        assertThat(beerSaved.getVolume(), equalTo(new BigDecimal("350")));

    }

}
