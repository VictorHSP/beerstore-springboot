package com.hibicode.beerstore.service;

import com.hibicode.beerstore.model.Beer;
import com.hibicode.beerstore.model.BeerType;
import com.hibicode.beerstore.service.exception.BeerAlreadyExistException;
import org.junit.Test;

import java.math.BigDecimal;

public class BeerServiceTest {

    private BeerService beerService;

    @Test(expected = BeerAlreadyExistException.class)
    public void should_deny_creation_of_beer_that_exists() {
        beerService = new BeerService();

        Beer newBeer = new Beer();
        newBeer.setName("Heineken");
        newBeer.setType(BeerType.LAGER);
        newBeer.setVolume(new BigDecimal("350"));

        beerService.save(newBeer);
    }

}
