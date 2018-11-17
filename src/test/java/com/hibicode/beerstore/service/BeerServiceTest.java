package com.hibicode.beerstore.service;

import com.hibicode.beerstore.model.Beer;
import com.hibicode.beerstore.model.BeerType;
import com.hibicode.beerstore.repository.Beers;
import com.hibicode.beerstore.service.exception.BeerAlreadyExistException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.hibicode.beerstore.service.exception.EntityNotFoundException;
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

    @Test
    public void should_update_beer_volume() {

        Beer newBeerInDataBase = new Beer();
        newBeerInDataBase.setId(10L);
        newBeerInDataBase.setName("Heineken");
        newBeerInDataBase.setType(BeerType.LAGER);
        newBeerInDataBase.setVolume(new BigDecimal("350"));

        when(beersMocked.findByNameAndType("Heineken", BeerType.LAGER)).thenReturn(Optional.of(newBeerInDataBase));

        final Beer beerToUpdate = new Beer();
        beerToUpdate.setId(10L);
        beerToUpdate.setName("Heineken");
        beerToUpdate.setType(BeerType.LAGER);
        beerToUpdate.setVolume(new BigDecimal("200"));

        final Beer beerMocked = new Beer();
        beerMocked.setId(10L);
        beerMocked.setName("Heineken");
        beerMocked.setType(BeerType.LAGER);
        beerMocked.setVolume(new BigDecimal("200"));

        when(beersMocked.save(beerToUpdate)).thenReturn(beerMocked);

        final Beer beerUpdated = beerService.save(beerToUpdate);

        assertThat(beerUpdated.getId(), equalTo(10L));
        assertThat(beerUpdated.getName(), equalTo("Heineken"));
        assertThat(beerUpdated.getType(), equalTo(BeerType.LAGER));
        assertThat(beerUpdated.getVolume(), equalTo(new BigDecimal("200")));
    }

    @Test(expected = BeerAlreadyExistException.class)
    public void should_deny_update_of_a_existing_beer_that_already_exists() {
        final Beer beerInDatabase = new Beer();
        beerInDatabase.setId(10L);
        beerInDatabase.setName("Heineken");
        beerInDatabase.setType(BeerType.LAGER);
        beerInDatabase.setVolume(new BigDecimal("355"));

        when(beersMocked.findByNameAndType("Heineken", BeerType
                .LAGER)).thenReturn(Optional.of(beerInDatabase));

        final Beer beerToUpdate = new Beer();
        beerToUpdate.setId(5L);
        beerToUpdate.setName("Heineken");
        beerToUpdate.setType(BeerType.LAGER);
        beerToUpdate.setVolume(new BigDecimal("355"));

        beerService.save(beerToUpdate);
    }

    @Test(expected = EntityNotFoundException.class)
    public void should_deny_delete_of_a_existing_beer() {
        beerService.delete(10L);
    }

}
